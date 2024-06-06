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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for managing user settings.
 */
public class SettingsPage extends AppCompatActivity {

    // Firebase authentication instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialize buttons and image view (button)
        Button logoutBtn = findViewById(R.id.logout_button);
        ImageView backBtn = findViewById(R.id.back_button);
        Button userBtn = findViewById(R.id.userInfo_button);

        // Button listener for logging out
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Firebase authentication instance
                mAuth=FirebaseAuth.getInstance();

                Dialog dialog = new Dialog(SettingsPage.this);
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
                        mAuth.signOut();
                        SharedPreferences sh=getSharedPreferences("mePowerLogin",MODE_PRIVATE);
                        sh.edit().clear().commit();
                        ShowPages.showWelcomePage(SettingsPage.this);
                        SettingsPage.this.finish();
                    }
                });
                dialog.show();
            }
        });

        // Button listener for navigating back to sections
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSections(SettingsPage.this);
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
}
