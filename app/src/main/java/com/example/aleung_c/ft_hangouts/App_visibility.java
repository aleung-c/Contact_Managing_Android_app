package com.example.aleung_c.ft_hangouts;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App_visibility extends Application {
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    // pour save time et display toast.
    public static void set_time(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        time = dateFormat.format(now);
    }

    public static void display_time(Context context) {
        Toast.makeText(context, time, Toast.LENGTH_SHORT).show();
    }

    private static boolean activityVisible;
    private static String time;
}
