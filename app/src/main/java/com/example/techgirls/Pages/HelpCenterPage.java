package com.example.techgirls.Pages;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.hotlinesAdapter;
import com.example.techgirls.Models.hotlinesModel;
import com.example.techgirls.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity displaying the help center page with hotlines information.
 */
public class HelpCenterPage extends AppCompatActivity {

    // List to hold hotline data
    private List<hotlinesModel> dataList;

    // Listener to listen for changes in the database reference
    ValueEventListener eventListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_centras_page);

        // Initialize the back button in the layout
        ImageView backBtn = findViewById(R.id.back_button);

        // Initialize the RecyclerView in the layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(HelpCenterPage.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Show progress dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog=builder.create();
        dialog.show();

        // Initialize the dataList
        dataList=new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView
        hotlinesAdapter adapter=new hotlinesAdapter(dataList);
        recyclerView.setAdapter(adapter);

        // Get a reference to the "Hotlines" node in the Firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotlines");
        dialog.show();

        // Attach a ValueEventListener to retrieve data from the database
        eventListener= databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the dataList to avoid duplication
                dataList.clear();

                // Iterate through the dataSnapshot and add hotlinesModel objects to the dataList
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    hotlinesModel models=itemSnapshot.getValue(hotlinesModel.class);
                    dataList.add(models);
                }
                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();

                // Dismiss the progress dialog
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Dismiss the progress dialog
                dialog.dismiss();
            }
        });

        // Set a click listener for the back button to navigate back to the sections page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
