package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterPage extends AppCompatActivity {
    private TextInputLayout passwordLayout, emailLayout, nameLayout, loginLayout, birthdayLayout, genderLayout;
    private EditText email, name, login, birthday, gender, password;

    AutoCompleteTextView autoCompleteTextView;
    private AtomicBoolean isEmailValid = new AtomicBoolean(false);
    private AtomicBoolean isLoginValid = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        autoCompleteTextView =findViewById(R.id.signUp_list);
        SharedData.genderAutoCompleteTextView(this,autoCompleteTextView);

        emailLayout=findViewById(R.id.outlinedEmailField);
        passwordLayout=findViewById(R.id.outlinedPasswordField);
        nameLayout=findViewById(R.id.outlinedNameField);
        loginLayout=findViewById(R.id.outlinedLoginField);
        birthdayLayout=findViewById(R.id.outlinedBirthField);
        genderLayout=findViewById(R.id.outlinedGenderField);

        email=findViewById(R.id.signUp_emailText);
        name=findViewById(R.id.signUp_nameText);
        login=findViewById(R.id.signUp_loginText);
        password=findViewById(R.id.signUp_passwordText);
        birthday=findViewById(R.id.signUp_birthText);
        gender=autoCompleteTextView;

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String userName = name.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();
                    String userBirthday = birthday.getText().toString().trim();
                    String userGender = gender.getText().toString().trim();
                    String userEmail = email.getText().toString().trim();
                    String userLogin = login.getText().toString().trim();

                    if(ValidationManager.validateEmail(userEmail, emailLayout) && ValidationManager.validateLogin(userLogin,loginLayout)){
                        DatabaseManager databaseManager = new DatabaseManager();
                        databaseManager.checkEmailExistence(userEmail, emailLayout,isEmailValid);
                        databaseManager.checkLoginExistence(userLogin,loginLayout, isLoginValid);
                        if (isEmailValid.get() && isLoginValid.get()) {
                            if (ValidationManager.validateName(userName, nameLayout) && ValidationManager.validatePassword(userPassword, passwordLayout)
                                    && ValidationManager.validateDate(userBirthday,birthdayLayout) && ValidationManager.validateGender(userGender,genderLayout)) {
                                databaseManager.registerUser(RegisterPage.this,userEmail, userName, userLogin, userPassword, userBirthday, userGender);
                                ShowPages.showMainPage(v.getContext());
                            }
                        }
                    }
            }
        });
        ImageButton backButton=findViewById(R.id.signUp_btnClose);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showWelcomePage(v.getContext());
            }
        });
    }
}
