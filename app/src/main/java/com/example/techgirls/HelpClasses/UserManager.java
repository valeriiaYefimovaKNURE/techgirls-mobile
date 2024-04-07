package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.content.SharedPreferences;

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

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

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
}
