package com.example.techgirls.RegistrationClasses;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.techgirls.HelpClasses.SharedData;
import com.example.techgirls.Pages.SettingsPage;
import com.example.techgirls.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Manages user data using SharedPreferences.
 */
public class UserManager {
    private static UserManager instance;
    private final SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "mePower_user_prefs";
    private static final String KEY_LOGIN = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_NAME = "name";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_ROLE = "role";

    private static final String LAST_SHOW_TIME_EMOTIONS = "lastShowTimeEmotions";



    /**
     * Private constructor to enforce singleton pattern.
     * Initializes SharedPreferences.
     *
     * @param context The context of the application.
     */
    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param context The context of the application.
     * @return The singleton instance of UserManager.
     */
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Saves user data to SharedPreferences.
     *
     * @param username The username of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     * @param name     The name of the user.
     * @param birthday The birthday of the user.
     * @param gender   The gender of the user.
     * @param role     The role of the user.
     */
    public void saveUser(String username, String email,String password, String name, String birthday, String gender, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_BIRTHDAY,birthday);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_ROLE,role);
        editor.apply();
    }

    // Getter methods for user data

    public String getLogin() {
        return sharedPreferences.getString(KEY_LOGIN, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }
    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, "");
    }
    public String getBirthday() {
        return sharedPreferences.getString(KEY_BIRTHDAY, "");
    }
    public String getGender() {
        return sharedPreferences.getString(KEY_GENDER, "");
    }
    public long getTimeEmotions() {
        return sharedPreferences.getLong(LAST_SHOW_TIME_EMOTIONS, 0);
    }
    public void setTimeEmotions(long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_SHOW_TIME_EMOTIONS, value);
        editor.apply();
    }
    public void setLogin(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, username);
        editor.apply();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public void setRole(String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public void setBirthday(String birthday) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_BIRTHDAY, birthday);
        editor.apply();
    }

    public void setGender(String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }
}
