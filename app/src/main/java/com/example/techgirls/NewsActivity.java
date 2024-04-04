package com.example.techgirls;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techgirls.HelpClasses.ShowPages;

public class NewsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        ImageView imageView=findViewById(R.id.image);
        ImageView backBtn=findViewById(R.id.back_button);
        TextView titleView=findViewById(R.id.title);
        TextView captionView=findViewById(R.id.caption);
        TextView textView=findViewById(R.id.text);
        TextView linkView=findViewById(R.id.link);
        TextView themeView=findViewById(R.id.themes);

        String imagePath = getIntent().getStringExtra("Image");
        Uri imageUri = Uri.parse(imagePath);
        imageView.setImageURI(imageUri);
        //imageView.setImageResource(getIntent().getIntExtra("Image",0));
        titleView.setText(getIntent().getStringExtra("Title"));
        captionView.setText(getIntent().getStringExtra("Caption"));
        textView.setText(getIntent().getStringExtra("Text"));
        linkView.setText(getIntent().getStringExtra("Link"));
        themeView.setText(getIntent().getStringExtra("Theme"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPages.showMainPage(v.getContext());
            }
        });
    }
}
