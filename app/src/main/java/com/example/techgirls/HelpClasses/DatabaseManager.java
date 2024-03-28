package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.example.techgirls.MainPage;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseManager {
    private final DatabaseReference table;

    public DatabaseManager() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        table = db.getReference("Users");

    }
    public void checkEmailExistence(String email, TextInputLayout layout, AtomicBoolean validEmail) {
        table.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        validEmail.set(!dataSnapshot.exists());  //'true' если нет в базе
                        if (!validEmail.get()) {    //не 'true'
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
    public void registerUser(Context context,String Email, String Name, String Login, String Password, String Birth, String Gender) {
        Users user = new Users();
        user.setEmail(Email);
        user.setName(Name);
        user.setLogin(Login);
        user.setPassword(Password);
        user.setBirthday(Birth);
        user.setGender(Gender);
        user.setRole("USER");

        table.child(Login).setValue(user);

        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
    }
    public void registerUserGoogle(Context context,String Email, String Name, String Login, String Password, String Birth, String Gender){

    }
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

                        Intent intent = new Intent(context, MainPage.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("login", loginFromDB);
                        intent.putExtra("birthday", dateFromDB);
                        intent.putExtra("gender", genderFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("role",role);
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
    public boolean isEditor(String role){
        return role != null && (role.equals("EDITOR"));
    }
    public boolean isAdmin(String role){
        return role != null && (role.equals("ADMIN"));
    }
}
