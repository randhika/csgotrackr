package com.example.android.cstogo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.adapters.MyPagerAdapter;


public class MainActivity extends ActionBarActivity {

    static SharedPreferences sp = null;
    SharedPreferences.OnSharedPreferenceChangeListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getThemeId());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("CS to GO");

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), getApplicationContext()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        listener = new themeListener();
        sp.registerOnSharedPreferenceChangeListener(listener);

    }

    private class themeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences spref, String key) {
            if(key.equals("prefs_style_nightmode") && !spref.getBoolean(key, false) == (MyApplication.getThemeSetting()))
            {
                MyApplication.reloadTheme();
                MyApplication.setScheduledRestart(true);
            }
            String sprefMatchpage = spref.getString("prefs_style_matchpage", "small_card");
            String savedMatchpage = MyApplication.getMatchpageStyle();
            assert sprefMatchpage != null;
            boolean compareSprefSaved = sprefMatchpage.equals(savedMatchpage);
            if(key.equals("prefs_style_matchpage") && !compareSprefSaved){
                MyApplication.loadMatchlistSetting();
                MyApplication.setScheduledRestart(true);
            }
            String sprefSteamId = spref.getString("prefs_steam_name", "");
            String savedSteamId = MyApplication.getSteamId();
            assert sprefSteamId != null;
            boolean compareIds = sprefSteamId.equals(savedSteamId);
            if(key.equals("prefs_steam_name") && !compareIds){
                MyApplication.loadId();
                MyApplication.setScheduledRestart(true);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.isScheduledRestart())
        {
            Log.i("TAG", "doing restart");
            MyApplication.setScheduledRestart(false);
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }


}
