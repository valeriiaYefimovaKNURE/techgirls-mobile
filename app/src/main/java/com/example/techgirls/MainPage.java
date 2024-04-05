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
import com.example.techgirls.Models.NewsData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    TextView welcomeText;
    FloatingActionButton addNewsbtn;

    GridView gridView;
    ArrayList<NewsData> newsList;
    NewsAdapter adapter;
    LinearLayout buttonsLayout;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("News");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        buttonsLayout=findViewById(R.id.themeButtonsLayout);
        createButtons(buttonsLayout);

        welcomeText = findViewById(R.id.welcomeText);

        String name=SharedData.getUserName(this);
        String role=SharedData.getUserRole(this);
        String email=SharedData.getUserEmail(this);
        String login=SharedData.getUserLogin(this);
        String gender=SharedData.getUserGender(this);
        String birthday=SharedData.getUserBirthday(this);
        String password=SharedData.getUserPassword(this);

        welcomeText.setText(String.format(getString(R.string.hello), name));

        addNewsbtn = findViewById(R.id.addNews_button);
        if (DatabaseManager.isEditor(role) || DatabaseManager.isAdmin(role)) {
            addNewsbtn.setVisibility(View.VISIBLE);
        } else addNewsbtn.setVisibility(View.INVISIBLE);

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
        SharedData.putUserInfo(this,name,email,login,birthday,gender,password,role);
    }
    public void createButtons(LinearLayout layout){
        String[] itemThemes = SharedData.itemThemes;

        for (String theme : itemThemes) {
            MaterialButton button = new MaterialButton(this);

            button.setText(theme);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            params.weight = 1;
            button.setLayoutParams(params);

            layout.addView(button);
        }
    }
}

