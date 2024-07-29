package com.example.techgirls.HelpClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.R;
import com.example.techgirls.RegistrationClasses.UserManager;

import java.util.Calendar;
import java.util.Random;

/**
 * Provides methods for managing shared data and views related to news items.
 */
public class SharedData {
    /** Array of available themes for news items. */
    public static String[] itemThemes = {"Наука", "Соціальне", "Новини","Спорт","Подкасти","Навчання","Історія"};

    /** Array of available genders. */
    public static String[] itemGender={"Жінка","Чоловік","Небінарна особа"};

    public static String[] itemAffirmaton;
    public static void initAffirmations(Context context) {
        itemAffirmaton = new String[]{
                context.getString(R.string.affirmation_1),
                context.getString(R.string.affirmation_2),
                context.getString(R.string.affirmation_3),
                context.getString(R.string.affirmation_4),
                context.getString(R.string.affirmation_5),
                context.getString(R.string.affirmation_6),
                context.getString(R.string.affirmation_7),
                context.getString(R.string.affirmation_8),
                context.getString(R.string.affirmation_9),
                context.getString(R.string.affirmation_10),
                context.getString(R.string.affirmation_11),
                context.getString(R.string.affirmation_12),
                context.getString(R.string.affirmation_13),
                context.getString(R.string.affirmation_14),
                context.getString(R.string.affirmation_15),
                context.getString(R.string.affirmation_16),
                context.getString(R.string.affirmation_17),
                context.getString(R.string.affirmation_18),
                context.getString(R.string.affirmation_19),
                context.getString(R.string.affirmation_20),
                context.getString(R.string.affirmation_21),
                context.getString(R.string.affirmation_22),
                context.getString(R.string.affirmation_23),
                context.getString(R.string.affirmation_24),
                context.getString(R.string.affirmation_25),
                context.getString(R.string.affirmation_26),
                context.getString(R.string.affirmation_27),
                context.getString(R.string.affirmation_28),
                context.getString(R.string.affirmation_29),
                context.getString(R.string.affirmation_30),
                context.getString(R.string.affirmation_31),
                context.getString(R.string.affirmation_32)
        };
    }
    private static final int[] imageAffirmation={
            R.drawable.affirmation_image_1,
            R.drawable.affirmation_image_2,
            R.drawable.affirmation_image_3,
            R.drawable.affirmation_image_4,
            R.drawable.affirmation_image_5,
            R.drawable.affirmation_image_6,
            R.drawable.affirmation_image_7,
            R.drawable.affirmation_image_8,
            R.drawable.affirmation_image_9,
            R.drawable.affirmation_image_10,
            R.drawable.affirmation_image_11,
            R.drawable.affirmation_image_12,
            R.drawable.affirmation_image_13,
            R.drawable.affirmation_image_14,
            R.drawable.affirmation_image_15,
            R.drawable.affirmation_image_16,
            R.drawable.affirmation_image_17
    };
    public static void checkAndShowDialog(Context context) {
        UserManager userManager = UserManager.getInstance(context);
        long lastShowTime = userManager.getTimeEmotions();

        Calendar currentCalendar = Calendar.getInstance();
        Calendar lastShowCalendar = Calendar.getInstance();
        lastShowCalendar.setTimeInMillis(lastShowTime);

        boolean isSameDay = currentCalendar.get(Calendar.YEAR) == lastShowCalendar.get(Calendar.YEAR) &&
                currentCalendar.get(Calendar.DAY_OF_YEAR) == lastShowCalendar.get(Calendar.DAY_OF_YEAR);

        if (!isSameDay) {
            Dialog dialog = new Dialog(context);
            try {
                dialog.setContentView(R.layout.card_view_emotions);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_card_bag));
                dialog.setCancelable(false);
                dialog.show();

                ImageButton angryButton = dialog.findViewById(R.id.angry_emotion_button);
                ImageButton sadButton = dialog.findViewById(R.id.sad_emotion_button);
                ImageButton wtfButton = dialog.findViewById(R.id.wtf_emotion_button);
                ImageButton anxietyButton = dialog.findViewById(R.id.anxiety_emotion_button);
                ImageButton happyButton = dialog.findViewById(R.id.happy_emotion_button);

                View.OnClickListener buttonClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAffirmationDialog(context);
                    }
                };

                angryButton.setOnClickListener(buttonClickListener);
                sadButton.setOnClickListener(buttonClickListener);
                wtfButton.setOnClickListener(buttonClickListener);
                anxietyButton.setOnClickListener(buttonClickListener);
                happyButton.setOnClickListener(buttonClickListener);

                // Сохранение текущего времени как время последнего показа
                userManager.setTimeEmotions(currentCalendar.getTimeInMillis());
            } catch (Exception e) {
                e.printStackTrace();

                dialog.dismiss();
            }
        }
    }
    private static void showAffirmationDialog(Context context){
        Dialog dialog = new Dialog(context);
        SharedData.initAffirmations(context);
        try {
            dialog.setContentView(R.layout.card_view_affirmation);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_card_bag));
            dialog.setCancelable(true);

            TextView affirmations = dialog.findViewById(R.id.affirmation_text);
            ImageView imageView = dialog.findViewById(R.id.imageView_affirmation);

            Random random = new Random();
            int randomIndex = random.nextInt(SharedData.itemAffirmaton.length);
            affirmations.setText(SharedData.itemAffirmaton[randomIndex]);

            int indexImage = random.nextInt(SharedData.imageAffirmation.length);
            imageView.setImageResource(SharedData.imageAffirmation[indexImage]);

            dialog.show();
        } catch (Exception e) {
            // Логирование ошибки
            e.printStackTrace();

            dialog.dismiss();
        }
    }
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
                                       TextView themeView) {
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
    }
}
