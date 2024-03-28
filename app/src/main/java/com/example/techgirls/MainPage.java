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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.NewsAdapter;
import com.example.techgirls.Models.GridItem;
import com.example.techgirls.Models.NewsData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainPage extends AppCompatActivity {
    DatabaseManager databaseManager=new DatabaseManager();
    TextView welcomeText;
    FloatingActionButton addNewsbtn;

    GridView newsGV;
    ArrayList<NewsData> newsList;
    NewsAdapter adapter;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("News");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        welcomeText=findViewById(R.id.welcomeText);
        String name = getIntent().getStringExtra("name");
        String role=getIntent().getStringExtra("role");

        welcomeText.setText(String.format(getString(R.string.hello), name));

        addNewsbtn=findViewById(R.id.addNews_button);
        if(databaseManager.isEditor(role) || databaseManager.isAdmin(role)){
            addNewsbtn.setVisibility(View.VISIBLE);
        }
        else addNewsbtn.setVisibility(View.INVISIBLE);

        newsGV=findViewById(R.id.gridView);
        newsList=new ArrayList<>();
        adapter=new NewsAdapter(newsList,this);
        newsGV.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NewsData newsClass=dataSnapshot.getValue(NewsData.class);
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
                showUploadNews(v);
            }
        });
    }
    public void showUploadNews(View v) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }
}
