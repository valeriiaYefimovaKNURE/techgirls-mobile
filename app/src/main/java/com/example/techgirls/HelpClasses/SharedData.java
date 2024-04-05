package com.example.techgirls.HelpClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.Models.Users;

import org.checkerframework.checker.units.qual.C;

public class SharedData {
    public static String[] itemThemes = {"Наука", "Соціальне", "Новини","Спорт","Подкасти","Навчання","Мода"};

    public static void putUserInfo(Intent intent, String name, String email, String login, String birthday, String gender, String password, String role) {
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        intent.putExtra("Login", login);
        intent.putExtra("Birthday", birthday);
        intent.putExtra("Gender", gender);
        intent.putExtra("Password", password);
        intent.putExtra("Role", role);
    }
    public static void putUserInfo(Context context, String name, String email, String login, String birthday, String gender, String password, String role) {
        Intent intent = ((Activity) context).getIntent();
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        intent.putExtra("Login", login);
        intent.putExtra("Birthday", birthday);
        intent.putExtra("Gender", gender);
        intent.putExtra("Password", password);
        intent.putExtra("Role", role);
    }
    public static void putUserInfo(Context context, Users user) {
        Intent intent = ((Activity) context).getIntent();
        intent.putExtra("Name", user.getName());
        intent.putExtra("Email", user.getEmail());
        intent.putExtra("Login", user.getLogin());
        intent.putExtra("Birthday", user.getBirthday());
        intent.putExtra("Gender", user.getGender());
        intent.putExtra("Password", user.getPassword());
        intent.putExtra("Role", user.getRole());
    }
    public static String getUserName(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Name");
    }
    public static String getUserRole(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Role");
    }
    public static String getUserEmail(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Email");
    }
    public static String getUserLogin(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Login");
    }
    public static String getUserGender(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Gender");
    }
    public static String getUserPassword(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Password");
    }
    public static String getUserBirthday(Context context){
        Intent intent = ((Activity) context).getIntent();
        return intent.getStringExtra("Birthday");
    }
    public static void putNewsInfo(Intent intent, NewsData news){
        intent.putExtra("Image", news.getDataImage());
        intent.putExtra("Title", news.getDataTitle());
        intent.putExtra("Caption", news.getDataCaption());
        intent.putExtra("Text",news.getDataText());
        intent.putExtra("Link",news.getDataLink());
        intent.putExtra("Theme",news.getDataTheme());
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
