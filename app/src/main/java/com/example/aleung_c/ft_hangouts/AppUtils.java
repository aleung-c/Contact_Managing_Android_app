package com.example.aleung_c.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by aleung-c on 23/10/15.
 */
public class AppUtils extends Activity {

    public void set_actionbar_color(Context context) {
        SharedPreferences setval = PreferenceManager.getDefaultSharedPreferences(context);
        String colors = setval.getString("header_color", "DEFAULT");

        // TODO : gerer le cas "color == default == > unset" => FAIT, a test.
        if (colors.isEmpty() || colors.equals("black"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();

            ab.setBackgroundDrawable(new ColorDrawable(0xff000000));
            ab.setTitle(Html.fromHtml("<font color='#ffFFFF'>" + activity.getTitle().toString() + "</font>"));
        }
        else if (colors.equals("red"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            ab.setBackgroundDrawable(new ColorDrawable(0xffCC0033));
        }
        else if (colors.equals("blue"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            ab.setBackgroundDrawable(new ColorDrawable(0xff33CCFF));
        }
        else if (colors.equals("green"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            ab.setBackgroundDrawable(new ColorDrawable(0xff00CC66));
        }
        else if (colors.equals("yellow"))
        {
            Activity activity = (Activity) context;
            ActionBar ab = activity.getActionBar();
            ab.setBackgroundDrawable(new ColorDrawable(0xffFFCC33));
        }
    }
}
