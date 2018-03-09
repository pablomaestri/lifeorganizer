package com.psm.lifeorganizer.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.psm.lifeorganizer.R;

/**
 * Created by pmaestri on 12/29/2016.
 */

public class MessageUtil {

    public static void showToast(Activity context, int stringId) {

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_default,
                (ViewGroup) context.findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(context.getString(stringId));


        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void showToast(Activity context, String message) {

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_default,
                (ViewGroup) context.findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);


        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
