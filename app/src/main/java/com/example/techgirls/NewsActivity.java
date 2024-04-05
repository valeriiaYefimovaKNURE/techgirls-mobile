package com.example.techgirls;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import com.bumptech.glide.Glide;
import com.example.techgirls.HelpClasses.DatabaseManager;
import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.HelpClasses.ShowPages;

public class NewsActivity extends AppCompatActivity {
    ImageView settingsBtn;
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

        String name=SharedData.getUserName(this);
        String role=SharedData.getUserRole(this);
        String email=SharedData.getUserEmail(this);
        String login=SharedData.getUserLogin(this);
        String gender=SharedData.getUserGender(this);
        String birthday=SharedData.getUserBirthday(this);
        String password=SharedData.getUserPassword(this);

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
                            Toast.makeText(NewsActivity.this,"Edit pressed",Toast.LENGTH_LONG).show();
                        }
                        else if(id==R.id.item_2){
                            Toast.makeText(NewsActivity.this,"Delete pressed",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        SharedData.putUserInfo(this,name,email,login,birthday,gender,password,role);
    }
}
