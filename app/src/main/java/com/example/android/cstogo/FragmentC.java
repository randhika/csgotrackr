package com.example.android.cstogo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private WebMap assault = new WebMap("cs_assault");
    private WebMap italy = new WebMap("cs_italy");
    private WebMap office = new WebMap("cs_office");
    private WebMap aztec = new WebMap("de_aztec");
    private WebMap cbble = new WebMap("de_cbble");
    private WebMap dust = new WebMap("de_dust");
    private WebMap dust2 = new WebMap("de_dust2");
    private WebMap inferno = new WebMap("de_inferno");
    private WebMap nuke = new WebMap("de_nuke");
    private WebMap train = new WebMap("de_train");
    private WebMap lake = new WebMap("de_lake");
    private WebMap safehouse = new WebMap("de_safehouse");
    private WebMap sugarcane = new WebMap("de_sugarcane");
    private WebMap stmarc = new WebMap("de_stmarc");
    private WebMap bank = new WebMap("de_bank");
    private WebMap shorttrain = new WebMap("de_shorttrain");
    private WebMap vertigo = new WebMap("de_vertigo");
    private WebMap monastery = new WebMap("ar_monastery");
    private WebMap shoots = new WebMap("ar_shoots");
    private WebMap baggage = new WebMap("ar_baggage");
    private WebMap militia = new WebMap("cs_militia");

    private WebGun ak47 = new WebGun("AK47");
    private WebGun aug = new WebGun("AUG");
    private WebGun awp = new WebGun("AWP");
    private WebGun bizon = new WebGun("Bizon");
    private WebGun deagle = new WebGun("Deagle");
    private WebGun elite = new WebGun("Dual Beretta");
    private WebGun famas = new WebGun("Famas");
    private WebGun fiveseven = new WebGun("Five-Seven");
    private WebGun g3sg1 = new WebGun("G3SG1");
    private WebGun galilar = new WebGun("Galil AR");
    private WebGun glock = new WebGun("Glock");
    private WebGun hkp2000 = new WebGun("P2000");
    private WebGun m249 = new WebGun("M249");
    private WebGun m4a1 = new WebGun("M4");
    private WebGun mac10 = new WebGun("mac10");
    private WebGun mag7 = new WebGun("mag-7");
    private WebGun mp7 = new WebGun("mp7");
    private WebGun mp9 = new WebGun("mp9");
    private WebGun negev = new WebGun("negev");
    private WebGun nova = new WebGun("nova");
    private WebGun p250 = new WebGun("P250");
    private WebGun p90 = new WebGun("P90");
    private WebGun sawedoff = new WebGun("Sawed-Off");
    private WebGun scar20 = new WebGun("scar-20");
    private WebGun sg556 = new WebGun("SG 553");
    private WebGun ssg08 = new WebGun("SSG 08");
    private WebGun tec9 = new WebGun("TEC-9");
    private WebGun ump45 = new WebGun("ump-45");
    private WebGun xm1014 = new WebGun("XM1014");

    private ArrayList<WebMap> webMapList = new ArrayList<>();
    private ArrayList<WebGun> webGunList = new ArrayList<>();

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

        // set your json string url here
        String yourJsonStringUrl = "http://cityinfocen.zz.vc/stats.json"; //test offline site

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected View doInBackground(View... arg0) {

            try {

                // instantiate our json parser
                JSONParser jParser = new JSONParser();

                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

                // get the array of users
                if (json == null) {
                    return null;
                }
                dataJsonArr = json.getJSONObject("playerstats").getJSONArray("stats");

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
                            case "de_aztec":
                                if (name.contains("wins_")){
                                        aztec.setWins(Integer.valueOf(value));
                                    } else {
                                        aztec.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_cbble":
                                if (name.contains("wins_")){
                                        cbble.setWins(Integer.valueOf(value));
                                    } else {
                                        cbble.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_dust":
                                if (name.contains("wins_")){
                                        dust.setWins(Integer.valueOf(value));
                                    } else {
                                        dust.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_dust2":
                                if (name.contains("wins_")){
                                    dust2.setWins(Integer.valueOf(value));
                                } else {
                                    dust2.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_nuke":
                                if (name.contains("wins_")){
                                        nuke.setWins(Integer.valueOf(value));
                                    } else {
                                        nuke.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_inferno":
                                if (name.contains("wins_")){
                                    inferno.setWins(Integer.valueOf(value));
                                } else {
                                    inferno.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_train":
                                if (name.contains("wins_")){
                                        train.setWins(Integer.valueOf(value));
                                    } else {
                                        train.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_lake":
                                if (name.contains("wins_")){
                                        lake.setWins(Integer.valueOf(value));
                                    } else {
                                        lake.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_safehouse":
                                if (name.contains("wins_")){
                                        safehouse.setWins(Integer.valueOf(value));
                                    } else {
                                        safehouse.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_sugarcane":
                                if (name.contains("wins_")){
                                    sugarcane.setWins(Integer.valueOf(value));
                                } else {
                                    sugarcane.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_stmarc":
                                if (name.contains("wins_")){
                                        stmarc.setWins(Integer.valueOf(value));
                                    } else {
                                        stmarc.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_bank":
                                if (name.contains("wins_")){
                                        bank.setWins(Integer.valueOf(value));
                                    } else {
                                        bank.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_shorttrain":
                                if (name.contains("wins_")){
                                        shorttrain.setWins(Integer.valueOf(value));
                                    } else {
                                        shorttrain.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "de_vertigo":
                                if (name.contains("wins_")){
                                        vertigo.setWins(Integer.valueOf(value));
                                    } else {
                                        vertigo.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "cs_assault":
                                if (name.contains("wins_")){
                                        assault.setWins(Integer.valueOf(value));
                                    } else {
                                        assault.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "cs_italy":
                                if (name.contains("wins_")){
                                        italy.setWins(Integer.valueOf(value));
                                    } else {
                                        italy.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "cs_office":
                                if (name.contains("wins_")){
                                        office.setWins(Integer.valueOf(value));
                                    } else {
                                        office.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "cs_militia":
                                if (name.contains("wins_")){
                                        militia.setWins(Integer.valueOf(value));
                                    } else {
                                        militia.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "ar_monastery":
                                if (name.contains("wins_")){
                                        monastery.setWins(Integer.valueOf(value));
                                    } else {
                                        monastery.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "ar_shoots":
                                if (name.contains("wins_")){
                                        shoots.setWins(Integer.valueOf(value));
                                    } else {
                                        shoots.setRounds(Integer.valueOf(value));
                                }
                                break;
                            case "ar_baggage":
                                if (name.contains("wins_")){
                                        baggage.setWins(Integer.valueOf(value));
                                    } else {
                                        baggage.setRounds(Integer.valueOf(value));
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    if (name.contains("total_")){
                        String temp = name.replace("total_", "").replace("kills_", "").replace("shots_", "").replace("hits_", "");

                        switch (temp){
                            case "ak47":
                                if (name.contains("kills_")){
                                    ak47.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    ak47.setShots(Integer.valueOf(value));
                                } else {
                                    ak47.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "aug":
                                if (name.contains("kills_")){
                                    aug.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    aug.setShots(Integer.valueOf(value));
                                } else {
                                    aug.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "awp":
                                if (name.contains("kills_")){
                                    awp.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    awp.setShots(Integer.valueOf(value));
                                } else {
                                    awp.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "bizon":
                                if (name.contains("kills_")){
                                    bizon.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    bizon.setShots(Integer.valueOf(value));
                                } else {
                                    bizon.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "deagle":
                                if (name.contains("kills_")){
                                    deagle.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    deagle.setShots(Integer.valueOf(value));
                                } else {
                                    deagle.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "elite":
                                if (name.contains("kills_")){
                                    elite.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    elite.setShots(Integer.valueOf(value));
                                } else {
                                    elite.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "famas":
                                if (name.contains("kills_")){
                                    famas.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    famas.setShots(Integer.valueOf(value));
                                } else {
                                    famas.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "fiveseven":
                                if (name.contains("kills_")){
                                    fiveseven.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    fiveseven.setShots(Integer.valueOf(value));
                                } else {
                                    fiveseven.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "g3sg1":
                                if (name.contains("kills_")){
                                    g3sg1.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    g3sg1.setShots(Integer.valueOf(value));
                                } else {
                                    g3sg1.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "galilar":
                                if (name.contains("kills_")){
                                    galilar.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    galilar.setShots(Integer.valueOf(value));
                                } else {
                                    galilar.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "glock":
                                if (name.contains("kills_")){
                                    glock.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    glock.setShots(Integer.valueOf(value));
                                } else {
                                    glock.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "hkp2000":
                                if (name.contains("kills_")){
                                    hkp2000.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    hkp2000.setShots(Integer.valueOf(value));
                                } else {
                                    hkp2000.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "m249":
                                if (name.contains("kills_")){
                                    m249.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    m249.setShots(Integer.valueOf(value));
                                } else {
                                    m249.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "m4a1":
                                if (name.contains("kills_")){
                                    m4a1.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    m4a1.setShots(Integer.valueOf(value));
                                } else {
                                    m4a1.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "mac10":
                                if (name.contains("kills_")){
                                    mac10.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    mac10.setShots(Integer.valueOf(value));
                                } else {
                                    mac10.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "mag7":
                                if (name.contains("kills_")){
                                    mag7.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    mag7.setShots(Integer.valueOf(value));
                                } else {
                                    mag7.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "mp7":
                                if (name.contains("kills_")){
                                    mp7.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    mp7.setShots(Integer.valueOf(value));
                                } else {
                                    mp7.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "mp9":
                                if (name.contains("kills_")){
                                    mp9.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    mp9.setShots(Integer.valueOf(value));
                                } else {
                                    mp9.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "negev":
                                if (name.contains("kills_")){
                                    negev.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    negev.setShots(Integer.valueOf(value));
                                } else {
                                    negev.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "nova":
                                if (name.contains("kills_")){
                                    nova.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    nova.setShots(Integer.valueOf(value));
                                } else {
                                    nova.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "p250":
                                if (name.contains("kills_")){
                                    p250.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    p250.setShots(Integer.valueOf(value));
                                } else {
                                    p250.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "p90":
                                if (name.contains("kills_")){
                                    p90.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    p90.setShots(Integer.valueOf(value));
                                } else {
                                    p90.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "sawedoff":
                                if (name.contains("kills_")){
                                    sawedoff.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    sawedoff.setShots(Integer.valueOf(value));
                                } else {
                                    sawedoff.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "scar20":
                                if (name.contains("kills_")){
                                    scar20.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    scar20.setShots(Integer.valueOf(value));
                                } else {
                                    scar20.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "sg556":
                                if (name.contains("kills_")){
                                    sg556.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    sg556.setShots(Integer.valueOf(value));
                                } else {
                                    sg556.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "ssg08":
                                if (name.contains("kills_")){
                                    ssg08.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    ssg08.setShots(Integer.valueOf(value));
                                } else {
                                    ssg08.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "tec9":
                                if (name.contains("kills_")){
                                    tec9.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    tec9.setShots(Integer.valueOf(value));
                                } else {
                                    tec9.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "ump45":
                                if (name.contains("kills_")){
                                    ump45.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    ump45.setShots(Integer.valueOf(value));
                                } else {
                                    ump45.setHits(Integer.valueOf(value));
                                }
                                break;
                            case "xm1014":
                                if (name.contains("kills_")){
                                    xm1014.setKills(Integer.valueOf(value));
                                } else if (name.contains("shots_")){
                                    xm1014.setShots(Integer.valueOf(value));
                                } else {
                                    xm1014.setHits(Integer.valueOf(value));
                                }
                                break;
                            default:
                                break;
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return arg0[0];
        }

        @Override
        protected void onPostExecute(View view) {
            if (view == null){
                Toast.makeText(getActivity().getApplicationContext(), "Error",
                        Toast.LENGTH_SHORT).show();
            } else {
                createTextViews(view);
            }
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
        BigDecimal hs_perc = headshots.divide(kills.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 1, RoundingMode.HALF_UP);

        BigDecimal shots_fired = new BigDecimal(getTotalShotsFired());
        BigDecimal shots_hit = new BigDecimal(getTotalShotsHit());
        BigDecimal accuracy = shots_hit.divide(shots_fired.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 1, RoundingMode.HALF_UP);

        BigDecimal rounds = new BigDecimal(getTotalRounds());
        BigDecimal win_rounds = new BigDecimal(getTotalWins());
        BigDecimal win_perc = win_rounds.divide(rounds.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 1, RoundingMode.HALF_UP);

        web_total_kd.setText(String.valueOf(getTotalKd()));
        web_kills.setText(String.valueOf(getTotalKills()));
        web_win_perc.setText(String.valueOf(win_perc) + "%");
        web_hs_perc.setText(String.valueOf(hs_perc) + "%");
        web_accuracy_perc.setText(String.valueOf(accuracy) + "%");
        //HEADER END
        getWebMapList().clear();
        getWebMapList().add(assault);
        getWebMapList().add(italy);
        getWebMapList().add(office);
        getWebMapList().add(aztec);
        getWebMapList().add(cbble);
        getWebMapList().add(dust);
        getWebMapList().add(dust2);
        getWebMapList().add(inferno);
        getWebMapList().add(nuke);
        getWebMapList().add(train);
        getWebMapList().add(lake);
        getWebMapList().add(safehouse);
        getWebMapList().add(sugarcane);
        getWebMapList().add(stmarc);
        getWebMapList().add(bank);
        getWebMapList().add(shorttrain);
        getWebMapList().add(vertigo);
        getWebMapList().add(monastery);
        getWebMapList().add(shoots);
        getWebMapList().add(baggage);
        getWebMapList().add(militia);

        float highestWinPerc = 0;
        int highestRounds = 0;
        int highestWins = 0;
        String highestWinPercName = "";
        String highestRoundsName = "";
        String highestWinsName = "";

        for (int i = 0; i < getWebMapList().size(); i++) {
            WebMap ci = getWebMapList().get(i);

            float tempWinPerc = ci.getWinPerc();
            int tempRounds = ci.getRounds();
            int tempWins = ci.getWins();

            if (tempWinPerc > highestWinPerc){
                highestWinPerc = tempWinPerc;
                highestWinPercName = ci.getWebMapName();
            }
            if (tempRounds > highestRounds){
                highestRounds = tempRounds;
                highestRoundsName = ci.getWebMapName();
            }
            if (tempWins > highestWins){
                highestWins = tempWins;
                highestWinsName = ci.getWebMapName();
            }
        }
        TextView web_map_win_perc = (TextView) view.findViewById(R.id.web_highest_win_perc_number);
        TextView web_map_wins = (TextView) view.findViewById(R.id.web_highest_wins_number);
        TextView web_map_rounds = (TextView) view.findViewById(R.id.web_highest_rounds_number);


        web_map_win_perc.setText(highestWinPercName + " - " + highestWinPerc + "%");
        web_map_wins.setText(highestWinsName+ " - " + highestWins);
        web_map_rounds.setText(highestRoundsName+ " - " + highestRounds);

        getWebGunList().add(ak47);
        getWebGunList().add(aug);
        getWebGunList().add(awp);
        getWebGunList().add(bizon);
        getWebGunList().add(deagle);
        getWebGunList().add(elite);
        getWebGunList().add(famas);
        getWebGunList().add(fiveseven);
        getWebGunList().add(g3sg1);
        getWebGunList().add(galilar);
        getWebGunList().add(glock);
        getWebGunList().add(hkp2000);
        getWebGunList().add(m249);
        getWebGunList().add(m4a1);
        getWebGunList().add(mac10);
        getWebGunList().add(mag7);
        getWebGunList().add(mp7);
        getWebGunList().add(mp9);
        getWebGunList().add(negev);
        getWebGunList().add(nova);
        getWebGunList().add(p250);
        getWebGunList().add(p90);
        getWebGunList().add(sawedoff);
        getWebGunList().add(scar20);
        getWebGunList().add(sg556);
        getWebGunList().add(ssg08);
        getWebGunList().add(tec9);
        getWebGunList().add(ump45);
        getWebGunList().add(xm1014);

        int highestShots = 0;
        int highestHits = 0;
        int highestKills = 0;
        float highestAcc = 0;
        String highestShotsName = "";
        String highestHitsName = "";
        String highestKillsName = "";
        String highestAccName = "";

        for (int i = 0; i < getWebGunList().size(); i++) {
            WebGun ci = getWebGunList().get(i);

            int tempShots = ci.getShots();
            int tempHits = ci.getHits();
            int tempKills = ci.getKills();
            float tempAcc = ci.getAccuracy();

            if (tempShots > highestShots){
                highestShots = tempShots;
                highestShotsName = ci.getGunName();
            }
            if (tempHits > highestHits){
                highestHits = tempHits;
                highestHitsName = ci.getGunName();
            }
            if (tempKills > highestKills){
                highestKills = tempKills;
                highestKillsName = ci.getGunName();
            }
            if (tempAcc > highestAcc){
                highestAcc = tempAcc;
                highestAccName = ci.getGunName();
            }

        }

        TextView fav_wep_shots = (TextView) view.findViewById(R.id.web_fav_wep_shots);
        TextView fav_wep_hits = (TextView) view.findViewById(R.id.web_fav_wep_hits);
        TextView fav_wep_kills = (TextView) view.findViewById(R.id.web_fav_wep_kills);
        TextView fav_wep_accuracy = (TextView) view.findViewById(R.id.web_fav_wep_accuracy);

        fav_wep_shots.setText(highestShotsName + " - " + highestShots);
        fav_wep_hits.setText(highestHitsName + " - " + highestHits);
        fav_wep_kills.setText(highestKillsName + " - " + highestKills);
        fav_wep_accuracy.setText(highestAccName + " - " + highestAcc);
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

    public ArrayList<WebGun> getWebGunList() {
        return webGunList;
    }

    public ArrayList<WebMap> getWebMapList() {
        return webMapList;
    }

}
