package com.example.techgirls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Activity for user login.
 */
public class LoginPage extends AppCompatActivity {
    // TextInputLayouts for login and password fields
    private TextInputLayout passwordLayout, loginLayout;

    // EditText fields for login and password
    private EditText loginField, passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Find TextInputLayouts and EditText fields in the layout
        loginLayout =findViewById(R.id.outlinedLoginFieldLogIn);
        passwordLayout=findViewById(R.id.outlinedPasswordFieldLogIn);

        loginField=findViewById(R.id.logIn_emailText);
        passwordField=findViewById(R.id.logIn_passwordText);

        // Find the login button in the layout
        Button btnLogin = findViewById(R.id.btnLogin2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of DatabaseManager
                DatabaseManager databaseManager=new DatabaseManager();

                // Get login and password from EditText fields
                String login=loginField.getText().toString().trim();
                String password=passwordField.getText().toString().trim();

                // Validate login and password fields using ValidationManager
                if (ValidationManager.validateLogin(login,loginLayout) |
                        ValidationManager.validatePassword(password,passwordLayout)) {
                    // Authenticate user using DatabaseManager
                    databaseManager.authenticateUser(LoginPage.this,login,loginLayout,password, passwordLayout);
                }
            }
        });
        // Find the back button in the layout
        ImageButton backBtn=findViewById(R.id.login_btnClose);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showWelcomePage(v.getContext());
            }
        });
    }
}
