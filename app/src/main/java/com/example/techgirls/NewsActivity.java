package com.example.techgirls;

import android.content.Context;
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

import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.HelpClasses.UserManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NewsActivity extends AppCompatActivity {
    ImageView settingsBtn;
    String key="";
    String imageUrl="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);
        ImageView backBtn=findViewById(R.id.back_button);
        settingsBtn=findViewById(R.id.settingsNews_button);

        ImageView imageView=findViewById(R.id.image);
        TextView titleView=findViewById(R.id.title);
        TextView captionView=findViewById(R.id.caption);
        TextView textView=findViewById(R.id.text);
        TextView linkView=findViewById(R.id.link);
        TextView themeView=findViewById(R.id.themes);

        String role= UserManager.getInstance(this).getRole();

        SharedData.getNewsData(this,imageView,titleView,captionView,textView,linkView,themeView);

        if (DatabaseManager.isEditor(role) || DatabaseManager.isAdmin(role)) {
            settingsBtn.setVisibility(View.VISIBLE);
        } else settingsBtn.setVisibility(View.INVISIBLE);

        registerForContextMenu(settingsBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
        key=getIntent().getStringExtra("Key");
        imageUrl=getIntent().getStringExtra("Image");
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(NewsActivity.this,settingsBtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_news_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id==R.id.item_1){
                            Intent intent = new Intent(NewsActivity.this, NewsUpdate.class);
                            intent.putExtra("Key", key);
                            startActivity(intent);
                        }
                        else if(id==R.id.item_2){
                            DeleteNews();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void DeleteNews(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ви точно хочете видалити цей запис?")
                .setCancelable(false)
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("News");
                        FirebaseStorage storage=FirebaseStorage.getInstance();
                        StorageReference storageReference=storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(NewsActivity.this,"Видалено",Toast.LENGTH_SHORT).show();
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
