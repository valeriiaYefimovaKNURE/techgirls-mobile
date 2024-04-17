package com.example.techgirls;

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
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Models.NewsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewsUpdate extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    ImageButton backButton;
    EditText updateTitle, updateCaption, updateText, updateLink;
    AutoCompleteTextView autoCompleteTextView;
    String title, caption, text, link,theme;
    String imageUrl, oldImageUrl;
    String key;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_update);

        updateImage=findViewById(R.id.updateImage);
        updateButton=findViewById(R.id.updateButton);
        updateTitle=findViewById(R.id.updateTitle);
        updateCaption=findViewById(R.id.updateCaption);
        updateText=findViewById(R.id.updateText);
        updateLink=findViewById(R.id.updateLink);
        autoCompleteTextView=findViewById(R.id.uploadTheme);
        backButton=findViewById(R.id.updateButton_close);

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
        key=getIntent().getStringExtra("Key");
        databaseReference= FirebaseDatabase.getInstance().getReference("News").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NewsData newsData = snapshot.getValue(NewsData.class);

                    updateTitle.setText(newsData.getDataTitle());
                    updateCaption.setText(newsData.getDataCaption());
                    updateText.setText(newsData.getDataText());
                    updateLink.setText(newsData.getDataLink());
                    
                    SharedData.genderAutoCompleteTextView(NewsUpdate.this,autoCompleteTextView);
                    autoCompleteTextView.setText(newsData.getDataTheme(),false);
                    autoCompleteTextView.setSelection(SharedData.itemThemes.length);

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
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(NewsUpdate.this,"Updated",Toast.LENGTH_SHORT).show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
    }
    public void saveData(){
        storageReference= FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());

        AlertDialog.Builder builder=new AlertDialog.Builder(NewsUpdate.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog= builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageUrl=urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showNews(NewsUpdate.this);
            }
        });
    }
    public void updateData(){
        title = updateTitle.getText().toString().trim();
        caption = updateCaption.getText().toString().trim();
        text = updateText.getText().toString().trim();
        link = updateLink.getText().toString().trim();
        theme = autoCompleteTextView.getText().toString().trim();

        NewsData newsData=new NewsData(title,caption,text,link,theme,imageUrl);

        databaseReference.setValue(newsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference=FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                    reference.delete();
                    Toast.makeText(NewsUpdate.this,"Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewsUpdate.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
