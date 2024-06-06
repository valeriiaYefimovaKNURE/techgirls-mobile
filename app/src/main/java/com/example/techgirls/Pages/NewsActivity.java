package com.example.techgirls.Pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.RegistrationClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.RegistrationClasses.UserManager;
import com.example.techgirls.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NewsActivity extends AppCompatActivity {
    // ImageView for settings button
    ImageView settingsBtn;

    // Key and image URL of the news item
    String key="";
    String imageUrl="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        // Find views in the layout
        ImageView backBtn=findViewById(R.id.back_button);
        settingsBtn=findViewById(R.id.settingsNews_button);

        ImageView imageView=findViewById(R.id.image);
        TextView titleView=findViewById(R.id.title);
        TextView captionView=findViewById(R.id.caption);
        TextView textView=findViewById(R.id.text);
        TextView linkView=findViewById(R.id.link);
        TextView themeView=findViewById(R.id.themes);

        // Get user's role
        String role= UserManager.getInstance(this).getRole();

        // Set news data to views
        SharedData.getNewsData(this,imageView,titleView,captionView,textView,linkView,themeView);

        // Set visibility of settings button based on user's role
        if (DatabaseManager.isEditor(role) || DatabaseManager.isAdmin(role)) {
            settingsBtn.setVisibility(View.VISIBLE);
        } else settingsBtn.setVisibility(View.INVISIBLE);

        // Register settings button for context menu
        registerForContextMenu(settingsBtn);

        // Back button click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });

        // Get news item key and image URL from intent
        key=getIntent().getStringExtra("Key");
        imageUrl=getIntent().getStringExtra("Image");

        // Settings button click listener
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show popup menu for settings button
                PopupMenu popupMenu=new PopupMenu(NewsActivity.this,settingsBtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_news_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id==R.id.item_1){
                            // Edit news item
                            //get key from database
                            try{
                            Intent intent = new Intent(NewsActivity.this, NewsUpdate.class);
                            intent.putExtra("Key", key);
                            startActivity(intent);
                            }
                            catch (Exception e){
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                                builder.setMessage("Виникла помилка.")
                                        .setCancelable(true)
                                        .show();
                            }
                        }
                        else if(id==R.id.item_2){
                            // Delete news item
                            try{
                                DeleteNews();
                            }
                            catch (Exception e){
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                                builder.setMessage("Виникла помилка.")
                                        .setCancelable(true)
                                        .show();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    /**
     * Deletes the news item.
     */
    private void DeleteNews(){
        // Create confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ви точно хочете видалити цей запис?")
                .setCancelable(false)
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete news item from database and storage
                        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("News");
                        FirebaseStorage storage=FirebaseStorage.getInstance();
                        StorageReference storageReference=storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Delete news item reference from database
                                reference.child(key).removeValue();
                                Toast.makeText(NewsActivity.this,"Видалено",Toast.LENGTH_SHORT).show();

                                // Navigate to main page
                                ShowPages.showMainPage(NewsActivity.this);
                            }
                        });
                    }
                })
                .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
