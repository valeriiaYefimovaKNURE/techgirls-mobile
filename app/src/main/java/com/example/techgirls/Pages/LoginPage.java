package com.example.techgirls.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.RegistrationClasses.DatabaseManager;
import com.example.techgirls.RegistrationClasses.GoogleSignInHelper;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.example.techgirls.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for user login.
 */
public class LoginPage extends AppCompatActivity implements GoogleSignInHelper.GoogleSignInListener {
    // TextInputLayouts for login and password fields
    private TextInputLayout passwordLayout, emailLayout;

    // EditText fields for login and password
    private EditText emailField, passwordField;
    private GoogleSignInHelper googleSignInHelper;
    private final int RC_SIGN_IN=40;
    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Find TextInputLayouts and EditText fields in the layout
        emailLayout =findViewById(R.id.outlinedLoginFieldLogIn);
        passwordLayout=findViewById(R.id.outlinedPasswordFieldLogIn);

        emailField =findViewById(R.id.logIn_emailText);
        passwordField=findViewById(R.id.logIn_passwordText);

        // Find the login button in the layout
        Button btnLogin = findViewById(R.id.btnLogin2);

        databaseManager=new DatabaseManager();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get email and password from EditText fields
                String email= emailField.getText().toString().trim();
                String password=passwordField.getText().toString().trim();

                // Validate email and password fields using ValidationManager
                if (ValidationManager.validateEmail(email, emailLayout) |
                        ValidationManager.validatePassword(password,passwordLayout)) {
                    // Authenticate user using DatabaseManager
                    databaseManager.authenticateUser(LoginPage.this,email, emailLayout,password, passwordLayout);
                }
            }
        });
        // Find the back button in the layout
        ImageButton backBtn=findViewById(R.id.login_btnClose);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btnGoogle=findViewById(R.id.btnRegGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInHelper = new GoogleSignInHelper(LoginPage.this, LoginPage.this);
                googleSignInHelper.signIn();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            googleSignInHelper.handleSignInResult(data);
        }
    }
    @Override
    public void onGoogleSignInSuccess(FirebaseUser user) {

    }
    @Override
    public void onGoogleSignInFailure(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
