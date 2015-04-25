package com.example.android.cstogo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * - Yuro - 24.4.2015.
 */
public class MyApplication extends Application {

    private static Context context;
    private static int themeId;
    private static boolean themeSetting;
    private static SharedPreferences sharedPreferences;

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
        return themeSetting;
    }

}
