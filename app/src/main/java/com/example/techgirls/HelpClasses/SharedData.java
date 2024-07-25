package com.example.techgirls.HelpClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.R;

/**
 * Provides methods for managing shared data and views related to news items.
 */
public class SharedData {
    /** Array of available themes for news items. */
    public static String[] itemThemes = {"Наука", "Соціальне", "Новини","Спорт","Подкасти","Навчання","Історія"};

    /** Array of available genders. */
    public static String[] itemGender={"Жінка","Чоловік","Невідомо"};

    /**
     * Sets up an AutoCompleteTextView with themes for news items.
     *
     * @param context The context of the application.
     * @param textView The AutoCompleteTextView to set up.
     */
    public static void themeAutoCompleteTextView(Context context,AutoCompleteTextView textView){
        ArrayAdapter<String> adapterItems;
        adapterItems = new ArrayAdapter<String>(context, R.layout.item_list, itemThemes);
        textView.setAdapter(adapterItems);
    }

    /**
     * Sets up an AutoCompleteTextView with genders.
     *
     * @param context The context of the application.
     * @param textView The AutoCompleteTextView to set up.
     */
    public static void genderAutoCompleteTextView(Context context,AutoCompleteTextView textView){
        ArrayAdapter<String> adapterItems;
        adapterItems = new ArrayAdapter<String>(context, R.layout.item_list, itemGender);
        textView.setAdapter(adapterItems);
    }

    /**
     * Puts news information into an intent.
     *
     * @param intent The intent to put the news information into.
     * @param news The NewsData object containing the news information.
     */
    public static void putNewsInfo(Intent intent, NewsData news){
        intent.putExtra("Image", news.getDataImage());
        intent.putExtra("Title", news.getDataTitle());
        intent.putExtra("Caption", news.getDataCaption());
        intent.putExtra("Text",news.getDataText());
        intent.putExtra("Link",news.getDataLink());
        intent.putExtra("Theme",news.getDataTheme());
        intent.putExtra("Key",news.getKey());
        intent.putExtra("Author",news.getDataAuthor());
    }
    public static void putUserInfo(Intent intent,String email, String name, String login, String password, String birth, String gender){
        intent.putExtra("Email", email);
        intent.putExtra("Name", name);
        intent.putExtra("Login", login);
        intent.putExtra("Password",password);
        intent.putExtra("Birth",birth);
        intent.putExtra("Gender",gender);
    }

    /**
     * Retrieves news data from an intent and sets it into views.
     *
     * @param context The context of the application.
     * @param imageView The ImageView to display the news image.
     * @param titleView The TextView to display the news title.
     * @param captionView The TextView to display the news caption.
     * @param textView The TextView to display the news text.
     * @param linkView The TextView to display the news link.
     * @param themeView The TextView to display the news theme.
     */
    public static void getNewsData(Context context, ImageView imageView, TextView titleView,
                                       TextView captionView, TextView textView, TextView linkView,
                                       TextView themeView, TextView authorView) {
        Intent intent = ((Activity) context).getIntent();

        Glide.with(context)
                .load(intent.getStringExtra("Image"))
                .into(imageView);

        titleView.setText(intent.getStringExtra("Title"));
        captionView.setText(intent.getStringExtra("Caption"));
        textView.setText(intent.getStringExtra("Text").replace("\\n", "\n"));
        linkView.setText(intent.getStringExtra("Link"));
        themeView.setText(intent.getStringExtra("Theme"));
        intent.getStringExtra("Key");
        authorView.setText(intent.getStringExtra("Author"));

    }
}
