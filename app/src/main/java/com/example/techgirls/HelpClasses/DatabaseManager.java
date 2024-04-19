package com.example.techgirls.HelpClasses;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.techgirls.MainPage;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;
import com.google.android.material.textfield.TextInputLayout;
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

    /**
     * Empty constructor for initialization the DatabaseManager and sets up a reference to the "Users" table in the Firebase Realtime Database.
     */
    public DatabaseManager() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        table = db.getReference("Users");

    }

    /**
     * Checks if an email already exists in the database.
     *
     * @param email      The email to check for existence.
     * @param layout     The layout where error messages will be displayed.
     * @param validEmail A boolean flag indicating if the email is valid.
     */
    public void checkEmailExistence(String email, TextInputLayout layout, AtomicBoolean validEmail) {
        table.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        validEmail.set(!dataSnapshot.exists());  //'true' if not in the db
                        if (!validEmail.get()) {    //not 'true'
                            layout.setError(layout.getContext().getString(R.string.error_email_exist));
                        } else {
                            layout.setError(null);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );
    }

    /**
     * Checks if a login already exists in the database.
     *
     * @param login      The login to check for existence.
     * @param layout     The layout where error messages will be displayed.
     * @param validLogin A boolean flag indicating if the login is valid.
     */
    public void checkLoginExistence(String login, TextInputLayout layout, AtomicBoolean validLogin) {
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
    public void registerUser(Context context,String Email, String Name, String Login, String Password, String Birth, String Gender) {
        Users user = new Users();
        user.setEmail(Email);
        user.setName(Name);
        user.setLogin(Login);
        user.setPassword(Password);
        user.setBirthday(Birth);
        user.setGender(Gender);
        user.setRole("USER");

        UserManager.getInstance(context).saveUser(Login, Email, Password, Name, Birth, Gender, "USER");

        table.child(Login).setValue(user);
        saveDataSharedPreference(context, Login, Email);
        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
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

                    if (Objects.equals(passwordFromDB, password)) {
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