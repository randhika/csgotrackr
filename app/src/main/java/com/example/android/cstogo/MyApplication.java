package com.example.android.cstogo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * - Yuro - 24.4.2015.
 */
public class MyApplication extends Application {

    private static Context context;
    private static int themeId;
    private static boolean themeSetting;
    private static SharedPreferences sharedPreferences;
    private static boolean scheduledRestart = false;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        reloadTheme();
    }

    public static void reloadTheme() {
        themeSetting = sharedPreferences.getBoolean("prefs_style_nightmode", false);
        if(!themeSetting)
            themeId = R.style.AppTheme;
        else
            themeId = R.style.DarkAppTheme;
    }

    public static Context getAppContext()
    {
        return context;
    }

    public static int getThemeId()
    {
        return themeId;
    }

    public static boolean getThemeSetting()
    {
        Log.i("TAG", "ThemeSetting : " + themeSetting);return themeSetting;
    }

    public static boolean isScheduledRestart() {
        return scheduledRestart;
    }

    public static void setScheduledRestart(boolean scheduledRestart) {
        MyApplication.scheduledRestart = scheduledRestart;
    }
}
