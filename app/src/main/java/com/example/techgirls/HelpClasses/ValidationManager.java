package com.example.techgirls.HelpClasses;

import static android.provider.Settings.System.getString;

import android.text.TextUtils;

import com.example.techgirls.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationManager {
    public static boolean validateDate(String date, TextInputLayout layout) {
        if (TextUtils.isEmpty(date) || !DateValidation.isValid(date)) {
            layout.getContext().getString(R.string.error_date);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }
    public static Boolean validatePassword(String val, TextInputLayout layout){
        if (val.isEmpty() || val.length()<5) {
            layout.getContext().getString(R.string.error_password);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }
    public static Boolean validateEmail(String val, TextInputLayout layout){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty() || !val.matches(emailPattern)) {
            layout.getContext().getString(R.string.error_email);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }
    public static Boolean validateName(String val, TextInputLayout layout){
        if (val.isEmpty()) {
            layout.getContext().getString(R.string.error_name);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }
    public static Boolean validateLogin(String val, TextInputLayout layout){
        if (val.isEmpty()) {
            layout.getContext().getString(R.string.error_signUpEmptyFields);
            return false;
        }
        else {
            layout.setError(null);
            return true;
        }
    }
    public static Boolean validateGender(String val, TextInputLayout layout){
        if (val.isEmpty()) {
            layout.getContext().getString(R.string.error_gender);
            return false;
        }
        else {
            layout.setError(null);
            return true;
        }
    }
}
