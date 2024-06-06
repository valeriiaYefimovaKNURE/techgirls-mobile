package com.example.techgirls.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.techgirls.RegistrationClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.R;

/**
 * Activity displayed to users when they open the app.
 * Allows users to login or register.
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        // Check if the user is already logged in
        checkAccessToken();
        if(DatabaseManager.checkCurrentUserAuth()){
            ShowPages.showMainPage(this);
        }

        // Initialize buttons
        Button btnLogin = findViewById(R.id.btnLogIn);
        Button btnReg = findViewById(R.id.btnRegister1);

        // Set click listeners for login and register buttons
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

    /**
     * Check if the user is already logged in.
     * If logged in, directly navigate to the main page.
     */
    private void checkAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("mePowerLogin", Context.MODE_PRIVATE);
        boolean counter=sharedPreferences.getBoolean("loginCounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        if(counter){
            // User is logged in, navigate to the main page
            ShowPages.showMainPage(this);
        }
    }
}