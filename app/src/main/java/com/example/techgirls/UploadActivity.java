package com.example.techgirls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.Models.NewsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {
    final private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("News");
    final private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTitle, uploadCaption, uploadText, uploadLink, uploadTheme;
    String imageURL;
    Uri imageUri;

    private final String[] itemThemes={"Наука","Соціальне","Новини"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.news_add);
        saveButton=findViewById(R.id.uploadButton_save);

        autoCompleteTextView =findViewById(R.id.uploadTheme);
        adapterItems=new ArrayAdapter<String>(this,R.layout.item_list,itemThemes);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String item=parent.getItemAtPosition(i).toString();
                Toast.makeText(UploadActivity.this,"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        uploadImage=findViewById(R.id.uploadImage);
        uploadTitle=findViewById(R.id.uploadTitle);
        uploadCaption=findViewById(R.id.uploadCaption);
        uploadText=findViewById(R.id.uploadText);
        uploadLink=findViewById(R.id.uploadLink);
        uploadTheme=autoCompleteTextView;

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data=result.getData();
                            imageUri =data.getData();
                            uploadImage.setImageURI(imageUri);
                        }
                        else{
                            Toast.makeText(UploadActivity.this, "Нічого не обрано",Toast.LENGTH_SHORT);
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent photoPicker=new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(imageUri!=null){
                    uploadToFirebase(imageUri);
                    saveData();
                }else
                    Toast.makeText(UploadActivity.this,"Please select image",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void uploadToFirebase(Uri uri){
        
    }
    public void saveData(){
        //StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("Images")
               // .child(uri.getLastPathSegment());

        AlertDialog.Builder builder=new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog= builder.create();
        dialog.show();

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageURL=urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        String title=uploadTitle.getText().toString().trim();
        String caption=uploadCaption.getText().toString().trim();
        String text=uploadText.getText().toString().trim();
        String link=uploadLink.getText().toString().trim();
        String theme=uploadTheme.getText().toString().trim();

        NewsData newsData=new NewsData(title,caption,text,link,theme,imageURL);

        FirebaseDatabase.getInstance().getReference("News").child(title).setValue(newsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UploadActivity.this,"Успішно збережено",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
