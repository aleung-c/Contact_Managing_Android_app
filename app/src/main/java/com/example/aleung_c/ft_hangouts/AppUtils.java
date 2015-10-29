package com.example.aleung_c.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.text.Html;

public class AppUtils extends Activity {

    public void set_actionbar_color(Context context) {
        SharedPreferences setval = PreferenceManager.getDefaultSharedPreferences(context);
        String colors = setval.getString("header_color", "DEFAULT");

        if (colors.isEmpty() || colors.equals("Black") || colors.equals("Noir"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            assert ab != null;
            ab.setBackgroundDrawable(new ColorDrawable(0xff000000));
            ab.setTitle(Html.fromHtml("<font color='#ffFFFF'>" + activity.getTitle().toString() + "</font>"));
        }
        else if (colors.equals("Red") || colors.equals("Rouge"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            assert ab != null;
            ab.setBackgroundDrawable(new ColorDrawable(0xffCC0033));
        }
        else if (colors.equals("Blue") || colors.equals("Bleu"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            assert ab != null;
            ab.setBackgroundDrawable(new ColorDrawable(0xff33CCFF));
        }
        else if (colors.equals("Green") || colors.equals("Vert"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            assert ab != null;
            ab.setBackgroundDrawable(new ColorDrawable(0xff00CC66));
        }
        else if (colors.equals("Yellow") || colors.equals("Jaune"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            assert ab != null;
            ab.setBackgroundDrawable(new ColorDrawable(0xffFFCC33));
        }
    }
}
