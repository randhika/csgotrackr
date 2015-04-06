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
    private int mTotalPlanted;
    private int mTotalDefused;
    private int mTotalDamageDone;
    private int mTotalMoneyEarned;
    private int mTotalRescuedHostages;
    private int mTotalKillsKnife;
    private int mTotalKillsHeGrenade;
    private int mTotalKillsEnemyWeapon;
    private int mTotalWeaponsDonated;
    private int mTotalBrokenWindows;
    private int mTotalKillsEnemyBlinded;
    private int mTotalKillsAgainstZoomedSniper;
    private int mTotalDominations;
    private int mTotalRevenges;
    private int mTotalMvps;
    private int mTotalKillsMolotov;
    private int mTotalKillsTaser;
    private int mTotalShotsTaser;

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
                    int intValue = c.getInt("value");

                    switch (name){
                        case "total_kills":
                            setTotalKills(intValue);
                            break;
                        case "total_deaths":
                            setTotalDeaths(intValue);
                            break;
                        case "total_wins":
                            setTotalWins(intValue);
                            break;
                        case "total_kills_headshot":
                            setTotalKillsHeadshot(intValue);
                            break;
                        case "total_shots_hit":
                            setTotalShotsHit(intValue);
                            break;
                        case "total_shots_fired":
                            setTotalShotsFired(intValue);
                            break;
                        case "total_rounds_played":
                            setTotalRounds(intValue);
                            break;
                        case "total_planted_bombs":
                            setTotalPlanted(intValue);
                            break;
                        case "total_defused_bombs":
                            setTotalDefused(intValue);
                            break;
                        case "total_damage_done":
                            setTotalDamageDone(intValue);
                            break;
                        case "total_money_earned":
                            setTotalMoneyEarned(intValue);
                            break;
                        case "total_rescued_hostages":
                            setTotalRescuedHostages(intValue);
                            break;
                        case "total_kills_knife":
                            setTotalKillsKnife(intValue);
                            break;
                        case "total_kills_hegrenade":
                            setTotalKillsHeGrenade(intValue);
                            break;
                        case "total_kills_enemy_weapon":
                            setTotalKillsEnemyWeapon(intValue);
                            break;
                        case "total_weapons_donated":
                            setTotalWeaponsDonated(intValue);
                            break;
                        case "total_broken_windows":
                            setTotalBrokenWindows(intValue);
                            break;
                        case "total_kills_enemy_blinded":
                            setTotalKillsEnemyBlinded(intValue);
                            break;
                        case "total_kills_against_zoomed_sniper":
                            setTotalKillsAgainstZoomedSniper(intValue);
                            break;
                        case "total_dominations":
                            setTotalDominations(intValue);
                            break;
                        case "total_revenges":
                            setTotalRevenges(intValue);
                            break;
                        case "total_mvps":
                            setTotalMvps(intValue);
                            break;
                        case "total_kills_molotov":
                            setTotalKillsMolotov(intValue);
                            break;
                        case "total_kills_taser":
                            setTotalKillsTaser(intValue);
                            break;
                        case "total_shots_taser":
                            setTotalShotsTaser(intValue);
                            break;
                        default:
                            break;
                    }

                    if (name.contains("_map_")){
                        String temp = name.replace("total_", "").replace("wins_", "").replace("rounds_", "").replace("map_", "");
                        switch (temp){
                            case "de_aztec":
                                if (name.contains("wins_")){
                                        aztec.setWins(intValue);
                                    } else {
                                        aztec.setRounds(intValue);
                                }
                                break;
                            case "de_cbble":
                                if (name.contains("wins_")){
                                        cbble.setWins(intValue);
                                    } else {
                                        cbble.setRounds(intValue);
                                }
                                break;
                            case "de_dust":
                                if (name.contains("wins_")){
                                        dust.setWins(intValue);
                                    } else {
                                        dust.setRounds(intValue);
                                }
                                break;
                            case "de_dust2":
                                if (name.contains("wins_")){
                                    dust2.setWins(intValue);
                                } else {
                                    dust2.setRounds(intValue);
                                }
                                break;
                            case "de_nuke":
                                if (name.contains("wins_")){
                                        nuke.setWins(intValue);
                                    } else {
                                        nuke.setRounds(intValue);
                                }
                                break;
                            case "de_inferno":
                                if (name.contains("wins_")){
                                    inferno.setWins(intValue);
                                } else {
                                    inferno.setRounds(intValue);
                                }
                                break;
                            case "de_train":
                                if (name.contains("wins_")){
                                        train.setWins(intValue);
                                    } else {
                                        train.setRounds(intValue);
                                }
                                break;
                            case "de_lake":
                                if (name.contains("wins_")){
                                        lake.setWins(intValue);
                                    } else {
                                        lake.setRounds(intValue);
                                }
                                break;
                            case "de_safehouse":
                                if (name.contains("wins_")){
                                        safehouse.setWins(intValue);
                                    } else {
                                        safehouse.setRounds(intValue);
                                }
                                break;
                            case "de_sugarcane":
                                if (name.contains("wins_")){
                                    sugarcane.setWins(intValue);
                                } else {
                                    sugarcane.setRounds(intValue);
                                }
                                break;
                            case "de_stmarc":
                                if (name.contains("wins_")){
                                        stmarc.setWins(intValue);
                                    } else {
                                        stmarc.setRounds(intValue);
                                }
                                break;
                            case "de_bank":
                                if (name.contains("wins_")){
                                        bank.setWins(intValue);
                                    } else {
                                        bank.setRounds(intValue);
                                }
                                break;
                            case "de_shorttrain":
                                if (name.contains("wins_")){
                                        shorttrain.setWins(intValue);
                                    } else {
                                        shorttrain.setRounds(intValue);
                                }
                                break;
                            case "de_vertigo":
                                if (name.contains("wins_")){
                                        vertigo.setWins(intValue);
                                    } else {
                                        vertigo.setRounds(intValue);
                                }
                                break;
                            case "cs_assault":
                                if (name.contains("wins_")){
                                        assault.setWins(intValue);
                                    } else {
                                        assault.setRounds(intValue);
                                }
                                break;
                            case "cs_italy":
                                if (name.contains("wins_")){
                                        italy.setWins(intValue);
                                    } else {
                                        italy.setRounds(intValue);
                                }
                                break;
                            case "cs_office":
                                if (name.contains("wins_")){
                                        office.setWins(intValue);
                                    } else {
                                        office.setRounds(intValue);
                                }
                                break;
                            case "cs_militia":
                                if (name.contains("wins_")){
                                        militia.setWins(intValue);
                                    } else {
                                        militia.setRounds(intValue);
                                }
                                break;
                            case "ar_monastery":
                                if (name.contains("wins_")){
                                        monastery.setWins(intValue);
                                    } else {
                                        monastery.setRounds(intValue);
                                }
                                break;
                            case "ar_shoots":
                                if (name.contains("wins_")){
                                        shoots.setWins(intValue);
                                    } else {
                                        shoots.setRounds(intValue);
                                }
                                break;
                            case "ar_baggage":
                                if (name.contains("wins_")){
                                        baggage.setWins(intValue);
                                    } else {
                                        baggage.setRounds(intValue);
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
                                    ak47.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    ak47.setShots(intValue);
                                } else {
                                    ak47.setHits(intValue);
                                }
                                break;
                            case "aug":
                                if (name.contains("kills_")){
                                    aug.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    aug.setShots(intValue);
                                } else {
                                    aug.setHits(intValue);
                                }
                                break;
                            case "awp":
                                if (name.contains("kills_")){
                                    awp.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    awp.setShots(intValue);
                                } else {
                                    awp.setHits(intValue);
                                }
                                break;
                            case "bizon":
                                if (name.contains("kills_")){
                                    bizon.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    bizon.setShots(intValue);
                                } else {
                                    bizon.setHits(intValue);
                                }
                                break;
                            case "deagle":
                                if (name.contains("kills_")){
                                    deagle.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    deagle.setShots(intValue);
                                } else {
                                    deagle.setHits(intValue);
                                }
                                break;
                            case "elite":
                                if (name.contains("kills_")){
                                    elite.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    elite.setShots(intValue);
                                } else {
                                    elite.setHits(intValue);
                                }
                                break;
                            case "famas":
                                if (name.contains("kills_")){
                                    famas.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    famas.setShots(intValue);
                                } else {
                                    famas.setHits(intValue);
                                }
                                break;
                            case "fiveseven":
                                if (name.contains("kills_")){
                                    fiveseven.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    fiveseven.setShots(intValue);
                                } else {
                                    fiveseven.setHits(intValue);
                                }
                                break;
                            case "g3sg1":
                                if (name.contains("kills_")){
                                    g3sg1.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    g3sg1.setShots(intValue);
                                } else {
                                    g3sg1.setHits(intValue);
                                }
                                break;
                            case "galilar":
                                if (name.contains("kills_")){
                                    galilar.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    galilar.setShots(intValue);
                                } else {
                                    galilar.setHits(intValue);
                                }
                                break;
                            case "glock":
                                if (name.contains("kills_")){
                                    glock.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    glock.setShots(intValue);
                                } else {
                                    glock.setHits(intValue);
                                }
                                break;
                            case "hkp2000":
                                if (name.contains("kills_")){
                                    hkp2000.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    hkp2000.setShots(intValue);
                                } else {
                                    hkp2000.setHits(intValue);
                                }
                                break;
                            case "m249":
                                if (name.contains("kills_")){
                                    m249.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    m249.setShots(intValue);
                                } else {
                                    m249.setHits(intValue);
                                }
                                break;
                            case "m4a1":
                                if (name.contains("kills_")){
                                    m4a1.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    m4a1.setShots(intValue);
                                } else {
                                    m4a1.setHits(intValue);
                                }
                                break;
                            case "mac10":
                                if (name.contains("kills_")){
                                    mac10.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    mac10.setShots(intValue);
                                } else {
                                    mac10.setHits(intValue);
                                }
                                break;
                            case "mag7":
                                if (name.contains("kills_")){
                                    mag7.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    mag7.setShots(intValue);
                                } else {
                                    mag7.setHits(intValue);
                                }
                                break;
                            case "mp7":
                                if (name.contains("kills_")){
                                    mp7.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    mp7.setShots(intValue);
                                } else {
                                    mp7.setHits(intValue);
                                }
                                break;
                            case "mp9":
                                if (name.contains("kills_")){
                                    mp9.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    mp9.setShots(intValue);
                                } else {
                                    mp9.setHits(intValue);
                                }
                                break;
                            case "negev":
                                if (name.contains("kills_")){
                                    negev.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    negev.setShots(intValue);
                                } else {
                                    negev.setHits(intValue);
                                }
                                break;
                            case "nova":
                                if (name.contains("kills_")){
                                    nova.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    nova.setShots(intValue);
                                } else {
                                    nova.setHits(intValue);
                                }
                                break;
                            case "p250":
                                if (name.contains("kills_")){
                                    p250.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    p250.setShots(intValue);
                                } else {
                                    p250.setHits(intValue);
                                }
                                break;
                            case "p90":
                                if (name.contains("kills_")){
                                    p90.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    p90.setShots(intValue);
                                } else {
                                    p90.setHits(intValue);
                                }
                                break;
                            case "sawedoff":
                                if (name.contains("kills_")){
                                    sawedoff.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    sawedoff.setShots(intValue);
                                } else {
                                    sawedoff.setHits(intValue);
                                }
                                break;
                            case "scar20":
                                if (name.contains("kills_")){
                                    scar20.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    scar20.setShots(intValue);
                                } else {
                                    scar20.setHits(intValue);
                                }
                                break;
                            case "sg556":
                                if (name.contains("kills_")){
                                    sg556.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    sg556.setShots(intValue);
                                } else {
                                    sg556.setHits(intValue);
                                }
                                break;
                            case "ssg08":
                                if (name.contains("kills_")){
                                    ssg08.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    ssg08.setShots(intValue);
                                } else {
                                    ssg08.setHits(intValue);
                                }
                                break;
                            case "tec9":
                                if (name.contains("kills_")){
                                    tec9.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    tec9.setShots(intValue);
                                } else {
                                    tec9.setHits(intValue);
                                }
                                break;
                            case "ump45":
                                if (name.contains("kills_")){
                                    ump45.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    ump45.setShots(intValue);
                                } else {
                                    ump45.setHits(intValue);
                                }
                                break;
                            case "xm1014":
                                if (name.contains("kills_")){
                                    xm1014.setKills(intValue);
                                } else if (name.contains("shots_")){
                                    xm1014.setShots(intValue);
                                } else {
                                    xm1014.setHits(intValue);
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
        web_win_perc.setText(win_perc + "%");
        web_hs_perc.setText(hs_perc + "%");
        web_accuracy_perc.setText(accuracy + "%");
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
        TextView web_map_win_perc_name = (TextView) view.findViewById(R.id.web_highest_win_perc_number);
        TextView web_map_wins_name = (TextView) view.findViewById(R.id.web_highest_wins_number);
        TextView web_map_rounds_name = (TextView) view.findViewById(R.id.web_highest_rounds_number);

        TextView web_map_win_perc_number = (TextView) view.findViewById(R.id.web_highest_win_perc_map);
        TextView web_map_wins_number = (TextView) view.findViewById(R.id.web_highest_wins_map);
        TextView web_map_rounds_number = (TextView) view.findViewById(R.id.web_highest_rounds_map);

        web_map_win_perc_name.setText(highestWinPercName);
        web_map_wins_name.setText(highestWinsName);
        web_map_rounds_name.setText(highestRoundsName);

        web_map_win_perc_number.setText(highestWinPerc + " %");
        web_map_wins_number.setText(String.valueOf(highestWins));
        web_map_rounds_number.setText(String.valueOf(highestRounds));

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

        TextView fav_wep_shots_name = (TextView) view.findViewById(R.id.web_fav_wep_shots);
        TextView fav_wep_hits_name = (TextView) view.findViewById(R.id.web_fav_wep_hits);
        TextView fav_wep_kills_name = (TextView) view.findViewById(R.id.web_fav_wep_kills);
        TextView fav_wep_accuracy_name = (TextView) view.findViewById(R.id.web_fav_wep_accuracy);

        TextView fav_wep_shots_number = (TextView) view.findViewById(R.id.web_highest_shots_gun);
        TextView fav_wep_hits_number = (TextView) view.findViewById(R.id.web_highest_hits_gun);
        TextView fav_wep_kills_number = (TextView) view.findViewById(R.id.web_highest_kills_gun);
        TextView fav_wep_accuracy_number = (TextView) view.findViewById(R.id.web_highest_accuracy_gun);

        fav_wep_shots_name.setText(highestShotsName);
        fav_wep_hits_name.setText(highestHitsName);
        fav_wep_kills_name.setText(highestKillsName);
        fav_wep_accuracy_name.setText(highestAccName);

        fav_wep_shots_number.setText(String.valueOf(highestShots));
        fav_wep_hits_number.setText(String.valueOf(highestHits));
        fav_wep_kills_number.setText(String.valueOf(highestKills));
        fav_wep_accuracy_number.setText(highestAcc + " %");

        TextView web_other_kills = (TextView) view.findViewById(R.id.web_other_kills);
        web_other_kills.setText(String.valueOf(getTotalKills()));
        TextView web_other_heashots = (TextView) view.findViewById(R.id.web_other_headshots);
        web_other_heashots.setText(String.valueOf(getTotalKillsHeadshot()));
        TextView web_other_deaths = (TextView) view.findViewById(R.id.web_other_deaths);
        web_other_deaths.setText(String.valueOf(getTotalDeaths()));
        TextView web_other_wins = (TextView) view.findViewById(R.id.web_other_wins);
        web_other_wins.setText(String.valueOf(getTotalWins()));
        TextView web_other_rounds = (TextView) view.findViewById(R.id.web_other_rounds);
        web_other_rounds.setText(String.valueOf(getTotalRounds()));
        TextView web_other_planted = (TextView) view.findViewById(R.id.web_other_planted);
        web_other_planted.setText(String.valueOf(getTotalPlanted()));
        TextView web_other_defused = (TextView) view.findViewById(R.id.web_other_defused);
        web_other_defused.setText(String.valueOf(getTotalDefused()));
        TextView web_other_damage_done = (TextView) view.findViewById(R.id.web_other_damage_done);
        web_other_damage_done.setText(String.valueOf(getTotalDamageDone()));
        TextView web_other_money_earned = (TextView) view.findViewById(R.id.web_other_money_earned);
        web_other_money_earned.setText("$" + String.valueOf(getTotalMoneyEarned()));
        TextView web_other_hostages_rescued = (TextView) view.findViewById(R.id.web_other_hostages_rescued);
        web_other_hostages_rescued.setText(String.valueOf(getTotalRescuedHostages()));
        TextView web_other_mvps = (TextView) view.findViewById(R.id.web_other_mvps);
        web_other_mvps.setText(String.valueOf(getTotalMvps()));
        TextView web_other_enemy_weapon = (TextView) view.findViewById(R.id.web_other_enemy_weapon);
        web_other_enemy_weapon.setText(String.valueOf(getTotalKillsEnemyWeapon()));

        TextView web_other_shots_fired = (TextView) view.findViewById(R.id.web_other_shots_fired);
        web_other_shots_fired.setText(String.valueOf(getTotalShotsFired()));
        TextView web_other_shots_hit = (TextView) view.findViewById(R.id.web_other_shots_hit);
        web_other_shots_hit.setText(String.valueOf(getTotalShotsHit()));
        TextView web_other_zeus_shots = (TextView) view.findViewById(R.id.web_other_zeus_shots);
        web_other_zeus_shots.setText(String.valueOf(getTotalShotsTaser()));
        TextView web_other_zeus_kills = (TextView) view.findViewById(R.id.web_other_zeus_kills);
        web_other_zeus_kills.setText(String.valueOf(getTotalKillsTaser()));
        TextView web_other_molotov = (TextView) view.findViewById(R.id.web_other_molotov);
        web_other_molotov.setText(String.valueOf(getTotalKillsMolotov()));
        TextView web_other_nade = (TextView) view.findViewById(R.id.web_other_nade);
        web_other_nade.setText(String.valueOf(getTotalKillsHeGrenade()));
        TextView web_other_knife = (TextView) view.findViewById(R.id.web_other_knife);
        web_other_knife.setText(String.valueOf(getTotalKillsKnife()));
        TextView web_other_flash = (TextView) view.findViewById(R.id.web_other_flash);
        web_other_flash.setText(String.valueOf(getTotalKillsEnemyBlinded()));
        TextView web_other_zoomed = (TextView) view.findViewById(R.id.web_other_zoomed);
        web_other_zoomed.setText(String.valueOf(getTotalKillsAgainstZoomedSniper()));
        TextView web_other_dominations = (TextView) view.findViewById(R.id.web_other_dominations);
        web_other_dominations.setText(String.valueOf(getTotalDominations()));
        TextView web_other_revenges = (TextView) view.findViewById(R.id.web_other_revenges);
        web_other_revenges.setText(String.valueOf(getTotalRevenges()));
        TextView web_other_donated = (TextView) view.findViewById(R.id.web_other_donated);
        web_other_donated.setText(String.valueOf(getTotalWeaponsDonated()));
        TextView web_other_broken_windows = (TextView) view.findViewById(R.id.web_other_broken_windows);
        web_other_broken_windows.setText(String.valueOf(getTotalBrokenWindows()));


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

    public int getTotalPlanted() {
        return mTotalPlanted;
    }

    public void setTotalPlanted(int totalPlanted) {
        mTotalPlanted = totalPlanted;
    }

    public int getTotalDefused() {
        return mTotalDefused;
    }

    public void setTotalDefused(int totalDefused) {
        mTotalDefused = totalDefused;
    }

    public int getTotalDamageDone() {
        return mTotalDamageDone;
    }

    public void setTotalDamageDone(int totalDamageDone) {
        mTotalDamageDone = totalDamageDone;
    }

    public int getTotalMoneyEarned() {
        return mTotalMoneyEarned;
    }

    public void setTotalMoneyEarned(int totalMoneyEarned) {
        mTotalMoneyEarned = totalMoneyEarned;
    }

    public int getTotalRescuedHostages() {
        return mTotalRescuedHostages;
    }

    public void setTotalRescuedHostages(int totalRescuedHostages) {
        mTotalRescuedHostages = totalRescuedHostages;
    }

    public int getTotalKillsKnife() {
        return mTotalKillsKnife;
    }

    public void setTotalKillsKnife(int totalKillsKnife) {
        mTotalKillsKnife = totalKillsKnife;
    }

    public int getTotalKillsHeGrenade() {
        return mTotalKillsHeGrenade;
    }

    public void setTotalKillsHeGrenade(int totalKillsHeGrenade) {
        mTotalKillsHeGrenade = totalKillsHeGrenade;
    }

    public int getTotalKillsEnemyWeapon() {
        return mTotalKillsEnemyWeapon;
    }

    public void setTotalKillsEnemyWeapon(int totalKillsEnemyWeapon) {
        mTotalKillsEnemyWeapon = totalKillsEnemyWeapon;
    }

    public int getTotalWeaponsDonated() {
        return mTotalWeaponsDonated;
    }

    public void setTotalWeaponsDonated(int totalWeaponsDonated) {
        mTotalWeaponsDonated = totalWeaponsDonated;
    }

    public int getTotalBrokenWindows() {
        return mTotalBrokenWindows;
    }

    public void setTotalBrokenWindows(int totalBrokenWindows) {
        mTotalBrokenWindows = totalBrokenWindows;
    }

    public int getTotalKillsEnemyBlinded() {
        return mTotalKillsEnemyBlinded;
    }

    public void setTotalKillsEnemyBlinded(int totalKillsEnemyBlinded) {
        mTotalKillsEnemyBlinded = totalKillsEnemyBlinded;
    }

    public int getTotalKillsAgainstZoomedSniper() {
        return mTotalKillsAgainstZoomedSniper;
    }

    public void setTotalKillsAgainstZoomedSniper(int totalKillsAgainstZoomedSniper) {
        mTotalKillsAgainstZoomedSniper = totalKillsAgainstZoomedSniper;
    }

    public int getTotalDominations() {
        return mTotalDominations;
    }

    public void setTotalDominations(int totalDominations) {
        mTotalDominations = totalDominations;
    }

    public int getTotalRevenges() {
        return mTotalRevenges;
    }

    public void setTotalRevenges(int totalRevenges) {
        mTotalRevenges = totalRevenges;
    }

    public int getTotalMvps() {
        return mTotalMvps;
    }

    public void setTotalMvps(int totalMvps) {
        mTotalMvps = totalMvps;
    }

    public int getTotalKillsMolotov() {
        return mTotalKillsMolotov;
    }

    public void setTotalKillsMolotov(int totalKillsMolotov) {
        mTotalKillsMolotov = totalKillsMolotov;
    }

    public int getTotalKillsTaser() {
        return mTotalKillsTaser;
    }

    public void setTotalKillsTaser(int totalKillsTaser) {
        mTotalKillsTaser = totalKillsTaser;
    }

    public int getTotalShotsTaser() {
        return mTotalShotsTaser;
    }

    public void setTotalShotsTaser(int totalShotsTaser) {
        mTotalShotsTaser = totalShotsTaser;
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
