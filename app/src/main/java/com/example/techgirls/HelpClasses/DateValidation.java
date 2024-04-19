package com.example.techgirls.HelpClasses;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates dates in the format DD/MM/YYYY.
 */
public class DateValidation {

    /** Regular expression pattern for validating dates in the format DD/MM/YYYY. */
    private static final String DATE_PATTERN =
            "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])$";

    /** Compiled pattern for date validation. */
    private static final Pattern pattern = Pattern.compile(DATE_PATTERN);
    /**
     * Validates a date string.
     *
     * @param date The date string to validate.
     * @return True if the date string is valid, otherwise false.
     */
    public static boolean isValid(final String date) {
        boolean result = false;

        Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2100) {
                result = false;
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                result = false; // Months with 30 days cannot have a day greater than 30.
            } else if (month == 2) {
                if (day > 29 || (day > 28 && !isLeapYear(year))) {
                    result = false; // February cannot have more than 29 days (or 28 if not a leap year).
                } else {
                    result = true;
                }
            } else {
                result = true;
            }
        }
        return result;
    }
    /**
     * Checks if a year is a leap year.
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
}
