package com.example.android.cstogo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.adapters.MyPagerAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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
        //noinspection StatementWithEmptyBody
        if (MyApplication.getThemeId() == R.style.asiimov){
            LinearLayout toolbarLinear = (LinearLayout) findViewById(R.id.toolbar_base_linear);
            toolbarLinear.setVisibility(View.VISIBLE);

            ImageView cornerDrawable = (ImageView) findViewById(R.id.toolbar_base_top_left_drawable);
            ImageView topDrawable = (ImageView) findViewById(R.id.toolbar_base_top_drawable);
            ImageView bottomDrawable = (ImageView) findViewById(R.id.toolbar_base_bottom_drawable);
            ImageView rightDrawable = (ImageView) findViewById(R.id.toolbar_base_right_drawable);

            Picasso.with(this).load(R.drawable.asiimov4).fit().centerCrop().into(cornerDrawable);
            Picasso.with(this).load(R.drawable.asiimov3).fit().centerCrop().into(topDrawable);
            Picasso.with(this).load(R.drawable.asiimov2).fit().centerCrop().into(bottomDrawable);
            Picasso.with(this).load(R.drawable.asiimov1).fit().centerCrop().into(rightDrawable);
        }

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

            String sprefTint = spref.getString("prefs_style_tinting", "blue_pink");
            String savedTint = MyApplication.getTintSetting();
            assert sprefTint != null;
            boolean compareTint = sprefTint.equals(savedTint);
            if(key.equals("prefs_style_tinting") && !compareTint)
            {
                MyApplication.reloadTheme();
                MyApplication.setScheduledRestart(true);
            }

            String sprefMatchpage = spref.getString("prefs_style_matchpage", "small_card");
            String savedMatchpage = MyApplication.getMatchpageStyle();
            assert sprefMatchpage != null;
            boolean compareSprefSaved = sprefMatchpage.equals(savedMatchpage);
            if(key.equals("prefs_style_matchpage") && !compareSprefSaved)
            {
                MyApplication.loadMatchlistSetting();
                MyApplication.setScheduledRestart(true);
            }

            String sprefSteamId = spref.getString("prefs_steam_name", "");
            String savedSteamId = MyApplication.getSteamId();
            assert sprefSteamId != null;
            boolean compareIds = sprefSteamId.equals(savedSteamId);
            if(key.equals("prefs_steam_name") && !compareIds)
            {
                if (sprefSteamId.equals(""))
                {
                    MyApplication.setScheduledRestart(true);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("steam_id_64_success", 1);
                    editor.putString("steam_id_64", "");
                    editor.apply();
                } else {
                    MyApplication.loadId();
                    MyApplication.setScheduledRestart(true);

                    String apiKey = getResources().getString(R.string.api_key);
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("api.steampowered.com")
                            .appendPath("ISteamUser")
                            .appendPath("ResolveVanityURL")
                            .appendPath("v0001")
                            .appendQueryParameter("key", apiKey)
                            .appendQueryParameter("vanityurl", sprefSteamId);
                    String myUrl = builder.build().toString();

                    new BackgroundWebRunner().execute(myUrl);
                }
            }
        }
    }

    private class BackgroundWebRunner extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... url) {
            Response response;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return new JSONObject(jsonData).getJSONObject("response");
            } catch (IOException | JSONException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            SharedPreferences.Editor editor = preferences.edit();
            if(result != null){
                try {
                    int jsonSuccess = result.getInt("success");
                    if (jsonSuccess == 42) {
                        editor.putInt("steam_id_64_success", jsonSuccess);
                        editor.putString("steam_id_64", "0");
                        Toast.makeText(MyApplication.getAppContext(), "Couldn't find specified ID. Typo?", Toast.LENGTH_LONG).show();
                    } else {
                        String jsonSteamId = result.getString("steamid");

                        editor.putInt("steam_id_64_success", jsonSuccess);
                        editor.putString("steam_id_64", jsonSteamId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                editor.putInt("steam_id_64_success", 99);
                editor.putString("steam_id_64", "0");
                Toast.makeText(MyApplication.getAppContext(), "Error connecting to steam API. Please try again later.", Toast.LENGTH_LONG).show();
            }
            editor.apply();
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
            MyApplication.setScheduledRestart(false);
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }


}
