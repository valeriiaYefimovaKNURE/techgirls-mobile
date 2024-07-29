package com.example.techgirls.Pages;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DateFormattingTextWatcher;
import com.example.techgirls.RegistrationClasses.DatabaseManager;
import com.example.techgirls.RegistrationClasses.GoogleSignInHelper;
import com.example.techgirls.RegistrationClasses.HashingClass;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Activity for user registration.
 */
public class RegisterPage extends AppCompatActivity implements GoogleSignInHelper.GoogleSignInListener {
    // TextInputLayouts for various input fields
    private TextInputLayout passwordLayout, emailLayout, nameLayout, loginLayout, birthdayLayout, genderLayout;

    // EditText fields for user input
    private EditText email, name, login, birthday, gender, password;
    private DatabaseManager databaseManager;
    private GoogleSignInHelper googleSignInHelper;
    private final int RC_SIGN_IN=40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // AutoCompleteTextView for selecting gender
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.signUp_list);
        SharedData.genderAutoCompleteTextView(this, autoCompleteTextView);

        // Initialize views
        emailLayout = findViewById(R.id.outlinedEmailField);
        passwordLayout = findViewById(R.id.outlinedPasswordField);
        nameLayout = findViewById(R.id.outlinedNameField);
        loginLayout = findViewById(R.id.outlinedLoginField);
        birthdayLayout = findViewById(R.id.outlinedBirthField);
        genderLayout = findViewById(R.id.outlinedGenderField);

        email = findViewById(R.id.signUp_emailText);
        name = findViewById(R.id.signUp_nameText);
        login = findViewById(R.id.signUp_loginText);
        password = findViewById(R.id.signUp_passwordText);
        birthday = findViewById(R.id.signUp_birthText);
        birthday.addTextChangedListener(new DateFormattingTextWatcher());

        gender = autoCompleteTextView;

        databaseManager = new DatabaseManager();
        // Button for registration
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from EditText fields
                String userName = name.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userBirthday = birthday.getText().toString().trim();
                String userGender = gender.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userLogin = login.getText().toString().trim();

                // Validate email and login using ValidationManager
                if (ValidationManager.validateEmail(userEmail, emailLayout) && ValidationManager.validateLogin(userLogin, loginLayout)) {

                    if (databaseManager.checkLoginExistence(userLogin, loginLayout).get()) {
                        // Validate other fields
                        if (ValidationManager.validateName(userName, nameLayout) && ValidationManager.validatePassword(userPassword, passwordLayout)
                                && ValidationManager.validateDate(userBirthday, birthdayLayout) && ValidationManager.validateGender(userGender, genderLayout)) {

                            //Registers a user
                            databaseManager.registerUser(RegisterPage.this, userEmail,userPassword);
                            Intent intent = new Intent(RegisterPage.this, EmailVerificationPage.class);
                            SharedData.putUserInfo(intent,userEmail,userName,userLogin,userPassword,userBirthday,userGender);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        // Button for navigating back
        ImageButton backButton = findViewById(R.id.signUp_btnClose);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button for Google Sign-In
        Button btnGoogle=findViewById(R.id.btnRegGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInHelper = new GoogleSignInHelper(RegisterPage.this, RegisterPage.this, RC_SIGN_IN);
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

    public void showDialogForAdditionalInfo(FirebaseUser user) {
        Dialog dialog = new Dialog(RegisterPage.this);
        dialog.setContentView(R.layout.card_view_registration);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_card_bag));
        dialog.setCancelable(false);

        // Initialize dialog views
        AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.card_signUp_list);
        SharedData.genderAutoCompleteTextView(dialog.getContext(), autoCompleteTextView);

        TextInputLayout cardPasswordLayout = dialog.findViewById(R.id.card_outlinedPasswordField);
        TextInputLayout cardLoginLayout = dialog.findViewById(R.id.card_outlinedLoginField);
        TextInputLayout cardBirthdayLayout = dialog.findViewById(R.id.card_outlinedBirthField);
        TextInputLayout cardGenderLayout = dialog.findViewById(R.id.card_outlinedGenderField);

        EditText cardLogin = dialog.findViewById(R.id.card_signUp_loginText);
        EditText cardBirthday = dialog.findViewById(R.id.card_signUp_birthText);
        EditText cardPassword = dialog.findViewById(R.id.card_signUp_passwordText);

        Button btnDialogCancel = dialog.findViewById(R.id.cardCancelButton);
        Button btnDialogNext = dialog.findViewById(R.id.cardNextButton);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Sign out the user from Google and Firebase
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(RegisterPage.this,
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                    FirebaseAuth.getInstance().signOut();
                    ShowPages.showRegisterForm(RegisterPage.this);
                });
            }
        });

        btnDialogNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = cardLogin.getText().toString().trim();
                String birthday = cardBirthday.getText().toString().trim();
                String gender = autoCompleteTextView.getText().toString().trim();
                String password = cardPassword.getText().toString().trim();

                if (ValidationManager.validateLogin(login, cardLoginLayout)) {
                    if (databaseManager.checkLoginExistence(login, cardLoginLayout).get()) {
                        if (ValidationManager.validatePassword(password, cardPasswordLayout)
                                && ValidationManager.validateDate(birthday, cardBirthdayLayout)
                                && ValidationManager.validateGender(gender, cardGenderLayout)) {
                            try{
                                Users users = new Users();
                                users.setName(user.getDisplayName());
                                users.setEmail(user.getEmail());
                                users.setLogin(login);
                                users.setGender(gender);
                                users.setPassword(HashingClass.hashPassword(password));
                                users.setBirthday(birthday);
                                databaseManager.saveUserData(RegisterPage.this, users);
                                dialog.dismiss();
                                ShowPages.showMainPage(v.getContext());
                            }
                            catch (Exception e){
                                Toast.makeText(RegisterPage.this, "Save user failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        dialog.show();
    }
}
