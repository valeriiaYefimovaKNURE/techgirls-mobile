package com.example.techgirls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ShowPages;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public Button btnLogin, btnReg;
    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        /*databaseManager=new DatabaseManager();
        if(databaseManager.getUser()!=null){
            ShowPages.showMainPage(this);
        }*/


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
}