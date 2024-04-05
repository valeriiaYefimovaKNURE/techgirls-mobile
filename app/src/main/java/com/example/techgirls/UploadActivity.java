package com.example.techgirls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class UploadActivity extends AppCompatActivity {
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("News");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    ImageView uploadImage;
    Button saveButton;
    ImageButton backButton;
    EditText uploadTitle, uploadCaption, uploadText, uploadLink, uploadTheme;
    Uri imageUri;

    private final String[] itemThemes = SharedData.itemThemes;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.news_add);

        saveButton = findViewById(R.id.uploadButton_save);
        backButton=findViewById(R.id.uploadButton_close);

        autoCompleteTextView = findViewById(R.id.uploadTheme);
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list, itemThemes);
        autoCompleteTextView.setAdapter(adapterItems);

        uploadImage = findViewById(R.id.uploadImage);
        uploadTitle = findViewById(R.id.uploadTitle);
        uploadCaption = findViewById(R.id.uploadCaption);
        uploadText = findViewById(R.id.uploadText);
        uploadLink = findViewById(R.id.uploadLink);
        uploadTheme = autoCompleteTextView;

        String name=SharedData.getUserName(this);
        String role=SharedData.getUserRole(this);
        String email=SharedData.getUserEmail(this);
        String login=SharedData.getUserLogin(this);
        String gender=SharedData.getUserGender(this);
        String birthday=SharedData.getUserBirthday(this);
        String password=SharedData.getUserPassword(this);

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
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                } else
                    Toast.makeText(UploadActivity.this, "Please select image", Toast.LENGTH_SHORT).show();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
        SharedData.putUserInfo(this,name,email,login,birthday,gender,password,role);
    }

    public void uploadToFirebase(Uri uri) {
        String title = uploadTitle.getText().toString().trim();
        String caption = uploadCaption.getText().toString().trim();
        String text = uploadText.getText().toString().trim();
        String link = uploadLink.getText().toString().trim();
        String theme = uploadTheme.getText().toString().trim();

        final StorageReference fileRef = storageReference.child("Images").child(System.currentTimeMillis() + "." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        NewsData newsData = new NewsData(title, caption, text, link, theme, uri.toString());
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).setValue(newsData);
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                        builder.setCancelable(false);
                        builder.setView(R.layout.progress_layout);
                        Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UploadActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
