package com.example.techgirls.RegistrationClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.techgirls.HelpClasses.SharedMethods;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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
    public void checkEmailExistence(Context context, FirebaseUser user, Runnable onUserExists, Runnable notUserExists) {
        table.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, proceed with onUserExists action
                    onUserExists.run();
                } else {
                    notUserExists.run();
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
                                        Toast.makeText(context, R.string.register_error_sending_email, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(context, R.string.error_email_is_used, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.Error + Objects.requireNonNull(exception).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(context, R.string.error_db_saving, Toast.LENGTH_SHORT).show();
        }
    }
    public void verifyEmail(Context context, String email, String name, String login, String password, String birth, String gender) {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            // Обновляем информацию о пользователе
            firebaseUser.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (firebaseUser.isEmailVerified()) {
                        saveUserData(context, email, name, login, password, birth, gender, firebaseUser.getUid());
                        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
                        ShowPages.showMainPage(context);
                    } else {
                        Toast.makeText(context, R.string.verify_email_warning, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.verify_email_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void resendEmailVerification(Context context){
        firebaseUser= firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(verificationTask -> {
            if(verificationTask.isSuccessful()) {
                Toast.makeText(context, R.string.verify_email_resend_sms, Toast.LENGTH_SHORT).show();
            }
            else {
                // Ошибка при отправке письма для верификации
                Toast.makeText(context, R.string.register_error_sending_email, Toast.LENGTH_SHORT).show();
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
    public void saveUserData(Context context, String Email, String Name, String Login,
                             String Password, String Birth, String Gender, String uid) {
        try{
        Users user = new Users();
        user.setEmail(Email);
        user.setName(Name);
        user.setLogin(Login);
        user.setPassword(HashingClass.hashPassword(Password));
        user.setBirthday(Birth);
        user.setGender(Gender);
        user.setRole("USER");
        user.setUid(uid);
        user.setCountry("Україна");

        UserManager.getInstance(context).saveUser(Login, Email, Password, Name, Birth, Gender, "USER");

        table.child(Login).setValue(user);
        saveDataSharedPreference(context, Login, Email);
        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context, R.string.error_db_saving, Toast.LENGTH_SHORT).show();
        }
    }
    public void saveUserData(Context context, Users user) {
        try{
            user.setRole("USER");
            user.setCountry("Україна");

            UserManager.getInstance(context).saveUser(user.getLogin(), user.getEmail(), user.getPassword(), user.getName(),
                    user.getBirthday(), user.getGender(), "USER");

            table.child(user.getLogin()).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Save user failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            saveDataSharedPreference(context, user.getLogin(), user.getEmail());
            Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context, R.string.error_db_saving, Toast.LENGTH_SHORT).show();
        }
    }

    public void ResetPassword(Context context,String email){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, R.string.reset_password_mail_send, Toast.LENGTH_SHORT).show();
                    ShowPages.showLoginForm(context);
                }
                else{
                    Toast.makeText(context, R.string.error_sending_mail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void authenticateUser(Context context, String email){
        Query query = table.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String loginFromDB = userSnapshot.getKey();
                        String nameFromDB = userSnapshot.child("name").getValue(String.class);
                        String dateFromDB = userSnapshot.child("birthday").getValue(String.class);
                        String genderFromDB = userSnapshot.child("gender").getValue(String.class);
                        String role = userSnapshot.child("role").getValue(String.class);
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        saveDataSharedPreference(context, loginFromDB, email);

                        UserManager.getInstance(context).saveUser(loginFromDB, email, passwordFromDB, nameFromDB, dateFromDB, genderFromDB, role);
                        Log.d("AuthCheck", "User is authenticated: "+loginFromDB);

                        ShowPages.showMainPage(context);
                        return;
                    }
                } else {
                    Toast.makeText(context, R.string.error_user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void authenticateUser(Context context, String email, TextInputLayout emailLayout,
                                 String password, TextInputLayout passLayout) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((activity) -> {
                    if (activity.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        if (user != null) {
                            Query query = table.orderByChild("email").equalTo(email);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                            String loginFromDB = userSnapshot.getKey();
                                            String nameFromDB = userSnapshot.child("name").getValue(String.class);
                                            String dateFromDB = userSnapshot.child("birthday").getValue(String.class);
                                            String genderFromDB = userSnapshot.child("gender").getValue(String.class);
                                            String role = userSnapshot.child("role").getValue(String.class);
                                            String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                                            saveDataSharedPreference(context, loginFromDB, email);

                                            UserManager.getInstance(context).saveUser(loginFromDB, email, passwordFromDB, nameFromDB, dateFromDB, genderFromDB, role);
                                            Log.d("AuthCheck", "User is authenticated: "+loginFromDB);
                                            ShowPages.showMainPage(context);
                                            return;
                                        }
                                    } else {
                                        Toast.makeText(context, R.string.error_user_not_found, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    } else {
                        // Ошибка аутентификации
                        String errorMessage = ((FirebaseAuthException) activity.getException()).getErrorCode();
                        handleAuthenticationError(errorMessage, emailLayout, passLayout, context);
                    }
                });
    }
    private void handleAuthenticationError(String errorCode, TextInputLayout emailLayout, TextInputLayout passLayout, Context context) {
        switch (errorCode) {
            case "ERROR_INVALID_EMAIL":
                emailLayout.setError(context.getString(R.string.error_logIn_email));
                emailLayout.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                passLayout.setError(context.getString(R.string.error_logIn_password));
                passLayout.requestFocus();
                break;
            case "ERROR_USER_NOT_FOUND":
                emailLayout.setError(context.getString(R.string.error_user_not_found));
                emailLayout.requestFocus();
                break;
            default:
                Toast.makeText(context, R.string.error_authentication, Toast.LENGTH_SHORT).show();
                break;
        }
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
        boolean isAuthenticated = firebaseUser != null;
        Log.d("AuthCheck", "User authenticated: " + isAuthenticated);
        return isAuthenticated;
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