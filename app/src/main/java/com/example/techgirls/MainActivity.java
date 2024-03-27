package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button btnLogin, btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        /*if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,MainPage.class));
        }*/

        btnLogin=findViewById(R.id.btnLogIn);
        btnReg=findViewById(R.id.btnRegister1);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginForm(v);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterForm(v);
            }
        });

    }
    public void showRegisterForm(View v) {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
    public void showLoginForm(View v) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}