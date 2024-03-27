package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterPage extends AppCompatActivity {
    private TextInputLayout passwordLayout, emailLayout, nameLayout, loginLayout, birthdayLayout, genderLayout;
    private EditText email, name, login, birthday, gender, password;

    private final String[] item={"Male","Female","Uknown"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private AtomicBoolean isEmailValid = new AtomicBoolean(false);
    private AtomicBoolean isLoginValid = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        autoCompleteTextView =findViewById(R.id.signUp_list);
        adapterItems=new ArrayAdapter<String>(this,R.layout.item_list,item);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String item=parent.getItemAtPosition(i).toString();
                Toast.makeText(RegisterPage.this,"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

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
                                showMainPage();
                            }
                        }
                    }
            }
        });
    }
    public void showMainPage() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
    public void signBackToWelcomePage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
