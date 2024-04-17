package com.example.techgirls.HelpClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.example.techgirls.AboutAppPage;
import com.example.techgirls.DepActivity;
import com.example.techgirls.HelpCenterPage;
import com.example.techgirls.LoginPage;
import com.example.techgirls.MainPage;
import com.example.techgirls.R;
import com.example.techgirls.SettingsUser;
import com.example.techgirls.WelcomeActivity;
import com.example.techgirls.NewsActivity;
import com.example.techgirls.NewsUpdate;
import com.example.techgirls.RegisterPage;
import com.example.techgirls.SettingsPage;
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
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }
    public static void showUploadNews (Context context){
        Intent intent = new Intent(context, UploadActivity.class);
        context.startActivity(intent);
    }
    public static void showSettings (Context context){
        Intent intent = new Intent(context, SettingsPage.class);
        context.startActivity(intent);
    }
    public static void showUpdateNews (Context context){
        Intent intent = new Intent(context, NewsUpdate.class);
        context.startActivity(intent);
    }
    public static void showNews (Context context){
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }
    public static void showAboutAppPage(Context context){
        Intent intent = new Intent(context, AboutAppPage.class);
        context.startActivity(intent);
    }
    public static void showUserSettings(Context context){
        Intent intent = new Intent(context, SettingsUser.class);
        context.startActivity(intent);
    }
    public static void showDep(Context context){
        Intent intent = new Intent(context, DepActivity.class);
        context.startActivity(intent);
    }
    public static void showHelpCenter(Context context){
        Intent intent = new Intent(context, HelpCenterPage.class);
        context.startActivity(intent);
    }
    public static void showLoadingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
