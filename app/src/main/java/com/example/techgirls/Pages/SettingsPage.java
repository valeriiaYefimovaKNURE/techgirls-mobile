package com.example.techgirls.Pages;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.R;
import com.example.techgirls.RegistrationClasses.GoogleSignInHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Activity for managing user settings.
 */
public class SettingsPage extends AppCompatActivity {

    // Firebase authentication instance
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialize buttons and image view (button)
        Button logoutBtn = findViewById(R.id.logout_button);
        ImageView backBtn = findViewById(R.id.back_button);
        Button userBtn = findViewById(R.id.userInfo_button);

        mAuth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(SettingsPage.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        // Button listener for logging out
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(SettingsPage.this);
                dialog.setContentView(R.layout.card_view_logout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_card_bag));
                dialog.setCancelable(false);
                Button btnDialogCancel = dialog.findViewById(R.id.cardCancelButton);
                Button btnDialogNext = dialog.findViewById(R.id.cardNextButton);
                btnDialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDialogNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mAuth.signOut();
                                    // Clear shared preferences if needed
                                    SharedPreferences sh = getSharedPreferences("mePower_user_prefs", MODE_PRIVATE);
                                    sh.edit().clear().apply();
                                    sh = getSharedPreferences("mePowerLogin", MODE_PRIVATE);
                                    sh.edit().clear().apply();
                                    ShowPages.showWelcomePage(SettingsPage.this);
                                    finish();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        // Button listener for navigating back to sections
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button listener for navigating to user settings
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showUserSettings(SettingsPage.this);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
