package com.example.techgirls;

import static android.provider.Settings.System.getString;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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


public class MainPage extends AppCompatActivity {
    TextView welcomeText;
    FloatingActionButton addNewsbtn, settingsBtn;
    Button allThemeBtn;

    GridView gridView;
    ArrayList<NewsData> newsList;
    NewsAdapter adapter;
    LinearLayout buttonsLayout;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("News");
    @Override
    protected void onStart() {
        super.onStart();

        buttonsLayout=findViewById(R.id.themeButtonsLayout);
        createButtons(buttonsLayout);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        welcomeText = findViewById(R.id.welcomeText);

        String name= UserManager.getInstance(this).getName();
        name = name.replace(" ", "\n");
        String role=UserManager.getInstance(this).getRole();

        welcomeText.setText(String.format(getString(R.string.hello), name));

        addNewsbtn = findViewById(R.id.addNews_button);
        if (DatabaseManager.isEditor(role) || DatabaseManager.isAdmin(role)) {
            addNewsbtn.setVisibility(View.VISIBLE);
        } else addNewsbtn.setVisibility(View.INVISIBLE);

        settingsBtn=findViewById(R.id.settings_button);
        allThemeBtn=findViewById(R.id.button_All);

        gridView = findViewById(R.id.gridView);
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(newsList, this);
        gridView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
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
        addNewsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showUploadNews(v.getContext());
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsData newsData = newsList.get(position);

                Intent intent = new Intent(MainPage.this, NewsActivity.class);
                SharedData.putNewsInfo(intent,newsData);
                startActivity(intent);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showSettings(MainPage.this);
            }
        });
        allThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllNews();
            }
        });
    }
    public void createButtons(LinearLayout layout){
        String[] itemThemes = SharedData.itemThemes;

        for (String theme : itemThemes) {
            checkNewsExistenceAndCreateButton(theme, layout);
        }
    }

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

