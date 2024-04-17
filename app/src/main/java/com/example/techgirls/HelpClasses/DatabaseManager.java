package com.example.techgirls.HelpClasses;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.techgirls.MainPage;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseManager {
    private final DatabaseReference table;
    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;

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

        UserManager.getInstance(context).saveUser(Login,Email,Password,Name,Birth,Gender,"USER");
        saveDataSharedPreference(context,Login,Email);

        Toast.makeText(context, R.string.toast_signup_succes, Toast.LENGTH_SHORT).show();
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
    /*public void startWithGoogle(Context context,GoogleSignInClient mGoogleSignIn){
        auth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleSignIn= GoogleSignIn.getClient(context,gso);
    }
    public void firebaseAuth(String idToken) {
        AuthCredential credential=GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=auth.getCurrentUser();

                    HashMap<String,Object> map=new HashMap<>();
                    map.put("id",user.getUid());
                    map.put("name",user.getDisplayName());

                }
            }
        });
    }*/
    public static boolean isEditor(String role){
        return role != null && (role.equals("EDITOR"));
    }
    public static boolean isAdmin(String role){
        return role != null && (role.equals("ADMIN"));
    }
    public FirebaseUser getUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public static void saveDataSharedPreference(Context context,String login, String email){
        SharedPreferences sh=context.getSharedPreferences("mePowerLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putBoolean("loginCounter",true);
        editor.putString("userLogin",login);
        editor.putString("userEmail",email);
        editor.apply();
    }
}
