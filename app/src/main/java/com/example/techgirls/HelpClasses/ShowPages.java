package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.content.Intent;

import com.example.techgirls.LoginPage;
import com.example.techgirls.MainActivity;
import com.example.techgirls.MainPage;
import com.example.techgirls.RegisterPage;
import com.example.techgirls.UploadActivity;

public class ShowPages {
    public static void showRegisterForm(Context context) {
        Intent intent = new Intent(context, RegisterPage.class);
        context.startActivity(intent);
    }
    public static void showLoginForm(Context context) {
        Intent intent = new Intent(context, LoginPage.class);
        context.startActivity(intent);
    }
    public static void showMainPage(Context context){
        Intent intent = new Intent(context, MainPage.class);
        context.startActivity(intent);
    }
    public static void showWelcomePage(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public static void showUploadNews (Context context){
        Intent intent = new Intent(context, UploadActivity.class);
        context.startActivity(intent);
    }
}
