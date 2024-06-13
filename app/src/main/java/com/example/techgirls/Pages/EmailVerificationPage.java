package com.example.techgirls.Pages;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.R;
import com.example.techgirls.RegistrationClasses.DatabaseManager;

import java.util.Objects;

public class EmailVerificationPage extends AppCompatActivity {
    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_verification_email_page);

        String email=getIntent().getStringExtra("Email");
        String name=getIntent().getStringExtra("Name");
        String login=getIntent().getStringExtra("Login");
        String password=getIntent().getStringExtra("Password");
        String birth=getIntent().getStringExtra("Birth");
        String gender=getIntent().getStringExtra("Gender");
        databaseManager=new DatabaseManager();

        TextView textView=findViewById(R.id.textVer);
        textView.setText(String.format(getString(R.string.email_forverification),Objects.requireNonNullElse(email, "...")));

        Button resendBtn=findViewById(R.id.resendButton);
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager.resendEmailVerification(EmailVerificationPage.this);
            }
        });

        Button nextBtn=findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager.verifyEmail(EmailVerificationPage.this,email,name,login,password,birth,gender);
            }
        });

        ImageButton backButton = findViewById(R.id.signUp_btnClose);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager.deleteAuthUser(EmailVerificationPage.this);
                ShowPages.showRegisterForm(v.getContext());
            }
        });
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                databaseManager.deleteAuthUser(EmailVerificationPage.this);
            }
        });
    }
}
