package com.example.techgirls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ShowPages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public Button btnLogin, btnReg;
    @Override
    protected void onStart() {
        super.onStart();
        checkAccessToken();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        btnLogin=findViewById(R.id.btnLogIn);
        btnReg=findViewById(R.id.btnRegister1);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showLoginForm(v.getContext());
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showRegisterForm( v.getContext());
            }
        });
    }
    private void checkAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("mePowerLogin", Context.MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("loginCounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String login=sharedPreferences.getString("userLogin",String.valueOf(MODE_PRIVATE));
        String email=sharedPreferences.getString("userEmail",String.valueOf(MODE_PRIVATE));
        if(counter){
            ShowPages.showMainPage(this);
        }else{
            Toast.makeText(this,"Hi!",Toast.LENGTH_SHORT).show();
        }
    }
}