package com.example.techgirls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;

public class DepActivity extends AppCompatActivity {
    Button helpBtn, aboutAppBtn, settingsBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dep_page);

        helpBtn=findViewById(R.id.helpCenter_button);
        settingsBtn=findViewById(R.id.settingsDep_button);
        aboutAppBtn=findViewById(R.id.aboutApp_button);
        backBtn=findViewById(R.id.back_button);

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showAboutAppPage(DepActivity.this);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(DepActivity.this);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSettings(DepActivity.this);
            }
        });
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showHelpCenter(DepActivity.this);
            }
        });
    }
}
