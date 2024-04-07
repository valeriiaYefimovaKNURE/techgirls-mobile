package com.example.techgirls.HelpClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.Models.Users;
import com.example.techgirls.R;

import org.checkerframework.checker.units.qual.C;

public class SharedData {
    public static String[] itemThemes = {"Наука", "Соціальне", "Новини","Спорт","Подкасти","Навчання","Мода"};
    public static void themeAutoCompleteTextView(Context context,AutoCompleteTextView textView){
        ArrayAdapter<String> adapterItems;
        adapterItems = new ArrayAdapter<String>(context, R.layout.item_list, itemThemes);
        textView.setAdapter(adapterItems);
    }
    public static void putNewsInfo(Intent intent, NewsData news){
        intent.putExtra("Image", news.getDataImage());
        intent.putExtra("Title", news.getDataTitle());
        intent.putExtra("Caption", news.getDataCaption());
        intent.putExtra("Text",news.getDataText());
        intent.putExtra("Link",news.getDataLink());
        intent.putExtra("Theme",news.getDataTheme());
        intent.putExtra("Key",news.getKey());
    }
    public static void putNewsInfo(Context context, String image, String title, String caption, String text, String link, String theme,
                                   String key){
        Intent intent = ((Activity) context).getIntent();

        intent.putExtra("Image", image);
        intent.putExtra("Title", title);
        intent.putExtra("Caption", caption);
        intent.putExtra("Text",text);
        intent.putExtra("Link",link);
        intent.putExtra("Theme",theme);
        intent.putExtra("Key",key);
    }
    public static void getNewsData(Context context, ImageView imageView, TextView titleView,
                                       TextView captionView, TextView textView, TextView linkView,
                                       TextView themeView) {
        Intent intent = ((Activity) context).getIntent();

        Glide.with(context)
                .load(intent.getStringExtra("Image"))
                .into(imageView);

        titleView.setText(intent.getStringExtra("Title"));
        captionView.setText(intent.getStringExtra("Caption"));
        textView.setText(intent.getStringExtra("Text"));
        linkView.setText(intent.getStringExtra("Link"));
        themeView.setText(intent.getStringExtra("Theme"));
    }
}
