package com.example.techgirls.Pages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.R;

/**
 * Activity displaying information about the application.
 */
public class AboutAppPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);

        // Initialize the back button in the layout
        ImageView backBtn = findViewById(R.id.backAbout_button);

        // Set a click listener for the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSections(AboutAppPage.this);
            }
        });
    }
}
