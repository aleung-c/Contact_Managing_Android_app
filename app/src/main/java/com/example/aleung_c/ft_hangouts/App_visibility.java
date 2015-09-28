package com.example.aleung_c.ft_hangouts;

import android.app.Application;

/**
 * Created by aleung-c on 28/09/15.
 */
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

    private static boolean activityVisible;
}
