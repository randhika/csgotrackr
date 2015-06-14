package com.palascak.android.cstogo;

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
    private static String tintSetting;
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
        tintSetting = sharedPreferences.getString("prefs_style_tinting", "indigo_red");
        if(!themeSetting) {
            switch (tintSetting){
                case "blue_pink":
                    tintSetting = "blue_pink";
                    themeId = R.style.blue_pink;
                    break;
                case "teal_orange":
                    tintSetting = "teal_orange";
                    themeId = R.style.teal_orange;
                    break;
                case "indigo_red":
                    tintSetting = "indigo_red";
                    themeId = R.style.indigo_red;
                    break;
                case "asiimov":
                    tintSetting = "asiimov";
                    themeId = R.style.asiimov;
                    break;
            }
        }
        else {
            themeId = R.style.DarkAppTheme;
        }
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

    public static String getTintSetting() {
        return tintSetting;
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
