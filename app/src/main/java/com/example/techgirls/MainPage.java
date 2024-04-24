package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.NewsAdapter;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.UserManager;
import com.example.techgirls.Models.NewsData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Activity displaying the main page of the application.
 */
public class MainPage extends AppCompatActivity {
    // Flag to indicate if it's the first load of the page
    private boolean isFirstLoad = true;

    // List to hold news data
    private ArrayList<NewsData> newsList;

    // Adapter for the GridView
    private NewsAdapter adapter;

    // Reference to the Firebase database
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("News");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // TextView to display a welcome message
        TextView welcomeText = findViewById(R.id.welcomeText);

        //Get name of user
        String name= UserManager.getInstance(this).getName();
        name = name.replace(" ", "\n");
        String role=UserManager.getInstance(this).getRole();

        welcomeText.setText(String.format(getString(R.string.hello), name));

        // Buttons to add news and go to settings
        FloatingActionButton addNewsbtn = findViewById(R.id.addNews_button);
        if (DatabaseManager.isEditor(role) || DatabaseManager.isAdmin(role)) {
            addNewsbtn.setVisibility(View.VISIBLE);
        } else addNewsbtn.setVisibility(View.INVISIBLE);

        FloatingActionButton settingsBtn = findViewById(R.id.settings_button);
        // Button to display all news
        Button allThemeBtn = findViewById(R.id.button_All);

        // Layout for theme buttons
        LinearLayout buttonsLayout = findViewById(R.id.themeButtonsLayout);
        createButtons(buttonsLayout);

        // GridView to display news items
        GridView gridView = findViewById(R.id.gridView);
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(newsList, this);
        gridView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NewsData newsClass = dataSnapshot.getValue(NewsData.class);
                    newsClass.setKey(dataSnapshot.getKey());
                    newsList.add(newsClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add news button listener
        addNewsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showUploadNews(v.getContext());
            }
        });

        // GridView item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsData newsData = newsList.get(position);

                Intent intent = new Intent(MainPage.this, NewsActivity.class);
                SharedData.putNewsInfo(intent,newsData);
                startActivity(intent);
            }
        });

        // Settings button listener
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSections(MainPage.this);
            }
        });

        // All theme button listener
        allThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllNews();
            }
        });
    }

    /**
     * Creates buttons for each theme.
     *
     * @param layout The layout to add the buttons to.
     */
    private void createButtons(LinearLayout layout){
        String[] itemThemes = SharedData.itemThemes;

        for (String theme : itemThemes) {
            checkNewsExistenceAndCreateButton(theme, layout);
        }
    }

    /**
     * Checks if news exists for a given theme and creates a button if it does.
     *
     * @param theme  The theme to check.
     * @param layout The layout to add the button to.
     */
    private void checkNewsExistenceAndCreateButton(String theme, LinearLayout layout) {
        Query query = databaseReference.orderByChild("dataTheme").equalTo(theme);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    createButton(theme, layout);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Creates a button for a given theme.
     *
     * @param theme  The theme for the button.
     * @param layout The layout to add the button to.
     */
    private void createButton(String theme, LinearLayout layout) {
        MaterialButton button = new MaterialButton(this);
        button.setText(theme);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(12, 0,0, 0);
        params.weight = 1;
        button.setLayoutParams(params);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNewsByTheme(theme);
            }
        });
        params.setMarginEnd(12);
        layout.addView(button);
    }

    /**
     * Displays news filtered by a specific theme.
     *
     * @param theme The theme to filter by.
     */
    private void displayNewsByTheme(String theme) {
        newsList.clear();
        Query query = databaseReference.orderByChild("dataTheme").equalTo(theme);
        ((Query) query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NewsData newsClass = dataSnapshot.getValue(NewsData.class);
                    newsList.add(newsClass);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Displays all news.
     */
    private void displayAllNews() {
        newsList.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NewsData newsClass = dataSnapshot.getValue(NewsData.class);
                    newsList.add(newsClass);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}