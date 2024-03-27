package com.example.techgirls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.NewsAdapter;
import com.example.techgirls.Models.GridItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainPage extends AppCompatActivity {
    DatabaseManager databaseManager=new DatabaseManager();
    GridView newsGV;
    TextView welcomeText;
    FloatingActionButton addNewsbtn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        welcomeText=findViewById(R.id.welcomeText);
        String name = getIntent().getStringExtra("name");
        String role=getIntent().getStringExtra("role");

        if (Objects.requireNonNull(name).length() > 7) {
            welcomeText.setTextSize(20);
        }
        welcomeText.setText(String.format(getString(R.string.hello), name));

        addNewsbtn=findViewById(R.id.addNews_button);
        if(databaseManager.isEditor(role) || databaseManager.isAdmin(role)){
            addNewsbtn.setVisibility(View.VISIBLE);
        }
        else addNewsbtn.setVisibility(View.INVISIBLE);

        newsGV=findViewById(R.id.gridView);
        ArrayList<GridItem> newsArrayList=new ArrayList<GridItem>();

        NewsAdapter newsAdapter = new NewsAdapter(this, newsArrayList);
        newsGV.setAdapter(newsAdapter);

        addNewsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadNews(v);
            }
        });
    }
    public void showUploadNews(View v) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }
}
