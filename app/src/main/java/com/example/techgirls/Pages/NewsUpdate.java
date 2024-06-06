package com.example.techgirls.Pages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Activity for updating news data.
 */
public class NewsUpdate extends AppCompatActivity {
    // Views
    ImageView updateImage;
    Button updateButton;
    ImageButton backButton;
    EditText updateTitle, updateCaption, updateText, updateLink;
    AutoCompleteTextView autoCompleteTextView;

    // Data
    String title, caption, text, link, theme;
    String imageUrl, oldImageUrl;
    String key;
    Uri uri;

    // Firebase
    DatabaseReference databaseReference;
    StorageReference storageReference;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_update);

        // Initialize views
        updateImage=findViewById(R.id.updateImage);
        updateButton=findViewById(R.id.updateButton);
        updateTitle=findViewById(R.id.updateTitle);
        updateCaption=findViewById(R.id.updateCaption);
        updateText=findViewById(R.id.updateText);
        updateLink=findViewById(R.id.updateLink);
        autoCompleteTextView=findViewById(R.id.uploadTheme);
        backButton=findViewById(R.id.updateButton_close);
        TextInputLayout themeLayout=findViewById(R.id.outlinedThemeField);

        // Register activity result launcher
        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data=result.getData();
                            uri=data.getData();
                            updateImage.setImageURI(uri);
                        }else Toast.makeText(NewsUpdate.this,"No image selected",Toast.LENGTH_SHORT).show();
                    }
                });

        // Get news key from intent
        key=getIntent().getStringExtra("Key");

        // Get database reference for the news
        databaseReference= FirebaseDatabase.getInstance().getReference("News").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NewsData newsData = snapshot.getValue(NewsData.class);

                    // Populate views with news data
                    assert newsData != null;
                    updateTitle.setText(newsData.getDataTitle());
                    updateCaption.setText(newsData.getDataCaption());
                    updateText.setText(newsData.getDataText());
                    updateLink.setText(newsData.getDataLink());

                    // Populate theme AutoCompleteTextView
                    SharedData.themeAutoCompleteTextView(NewsUpdate.this,autoCompleteTextView);
                    if (SharedData.itemThemes.length > 0) {
                        autoCompleteTextView.setText(newsData.getDataTheme(), false);
                        autoCompleteTextView.setSelection(SharedData.itemThemes.length - 1);
                    } else {
                        autoCompleteTextView.setText(newsData.getDataTheme(), false);
                        themeLayout.setError("Вибачте, виникла помилка. Ви не зможете змінити тему.");
                    }

                    // Load image
                    imageUrl = newsData.getDataImage();
                    oldImageUrl = imageUrl;

                    Glide.with(NewsUpdate.this).load(imageUrl).into(updateImage);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewsUpdate.this);
                    builder.setMessage("Даних по цій записі немає").setCancelable(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsUpdate.this);
                builder.setMessage("Виникла помилка під час загрузки з бази даних").setCancelable(true);
            }
        });

        // Image click listener
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // Update button click listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(NewsUpdate.this,"Updated",Toast.LENGTH_SHORT).show();
            }
        });

        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
    }
    /**
     * Saves news data to Firebase.
     */
    public void saveData(){
        // Initialize storage reference
        storageReference= FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());

        // Show progress dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(NewsUpdate.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog= builder.create();
        dialog.show();

        // Upload image to storage
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get download URL
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageUrl=urlImage.toString();

                // Update data
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    /**
     * Updates news data in Firebase.
     */
    public void updateData(){
        // Get data from views
        title = updateTitle.getText().toString().trim();
        caption = updateCaption.getText().toString().trim();
        text = updateText.getText().toString().trim();
        link = updateLink.getText().toString().trim();
        theme = autoCompleteTextView.getText().toString().trim();

        // Create news data object
        NewsData newsData=new NewsData(title,caption,text,link,theme,imageUrl);
        newsData.setKey(key);

        // Update data in database
        databaseReference.setValue(newsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Delete old image
                    StorageReference reference=FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                    reference.delete();
                    Toast.makeText(NewsUpdate.this,"Оновлено",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewsUpdate.this,"Помилка",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
