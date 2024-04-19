package com.example.techgirls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
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

import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Models.NewsData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Activity for uploading news to Firebase.
 */
public class UploadActivity extends AppCompatActivity {

    // Firebase database reference
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("News");

    // Firebase storage reference
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    // Views
    private ImageView uploadImage;
    private EditText uploadTitle, uploadCaption, uploadText, uploadLink, uploadTheme;

    // Uri for storing the selected image
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.news_add);

        // Initialize views
        Button saveButton = findViewById(R.id.uploadButton_save);
        ImageButton backButton = findViewById(R.id.uploadButton_close);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.uploadTheme);
        // Set up AutoCompleteTextView for selecting news theme
        SharedData.themeAutoCompleteTextView(this, autoCompleteTextView);

        uploadImage = findViewById(R.id.uploadImage);
        uploadTitle = findViewById(R.id.uploadTitle);
        uploadCaption = findViewById(R.id.uploadCaption);
        uploadText = findViewById(R.id.uploadText);
        uploadLink = findViewById(R.id.uploadLink);
        uploadTheme = autoCompleteTextView;

        // Register activity result launcher for selecting image from gallery
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(UploadActivity.this, "Нічого не обрано", Toast.LENGTH_SHORT);
                        }
                    }
                }
        );

        // Set up click listener for selecting image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // Set up click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                } else
                    Toast.makeText(UploadActivity.this, "Будь ласка, оберіть зображення", Toast.LENGTH_SHORT).show();

            }
        });

        // Set up click listener for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
    }

    // Upload image and news data to Firebase storage and database
    public void uploadToFirebase(Uri uri) {
        String title = uploadTitle.getText().toString().trim();
        String caption = uploadCaption.getText().toString().trim();
        String text = uploadText.getText().toString().trim();
        String link = uploadLink.getText().toString().trim();
        String theme = uploadTheme.getText().toString().trim();

        // Define a reference to store the image
        final StorageReference fileRef = storageReference.child("Images")
                .child(System.currentTimeMillis() + "." + getFileExtension(uri));

        // Upload the image to Firebase storage
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL of the uploaded image
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Create a NewsData object with the news information
                        NewsData newsData = new NewsData(title, caption, text, link, theme, uri.toString());

                        // Generate a unique key for the news item
                        String key = databaseReference.push().getKey();
                        // Set the key for the news item
                        newsData.setKey(key);
                        // Save the news item to the Firebase database
                        databaseReference.child(key).setValue(newsData);

                        // Show a progress dialog and toast to indicate success
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                        builder.setCancelable(false);
                        builder.setView(R.layout.progress_layout);
                        Toast.makeText(UploadActivity.this, "Викладено", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Show a toast to indicate failure
                Toast.makeText(UploadActivity.this, "Помилка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get the file extension of the selected image
    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
