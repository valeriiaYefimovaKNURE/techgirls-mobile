package com.example.techgirls.HelpClasses;

import android.text.TextUtils;

import com.example.techgirls.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Utility class for performing various validations.
 */
public class ValidationManager {
    /**
     * Validates a date string.
     *
     * @param date   The date string to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the date is valid, false otherwise.
     */
    public static boolean validateDate(String date, TextInputLayout layout) {
        if (TextUtils.isEmpty(date) || !DateValidation.isValid(date)) {
            layout.setError(layout.getContext().getString(R.string.error_date));
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    /**
     * Validates a password.
     *
     * @param val    The password to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the password is valid, false otherwise.
     */
    public static Boolean validatePassword(String val, TextInputLayout layout){
        if (val.isEmpty() || val.length()<5) {
            layout.setError(layout.getContext().getString(R.string.error_password));
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    /**
     * Validates an email address.
     *
     * @param val    The email address to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the email address is valid, false otherwise.
     */
    public static Boolean validateEmail(String val, TextInputLayout layout){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty() || !val.matches(emailPattern)) {
            layout.setError(layout.getContext().getString(R.string.error_email));
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    /**
     * Validates a name.
     *
     * @param val    The name to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the name is valid, false otherwise.
     */
    public static Boolean validateName(String val, TextInputLayout layout){
        if (val.isEmpty()) {
            layout.setError(layout.getContext().getString(R.string.error_name));
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    /**
     * Validates a login username.
     *
     * @param val    The login username to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the login username is valid, false otherwise.
     */
    public static Boolean validateLogin(String val, TextInputLayout layout){
        String forbiddenCharacters = ".#$[]";
        boolean containsForbiddenCharacters = false;

        for (int i = 0; i < val.length(); i++) {
            if (forbiddenCharacters.contains(String.valueOf(val.charAt(i)))) {
                containsForbiddenCharacters = true;
                break;
            }
        }

        if (val.isEmpty()) {
            layout.setError(layout.getContext().getString(R.string.error_signUpEmptyFields));
            return false;
        }
        if(containsForbiddenCharacters){
            layout.setError(layout.getContext().getString(R.string.error_login_containsCharacters));
            return false;
        }else {
            layout.setError(null);
            return true;
        }
    }

    /**
     * Validates a gender.
     *
     * @param val    The gender to validate.
     * @param layout The TextInputLayout to display error messages.
     * @return True if the gender is valid, false otherwise.
     */
    public static Boolean validateGender(String val, TextInputLayout layout){
        if (val.isEmpty()) {
            layout.setError(layout.getContext().getString(R.string.error_gender));
            return false;
        }
        else {
            layout.setError(null);
            return true;
        }
    }
}
