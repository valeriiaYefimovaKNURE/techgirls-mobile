package com.example.techgirls.ThreadsClasses;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.techgirls.HelpClasses.NewsAdapter;
import com.example.techgirls.Models.NewsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoadNewsTask implements Runnable {
    private final DatabaseReference databaseReference;
    private final List<NewsData> newsList;
    private final NewsAdapter adapter;
    private final Handler mainHandler;

    public LoadNewsTask(DatabaseReference databaseReference, List<NewsData> newsList, NewsAdapter adapter, Handler mainHandler) {
        this.databaseReference = databaseReference;
        this.newsList = newsList;
        this.adapter = adapter;
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NewsData newsClass = dataSnapshot.getValue(NewsData.class);
                    if (newsClass != null) {
                        newsClass.setKey(dataSnapshot.getKey());
                        newsList.add(newsClass);
                    }
                }
                mainHandler.post(adapter::notifyDataSetChanged);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
