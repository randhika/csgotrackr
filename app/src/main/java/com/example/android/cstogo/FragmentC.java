package com.example.android.cstogo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


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
    private int mTotalRounds;
    private float mTotalKd;

    private WebMap dust2 = new WebMap("de_dust2");
    private WebMap inferno = new WebMap("de_inferno");
    private WebMap sugarcane = new WebMap("de_sugarcane");

    private ArrayList<WebMap> webMapList = new ArrayList<>();

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

        final String TAG = "AsyncTaskParseJson.java";

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
                        case "total_rounds_played":
                            setTotalRounds(Integer.valueOf(value));
                            break;
                        default:
                            break;
                    }

                    if (name.contains("_map_")){
                        String temp = name.replace("total_", "").replace("wins_", "").replace("rounds_", "").replace("map_", "");

                        switch (temp){
                            case "de_dust2":
                                if (name.contains("wins_")){
                                    dust2.setWins(Integer.valueOf(value));
                                } else {
                                    dust2.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_inferno":
                                if (name.contains("wins_")){
                                    inferno.setWins(Integer.valueOf(value));
                                } else {
                                    inferno.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_sugarcane":
                                if (name.contains("wins_")){
                                    sugarcane.setWins(Integer.valueOf(value));
                                } else {
                                    sugarcane.setRounds(Integer.valueOf(value));
                                }
                                break;
                            default:
                                break;
                        }

                        Log.d(TAG, name + " : " + value
                                + "\n" + temp + "");
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
        //HEADER START
        TextView web_total_kd = (TextView) view.findViewById(R.id.web_kd);
        TextView web_kills = (TextView) view.findViewById(R.id.web_total_kills_number);
        TextView web_win_perc = (TextView) view.findViewById(R.id.web_win_percent_number);
        TextView web_hs_perc = (TextView) view.findViewById(R.id.web_headshot_number);
        TextView web_accuracy_perc = (TextView) view.findViewById(R.id.web_accuracy_number);

        BigDecimal kills = new BigDecimal(getTotalKills());
        BigDecimal deaths = new BigDecimal(getTotalDeaths());
        BigDecimal kd = kills.divide(deaths, 2, BigDecimal.ROUND_HALF_UP);
        setTotalKd(kd.floatValue());

        BigDecimal headshots = new BigDecimal(getTotalKillsHeadshot());
        BigDecimal hs_perc = headshots.divide(kills.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);

        BigDecimal shots_fired = new BigDecimal(getTotalShotsFired());
        BigDecimal shots_hit = new BigDecimal(getTotalShotsHit());
        BigDecimal accuracy = shots_hit.divide(shots_fired.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);

        BigDecimal rounds = new BigDecimal(getTotalRounds());
        BigDecimal win_rounds = new BigDecimal(getTotalWins());
        BigDecimal win_perc = win_rounds.divide(rounds.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);

        web_total_kd.setText(String.valueOf(getTotalKd()));
        web_kills.setText(String.valueOf(getTotalKills()));
        web_win_perc.setText(String.valueOf(win_perc) + "%");
        web_hs_perc.setText(String.valueOf(hs_perc) + "%");
        web_accuracy_perc.setText(String.valueOf(accuracy) + "%");
        //HEADER END

        getWebMapList().add(dust2);
        getWebMapList().add(inferno);
        getWebMapList().add(sugarcane);

        float highestWinPerc = 0;
        String highestWinPercName = "";
        for (int i = 0; i < getWebMapList().size(); i++) {
            WebMap ci = getWebMapList().get(i);

            float tempWinPerc = ci.getWinPerc();
            if (tempWinPerc > highestWinPerc){
                highestWinPerc = tempWinPerc;
                highestWinPercName = ci.getName();
            }
        }
        TextView web_map_win_perc = (TextView) view.findViewById(R.id.web_highest_win_perc_number);

        web_map_win_perc.setText(" - " + highestWinPercName + " - " + highestWinPerc + "%");
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

    public int getTotalRounds() {
        return mTotalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        mTotalRounds = totalRounds;
    }

    public ArrayList<WebMap> getWebMapList() {
        return webMapList;
    }
}
