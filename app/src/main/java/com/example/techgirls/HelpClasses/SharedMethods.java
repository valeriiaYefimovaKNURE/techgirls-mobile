package com.example.techgirls.HelpClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.example.techgirls.R;
import com.google.firebase.database.core.Context;

public class SharedMethods {
    public static void showPopupWindow(Activity activity, View anchorView, int layoutResourceId) {
        // Inflate the popup layout
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layoutResourceId, null);

        // Create the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        // Show the popup window
        popupWindow.showAsDropDown(anchorView);

        // Optionally, set an OnDismissListener to handle when the popup is dismissed
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // Code to execute when the popup is dismissed
            }
        });
    }
    public static AlertDialog createProgressDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.progress_layout, null);

        builder.setView(view);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }
}
