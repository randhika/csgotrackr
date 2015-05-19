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
    private static String matchpageStyle;
    private static String steamId;
    private static SharedPreferences sharedPreferences;
    private static boolean scheduledRestart = false;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        reloadTheme();
        loadMatchlistSetting();
        loadId();
    }

    public static void loadId(){
        steamId = sharedPreferences.getString("prefs_steam_name", "");
    }

    public static void loadMatchlistSetting() {
        matchpageStyle = sharedPreferences.getString("prefs_style_matchpage", "small_card");
    }

    public static void reloadTheme() {
        themeSetting = sharedPreferences.getBoolean("prefs_style_nightmode", false);
        if(!themeSetting)
            themeId = R.style.blue_pink;
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

    public static String getMatchpageStyle() {
        return matchpageStyle;
    }

    public static String getSteamId() {
        return steamId;
    }

    public static boolean isScheduledRestart() {
        return scheduledRestart;
    }

    public static void setScheduledRestart(boolean scheduledRestart) {
        MyApplication.scheduledRestart = scheduledRestart;
    }
}
