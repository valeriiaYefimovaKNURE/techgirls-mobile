package com.example.techgirls.ThreadsClasses;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.Pages.MainPage;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoadButtonsTask implements Runnable {
    private final DatabaseReference databaseReference;
    private final LinearLayout layout;
    private final Handler mainHandler;
    private final MainPage mainPage;

    public LoadButtonsTask(MainPage mainPage, DatabaseReference databaseReference, LinearLayout layout, Handler mainHandler) {
        this.mainPage = mainPage;
        this.databaseReference = databaseReference;
        this.layout = layout;
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        String[] themes = SharedData.itemThemes;
        if (themes != null) {
            for (String theme : themes) {
                checkNewsExistenceAndCreateButton(theme);
            }
        }
    }

    private void checkNewsExistenceAndCreateButton(String theme) {
        databaseReference.orderByChild("dataTheme").equalTo(theme).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mainHandler.post(() -> createButton(theme));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void createButton(String theme) {
        MaterialButton button = new MaterialButton(layout.getContext());
        button.setText(theme);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 0, 0, 0);
        params.weight = 1;
        button.setLayoutParams(params);

        button.setOnClickListener(v -> mainPage.displayNewsByTheme(theme));
        layout.addView(button);
    }
}
