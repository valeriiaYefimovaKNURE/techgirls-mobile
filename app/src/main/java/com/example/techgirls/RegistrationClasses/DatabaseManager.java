package com.example.techgirls.RegistrationClasses;

import static androidx.core.content.ContextCompat.getDrawable;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.techgirls.HelpClasses.SharedMethods;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Pages.MainPage;
import com.example.techgirls.Models.Users;
import com.example.techgirls.Pages.RegisterPage;
import com.example.techgirls.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Manages interactions with the Firebase Realtime Database.
 */
public class DatabaseManager {
    private final DatabaseReference table;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    /**
     * Empty constructor for initialization the DatabaseManager and sets up a reference to the "Users" table in the Firebase Realtime Database.
     */
    public DatabaseManager() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        table = db.getReference("Users");
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public void checkEmailExistence(Context context, FirebaseUser user){
        table.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, proceed to the main activity
                    ShowPages.showMainPage(context);
                } else {
                    RegisterPage r=new RegisterPage();
                    r.showDialogForAdditionalInfo(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Error with checking email existence", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks if a login already exists in the database.
     *
     * @param login      The login to check for existence.
     * @param layout     The layout where error messages will be displayed.
     */
    public AtomicBoolean checkLoginExistence(String login, TextInputLayout layout) {
        AtomicBoolean validLogin = new AtomicBoolean(true);

        table.orderByChild("login").equalTo(login).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        validLogin.set(!dataSnapshot.exists());  //'true' если нет в базе
                        if (!validLogin.get()) {    //не 'true'
                            layout.setError(layout.getContext().getString(R.string.error_login_exist));
                        } else {
                            layout.setError(null);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );

        return validLogin;
    }
    public void registerUser(Activity context, String email, String password) {
        AlertDialog progressDialog = SharedMethods.createProgressDialog(context);
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            firebaseUser= firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                // Отправка письма для верификации
                                firebaseUser.sendEmailVerification().addOnCompleteListener(verificationTask -> {
                                    if(verificationTask.isSuccessful()) {
                                        progressDialog.dismiss();
                                    }
                                    else {
                                        // Ошибка при отправке письма для верификации
                                        Toast.makeText(context, "Помилка під час відправки листа для верифікації пошти", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(context, "Ця пошта вже використовується", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Ошибка при регистрации: " + Objects.requireNonNull(exception).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка при сохранении в базу данных", Toast.LENGTH_SHORT).show();
        }
    }
    public void verifyEmail(Context context, String email, String name, String login, String password, String birth, String gender) {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            // Обновляем информацию о пользователе
            firebaseUser.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (firebaseUser.isEmailVerified()) {
                        saveUserData(context, email, name, login, password, birth, gender);
                        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
                        ShowPages.showMainPage(context);
                    } else {
                        Toast.makeText(context, "Будь ласка, підтвердити свою пошту", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Не вдалося оновити статус користувача", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void resendEmailVerification(Context context){
        firebaseUser= firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(verificationTask -> {
            if(verificationTask.isSuccessful()) {
                Toast.makeText(context, "Повторний лист відіслано", Toast.LENGTH_SHORT).show();
            }
            else {
                // Ошибка при отправке письма для верификации
                Toast.makeText(context, "Помилка під час відправки листа для верифікації пошти", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteAuthUser(Context context){
        firebaseUser= firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            
        }
    }
    /**
     * Registers a new user in the database.
     *
     * @param context  The context of the page.
     * @param Email    The email of the user.
     * @param Name     The name of the user.
     * @param Login    The login of the user.
     * @param Password The password of the user.
     * @param Birth    The birth date of the user.
     * @param Gender   The gender of the user.
     */
    public void saveUserData(Context context, String Email, String Name, String Login, String Password, String Birth, String Gender) {
        try{
        Users user = new Users();
        user.setEmail(Email);
        user.setName(Name);
        user.setLogin(Login);
        user.setPassword(HashingClass.hashPassword(Password));
        user.setBirthday(Birth);
        user.setGender(Gender);
        user.setRole("USER");

        UserManager.getInstance(context).saveUser(Login, Email, Password, Name, Birth, Gender, "USER");

        table.child(Login).setValue(user);
        saveDataSharedPreference(context, Login, Email);
        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context, "Помилка при зберіганні до бази даних", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveUserData(Context context, Users user) {
        try{
            user.setRole("USER");

            UserManager.getInstance(context).saveUser(user.getLogin(), user.getEmail(), user.getPassword(), user.getName(),
                    user.getBirthday(), user.getGender(), "USER");

            table.child(user.getLogin()).setValue(user);
            saveDataSharedPreference(context, user.getLogin(), user.getEmail());
            Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context, "Помилка при зберіганні до бази даних", Toast.LENGTH_SHORT).show();
        }
    }

    //Вход происходит без FirebaseAuthentication поэтому не понятно как сделать
    public void ResetPassword(Context context,String email){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Лист для відновлення паролю відправлено на пошту", Toast.LENGTH_SHORT).show();
                    ShowPages.showLoginForm(context);
                }
                else{
                    Toast.makeText(context, "Помилка при відправленні листа", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param context     The context of the page.
     * @param login       The login of the user.
     * @param loginLayout The layout where login-related error messages will be displayed.
     * @param password    The password of the user.
     * @param passLayout  The layout where password-related error messages will be displayed.
     */
    public void authenticateUser(Context context,String login,TextInputLayout loginLayout,
                                 String password, TextInputLayout passLayout) {
        Query query = table.orderByChild("login").equalTo(login);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginLayout.setError(null);

                    String passwordFromDB = snapshot.child(login).child("password").getValue(String.class);
                    try {
                        if (HashingClass.verifyPassword(password,passwordFromDB)) {
                            loginLayout.setError(null);

                            String nameFromDB = snapshot.child(login).child("name").getValue(String.class);
                            String emailFromDB = snapshot.child(login).child("email").getValue(String.class);
                            String loginFromDB = snapshot.child(login).child("login").getValue(String.class);
                            String dateFromDB = snapshot.child(login).child("birthday").getValue(String.class);
                            String genderFromDB = snapshot.child(login).child("gender").getValue(String.class);
                            String role=snapshot.child(login).child("role").getValue(String.class);

                            saveDataSharedPreference(context,loginFromDB,emailFromDB);
                            Intent intent = new Intent(context, MainPage.class);
                            UserManager.getInstance(context).saveUser(loginFromDB,emailFromDB,passwordFromDB,nameFromDB,dateFromDB,genderFromDB,role);
                            context.startActivity(intent);
                        }
                        else {
                            passLayout.setError(passLayout.getContext().getString(R.string.error_logIn_password));
                            passLayout.requestFocus();
                        }
                    }catch (Exception e){
                        Toast.makeText(context, "Помилка при роботі з базами даних", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loginLayout.setError(loginLayout.getContext().getString(R.string.error_logIn_userNotExist));
                    loginLayout.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * Checks if a user has editor role.
     *
     * @param role The role of the user.
     * @return True if the user has editor role, otherwise false.
     */
    public static boolean isEditor(String role){
        return role != null && (role.equals("EDITOR"));
    }

    /**
     * Checks if a user has admin role.
     *
     * @param role The role of the user.
     * @return True if the user has editor role, otherwise false.
     */
    public static boolean isAdmin(String role){
        return role != null && (role.equals("ADMIN"));
    }
    public static boolean checkCurrentUserAuth(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser != null;
    }
    /**
     * Saves user data to SharedPreferences.
     *
     * @param context The context of the page.
     * @param login   The login of the user.
     * @param email   The email of the user.
     */

    public static void saveDataSharedPreference(Context context,String login, String email){
        SharedPreferences sh=context.getSharedPreferences("mePowerLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putBoolean("loginCounter",true);
        editor.putString("userLogin",login);
        editor.putString("userEmail",email);
        editor.apply();
    }
}