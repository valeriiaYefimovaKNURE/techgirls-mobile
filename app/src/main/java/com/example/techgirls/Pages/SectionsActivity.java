package com.example.techgirls.Pages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.R;

/**
 * Activity for displaying sections.
 */
public class SectionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dep_page);

        // Buttons for navigating to different sections
        Button helpBtn = findViewById(R.id.helpCenter_button);
        Button settingsBtn = findViewById(R.id.settingsDep_button);
        Button aboutAppBtn = findViewById(R.id.aboutApp_button);

        //Find button for back to the main page
        ImageView backBtn = findViewById(R.id.back_button);

        // Set a click listener for the about app button to navigate to the about app page
        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showAboutAppPage(SectionsActivity.this);
            }
        });

        // Set a click listener for the back button to navigate back to the main page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set a click listener for the settings button to navigate to the settings section
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSettings(SectionsActivity.this);
            }
        });

        // Set a click listener for the help centres/hotlines button to navigate to the hotlines page
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showHelpCenter(SectionsActivity.this);
            }
        });
    }
}
