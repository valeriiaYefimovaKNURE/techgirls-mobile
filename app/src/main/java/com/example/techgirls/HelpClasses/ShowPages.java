package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.content.Intent;

import com.example.techgirls.Pages.AboutAppPage;
import com.example.techgirls.Pages.SectionsActivity;
import com.example.techgirls.Pages.HelpCenterPage;
import com.example.techgirls.Pages.LoginPage;
import com.example.techgirls.Pages.MainPage;
import com.example.techgirls.Pages.SettingsUser;
import com.example.techgirls.Pages.WelcomeActivity;
import com.example.techgirls.Pages.NewsActivity;
import com.example.techgirls.Pages.NewsUpdate;
import com.example.techgirls.Pages.RegisterPage;
import com.example.techgirls.Pages.SettingsPage;
import com.example.techgirls.Pages.UploadActivity;

/**
 * Utility class for showing different pages or activities.
 */
public class ShowPages {
    /**
     * Show the register page activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showRegisterForm(Context context) {
        Intent intent = new Intent(context, RegisterPage.class);
        context.startActivity(intent);
    }
    /**
     * Show the login page activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showLoginForm(Context context) {
        Intent intent = new Intent(context, LoginPage.class);
        context.startActivity(intent);
    }
    /**
     * Show the main page activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showMainPage(Context context){
        Intent intent = new Intent(context, MainPage.class);
        context.startActivity(intent);
    }
    /**
     * Show the welcome page activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showWelcomePage(Context context){
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }
    /**
     * Show the upload news activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showUploadNews (Context context){
        Intent intent = new Intent(context, UploadActivity.class);
        context.startActivity(intent);
    }
    /**
     * Show the settings activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showSettings (Context context){
        Intent intent = new Intent(context, SettingsPage.class);
        context.startActivity(intent);
    }
    /**
     * Show the news update activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showUpdateNews (Context context){
        Intent intent = new Intent(context, NewsUpdate.class);
        context.startActivity(intent);
    }
    /**
     * Show the news view activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showNews (Context context){
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }
    /**
     * Show the about app page activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showAboutAppPage(Context context){
        Intent intent = new Intent(context, AboutAppPage.class);
        context.startActivity(intent);
    }
    /**
     * Show the user settings activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showUserSettings(Context context){
        Intent intent = new Intent(context, SettingsUser.class);
        context.startActivity(intent);
    }
    /**
     * Show the section activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showSections(Context context){
        Intent intent = new Intent(context, SectionsActivity.class);
        context.startActivity(intent);
    }
    /**
     * Show the help center activity.
     *
     * @param context The context from which the activity is launched.
     */
    public static void showHelpCenter(Context context){
        Intent intent = new Intent(context, HelpCenterPage.class);
        context.startActivity(intent);
    }
}
