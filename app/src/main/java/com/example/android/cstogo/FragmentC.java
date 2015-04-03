package com.example.android.cstogo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentC extends Fragment {

    private int mTotalKills;
    private int mTotalDeaths;
    private int mTotalWins;
    private int mTotalKillsHeadshot;
    private int mTotalShotsHit;
    private int mTotalShotsFired;
    private float mTotalKd;

    public FragmentC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        //call Async with view param so I dont get nullpointer on updateview
        new AsyncTaskParseJson().execute(view);

        return view;
    }

    public class AsyncTaskParseJson extends AsyncTask<View, String, View> {

        //final String TAG = "AsyncTaskParseJson.java";

        // set your json string url here
        String yourJsonStringUrl = "http://cityinfocen.zz.vc/stats.json";

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {}

        @Override
        protected View doInBackground(View... arg0) {

            try {

                // instantiate our json parser
                JSONParser jParser = new JSONParser();

                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

                // get the array of users
                dataJsonArr = json.getJSONObject("playerstats").getJSONArray("stats");

                //int most_map_wins = 0;
                //String most_map_wins_name = "";
                // loop through all stats
                for (int i = 0; i < dataJsonArr.length(); i++) {

                    JSONObject c = dataJsonArr.getJSONObject(i);
                    // Storing each json item in variable
                    String name = c.getString("name");
                    String value = c.getString("value");

                    switch (name){
                        case "total_kills":
                            setTotalKills(Integer.valueOf(value));
                            break;
                        case "total_deaths":
                            setTotalDeaths(Integer.valueOf(value));
                            break;
                        case "total_wins":
                            setTotalWins(Integer.valueOf(value));
                            break;
                        case "total_kills_headshot":
                            setTotalKillsHeadshot(Integer.valueOf(value));
                            break;
                        case "total_shots_hit":
                            setTotalShotsHit(Integer.valueOf(value));
                            break;
                        case "total_shots_fired":
                            setTotalShotsFired(Integer.valueOf(value));
                            break;
                        default:
                            break;
                    }

                    /*
                    if (name.startsWith("total_wins_map_")) {
                        Log.d(TAG, i + " : " + dataJsonArr.length() + c.toString() + " : doInBackground"
                                + "\n" + name + " : " + value);
                    }

                    if (name.startsWith("total_wins_map_")) {
                        if (Integer.valueOf(value) > most_map_wins){
                            most_map_wins = Integer.valueOf(value);
                            most_map_wins_name = name.replace("total_wins_map_", "");
                        }
                    }
                    */
                }
                //Log.d(TAG, "_______________________________________________");
                //Log.d(TAG, "RESULT : " + most_map_wins + " on " + most_map_wins_name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arg0[0];
        }

        @Override
        protected void onPostExecute(View view) {
            createTextViews(view);
        }
    }

    private void createTextViews(View view) {
        TextView web_total_kills_number = (TextView) view.findViewById(R.id.web_total_kills_number);
        TextView web_total_deaths_number = (TextView) view.findViewById(R.id.web_total_deaths_number);
        TextView web_total_wins_number = (TextView) view.findViewById(R.id.web_total_wins_number);
        TextView web_total_kills_headshot_number = (TextView) view.findViewById(R.id.web_total_kills_headshot_number);
        TextView web_total_shots_fired_number = (TextView) view.findViewById(R.id.web_total_shots_fired_number);
        TextView web_total_shots_hit_number = (TextView) view.findViewById(R.id.web_total_shots_hit_number);
        TextView web_total_kd = (TextView) view.findViewById(R.id.web_kd);

        BigDecimal kills = new BigDecimal(getTotalKills());
        BigDecimal deaths = new BigDecimal(getTotalDeaths());
        BigDecimal kd = kills.divide(deaths, 2, BigDecimal.ROUND_HALF_UP);
        setTotalKd(kd.floatValue());

        web_total_kills_number.setText(String.valueOf(getTotalKills()));
        web_total_deaths_number.setText(String.valueOf(getTotalDeaths()));
        web_total_wins_number.setText(String.valueOf(getTotalWins()));
        web_total_kills_headshot_number.setText(String.valueOf(getTotalKillsHeadshot()));
        web_total_shots_fired_number.setText(String.valueOf(getTotalShotsFired()));
        web_total_shots_hit_number.setText(String.valueOf(getTotalShotsHit()));
        web_total_kd.setText(String.valueOf(getTotalKd()));
    }

    public int getTotalKills() {
        return mTotalKills;
    }

    public void setTotalKills(int totalKills) {
        mTotalKills = totalKills;
    }

    public int getTotalDeaths() {
        return mTotalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        mTotalDeaths = totalDeaths;
    }

    public int getTotalWins() {
        return mTotalWins;
    }

    public void setTotalWins(int totalWins) {
        mTotalWins = totalWins;
    }

    public int getTotalKillsHeadshot() {
        return mTotalKillsHeadshot;
    }

    public void setTotalKillsHeadshot(int totalKillsHeadshot) {
        mTotalKillsHeadshot = totalKillsHeadshot;
    }

    public int getTotalShotsHit() {
        return mTotalShotsHit;
    }

    public void setTotalShotsHit(int totalShotsHit) {
        mTotalShotsHit = totalShotsHit;
    }

    public int getTotalShotsFired() {
        return mTotalShotsFired;
    }

    public void setTotalShotsFired(int totalShotsFired) {
        mTotalShotsFired = totalShotsFired;
    }

    public float getTotalKd() {
        return mTotalKd;
    }

    public void setTotalKd(float totalKd) {
        mTotalKd = totalKd;
    }
}
