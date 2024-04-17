package com.example.techgirls;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button logoutBtn, aboutAppBtn, userBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        logoutBtn=findViewById(R.id.logout_button);
        backBtn=findViewById(R.id.back_button);
        userBtn=findViewById(R.id.userInfo_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth=FirebaseAuth.getInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsPage.this);
                builder.setMessage("Ви впевнені що хочете вийти?")
                        .setCancelable(false)
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                SharedPreferences sh=getSharedPreferences("mePowerLogin",MODE_PRIVATE);
                                sh.edit().clear().commit();
                                ShowPages.showWelcomePage(SettingsPage.this);
                                SettingsPage.this.finish();
                            }
                        })
                        .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showDep(SettingsPage.this);
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showUserSettings(SettingsPage.this);
            }
        });
    }
}
