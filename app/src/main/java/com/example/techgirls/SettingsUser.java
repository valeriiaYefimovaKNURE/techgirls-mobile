package com.example.techgirls;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.UserManager;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * Activity for managing user settings.
 */
public class SettingsUser extends AppCompatActivity {

    // Firebase database reference
    private DatabaseReference reference;

    // EditText fields for user information
    private EditText nameView, loginView, emailView, passwordView, birthView;
    private TextInputLayout passwordLayout, emailLayout, birthLayout, loginLayout;

    // Strings to store user information
    private String name, login, email, password, birthday, gender;

    // AutoCompleteTextView for selecting gender
    private AutoCompleteTextView genderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);

        // Initialize Firebase database reference
        reference= FirebaseDatabase.getInstance().getReference("Users");
        ImageView backBtn = findViewById(R.id.backUser_button);
        Button changeBtn = findViewById(R.id.change_button);

        // Initialize views
        nameView=findViewById(R.id.user_name);
        loginView=findViewById(R.id.user_login);
        emailView=findViewById(R.id.user_email);
        passwordView=findViewById(R.id.user_password);
        birthView=findViewById(R.id.user_birthday);
        genderView=findViewById(R.id.user_gender);
        SharedData.genderAutoCompleteTextView(this,genderView);

        // Initialize TextInputLayouts
        passwordLayout=findViewById(R.id.user_password_layout);
        emailLayout=findViewById(R.id.user_email_layout);
        birthLayout=findViewById(R.id.user_birthday_layout);
        loginLayout=findViewById(R.id.user_login_layout);

        // Load user information from UserManager
        name= UserManager.getInstance(this).getName();
        login=UserManager.getInstance(this).getLogin();
        email=UserManager.getInstance(this).getEmail();
        password=UserManager.getInstance(this).getPassword();
        birthday=UserManager.getInstance(this).getBirthday();
        gender=UserManager.getInstance(this).getGender();

        nameView.setText(name);
        loginView.setText(login);
        emailView.setText(email);
        birthView.setText(birthday);
        genderView.setText(gender,false);
        genderView.setSelection(SharedData.itemGender.length);

        // Button listener for updating user information
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update user information based on changes made by the user

                    HashMap<String, Object> obj = new HashMap<>();
                    if (isNameChanged()) {
                        obj.put("name", nameView.getText().toString());
                    }
                    if (isBirthdayChanged()) {
                        obj.put("birthday", birthView.getText().toString());
                    }
                    if (isEmailChanged()) {
                        obj.put("email", emailView.getText().toString());
                    }
                    if (isGenderChanged()) {
                        obj.put("gender", genderView.getText().toString());
                    }
                    if (isLoginChanged()) {
                        obj.put("login", loginView.getText().toString());
                    }
                    if (isPasswordChanged()) {
                        obj.put("password", passwordView.getText().toString());
                    }

                // Update user information in the database
                if (!obj.isEmpty()) {
                    reference.child(login).updateChildren(obj);
                    if (isEmailChanged() || isLoginChanged()) {
                        DatabaseManager.saveDataSharedPreference(SettingsUser.this, loginView.getText().toString(), emailView.getText().toString());
                    }
                    Toast.makeText(SettingsUser.this, "Дані збережено", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button listener for navigating back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSettings(SettingsUser.this);
            }
        });
    }

    // Check if the name has been changed
    public boolean isNameChanged(){
        if(!name.equals(nameView.getText().toString())){
            name=nameView.getText().toString();
            UserManager.getInstance(this).setName(name);
            return true;
        }
        else return false;
    }

    // Check if the login has been changed
    public boolean isLoginChanged(){
        if(!login.equals(loginView.getText().toString())){
            if(ValidationManager.validateLogin(loginView.getText().toString(),loginLayout)){
            login=loginView.getText().toString();
            UserManager.getInstance(this).setLogin(login);
            return true;
            }
            else {
                loginLayout.setError(getString(R.string.error_name));
                return false;
            }
        }
        else return false;
    }

    // Check if the email has been changed
    public boolean isEmailChanged(){
        if(!email.equals(emailView.getText().toString()) ){
            if(ValidationManager.validateEmail(emailView.getText().toString(),emailLayout)){
                email=emailView.getText().toString();
                UserManager.getInstance(this).setEmail(email);
                return true;
            }
            else{
                emailLayout.setError(getString(R.string.error_email));
                return false;
            }
        }
        else return false;
    }

    // Check if the birthday has been changed
    public boolean isBirthdayChanged(){
        if(!birthday.equals(birthView.getText().toString())){
            if(ValidationManager.validateDate(birthView.getText().toString(),birthLayout)){
                birthday=birthView.getText().toString();
                UserManager.getInstance(this).setBirthday(birthday);
                return true;
            }
            else {
                birthLayout.setError(getString(R.string.error_date));
                return false;
            }
        }
        else return false;
    }

    // Check if the gender has been changed
    public boolean isGenderChanged(){
        if(!gender.equals(genderView.getText().toString())){
            gender=genderView.getText().toString();
            UserManager.getInstance(this).setGender(gender);
            return true;
        }
        else return false;
    }

    // Check if the password has been changed
    public boolean isPasswordChanged(){
        if(!passwordView.getText().toString().isEmpty()){
            if(ValidationManager.validatePassword(passwordView.getText().toString(),passwordLayout)){
                gender=genderView.getText().toString();
                UserManager.getInstance(this).setPassword(password);
                return true;
            }else {
                passwordLayout.setError(getString(R.string.error_password));
                return false;
            }
        }
        else return false;
    }
}
