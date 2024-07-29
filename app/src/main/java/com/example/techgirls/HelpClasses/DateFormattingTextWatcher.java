package com.example.techgirls.HelpClasses;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Calendar;

public class DateFormattingTextWatcher implements TextWatcher {
    private boolean isFormatting;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No operation
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No operation
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isFormatting) {
            return;
        }
        isFormatting = true;
        String originalString = s.toString();
        String formattedString = formatDate(originalString);
        s.replace(0, s.length(), formattedString);
        isFormatting = false;
    }

    private String formatDate(String date) {
        // Remove all non-digits
        date = date.replaceAll("[^\\d]", "");

        // Handle length cases
        if (date.length() > 2) {
            date = date.substring(0, 2) + "/" + date.substring(2);
        }
        if (date.length() > 5) {
            date = date.substring(0, 5) + "/" + date.substring(5);
        }

        // Split date into parts
        String[] parts = date.split("/");
        if (parts.length > 2) {
            String day = parts[0];
            String month = parts[1];
            String year = parts.length > 2 ? parts[2] : "";

            if(!day.isEmpty() && Integer.parseInt(day)>31){
                day="31";
            }

            // Validate month
            if (!month.isEmpty() && Integer.parseInt(month) > 12) {
                month = "12"; // Set month to the maximum valid value
            }

            // Validate year
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (!year.isEmpty() && Integer.parseInt(year) > currentYear) {
                year = String.valueOf(currentYear); // Set year to the current year
            }

            // Reformat date with corrected values
            date = String.format("%s/%s/%s", day, month, year);
        }

        return date;
    }
}
