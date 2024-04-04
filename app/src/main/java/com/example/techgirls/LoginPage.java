package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    private TextInputLayout passwordLayout, loginLayout;
    private EditText loginField, passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginLayout =findViewById(R.id.outlinedLoginFieldLogIn);
        passwordLayout=findViewById(R.id.outlinedPasswordFieldLogIn);

        loginField=findViewById(R.id.logIn_emailText);
        passwordField=findViewById(R.id.logIn_passwordText);

        Button btnLogin = findViewById(R.id.btnLogin2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager databaseManager=new DatabaseManager();

                String login=loginField.getText().toString().trim();
                String password=passwordField.getText().toString().trim();

                if (ValidationManager.validateLogin(login,loginLayout) |
                        ValidationManager.validatePassword(password,passwordLayout)) {
                    databaseManager.authenticateUser(LoginPage.this,login,loginLayout,password, passwordLayout);
                }
            }
        });
        ImageButton backBtn=findViewById(R.id.login_btnClose);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showWelcomePage(v.getContext());
            }
        });
    }
}
