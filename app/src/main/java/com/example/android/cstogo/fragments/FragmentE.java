package com.example.android.cstogo.fragments;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.adapters.MySteamWebStatsAdapter;
import com.example.android.cstogo.helpers.WebGun;
import com.example.android.cstogo.helpers.WebMap;
import com.rey.material.widget.ProgressView;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentE extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private List<String> steamWebHeader = new ArrayList<>();
    private List<String> steamWebOverall = new ArrayList<>();
    private List<String> steamWebWebMap = new ArrayList<>();
    private List<String> steamWebWebGun = new ArrayList<>();
    private List<String> steamWebOther = new ArrayList<>();

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

    private String myUrlAvatar;
    private String myUrlStats;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressView mProgressView;
    private int disableRefresher = 0;

    private String apiKey;
    private SharedPreferences spref;
    private String sprefSteamId;
    private int sprefSteam64Success;

    private View importPanel;

    public FragmentE() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiKey = getResources().getString(R.string.api_key);

        spref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sprefSteamId = spref.getString("prefs_steam_name", "");
        String sprefSteam64Id = spref.getString("steam_id_64", "");
        sprefSteam64Success = spref.getInt("steam_id_64_success", 0);

        if (sprefSteam64Success == 1) {
            buildUrls(sprefSteam64Id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e, container, false);
        mProgressView = (ProgressView) view.findViewById(R.id.steam_web_stats_progress_line);
        mProgressView.setVisibility(View.GONE);
        setHasOptionsMenu(true);

        TextView importText;
        ImageView importImage;
        assert sprefSteamId != null;
        if (sprefSteamId.equals("")) {
            importPanel = ((ViewStub) view.findViewById(R.id.steam_web_stats_stub_import)).inflate();
            importText = (TextView) importPanel.findViewById(R.id.steam_web_stats_no_id_description);
            importImage = (ImageView) importPanel.findViewById(R.id.steam_web_stats_no_id_image);
            importText.setText(getText(R.string.steam_web_stats_strings_no_id));
            Picasso.with(getActivity()).load(R.drawable.steam_web_settings).into(importImage);
            importImage.setVisibility(View.VISIBLE);
        } else {
            switch (sprefSteam64Success){
                case 0:
                    importPanel = ((ViewStub) view.findViewById(R.id.steam_web_stats_stub_import)).inflate();
                    importText = (TextView) importPanel.findViewById(R.id.steam_web_stats_no_id_import);
                    importText.setText(getText(R.string.steam_web_stats_strings_internal_problem));
                    break;
                case 1:
                    Request requestAvatar = new Request.Builder()
                            .cacheControl(new CacheControl.Builder()
                                    .maxStale(1, TimeUnit.DAYS)
                                    .build())
                            .url(myUrlAvatar)
                            .build();

                    new GetSteamUserData().execute(requestAvatar);

                    Request requestStats = new Request.Builder()
                            .cacheControl(new CacheControl.Builder()
                                    .maxStale(1, TimeUnit.DAYS)
                                    .build())
                            .url(myUrlStats)
                            .build();

                    new GetSteamGameStats().execute(requestStats);
                    break;
                case 42:
                    importPanel = ((ViewStub) view.findViewById(R.id.steam_web_stats_stub_import)).inflate();
                    importText = (TextView) importPanel.findViewById(R.id.steam_web_stats_no_id_import);
                    importText.setText(getText(R.string.steam_web_stats_strings_id_typo));
                    break;
                case 99:
                    importPanel = ((ViewStub) view.findViewById(R.id.steam_web_stats_stub_import)).inflate();
                    importText = (TextView) importPanel.findViewById(R.id.steam_web_stats_no_id_import);
                    importText.setText(getText(R.string.steam_web_stats_strings_no_network));
                    break;
                default:
                    importPanel = ((ViewStub) view.findViewById(R.id.steam_web_stats_stub_import)).inflate();
                    importText = (TextView) importPanel.findViewById(R.id.steam_web_stats_no_id_import);
                    importText.setText(getText(R.string.steam_web_stats_strings_wtf));
                    break;
            }
        }

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.steam_web_stats_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (sprefSteam64Success == 1) {
                    setDisableRefresher(0);
                    Request requestAvatar = new Request.Builder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .url(myUrlAvatar)
                            .build();

                    new GetSteamUserData().execute(requestAvatar);

                    Request requestStats = new Request.Builder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .url(myUrlStats)
                            .build();

                    new GetSteamGameStats().execute(requestStats);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Steam ID is not correctly set up. There isn't really anything to refresh", Toast.LENGTH_SHORT).show();
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.steam_web_stats_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MySteamWebStatsAdapter(getActivity(),
                steamWebHeader,
                steamWebOverall,
                steamWebWebMap,
                steamWebWebGun,
                steamWebOther);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void buildUrls(String sprefSteam64Id){
        Uri.Builder builderAvatar = new Uri.Builder();
        builderAvatar.scheme("http")
                .authority("api.steampowered.com")
                .appendPath("ISteamUser")
                .appendPath("GetPlayerSummaries")
                .appendPath("v0002")
                .appendQueryParameter("key", apiKey)
                .appendQueryParameter("steamids", sprefSteam64Id);
        myUrlAvatar = builderAvatar.build().toString();

        Uri.Builder builderStats = new Uri.Builder();
        builderStats.scheme("http")
                .authority("api.steampowered.com")
                .appendPath("ISteamUserStats")
                .appendPath("GetUserStatsForGame")
                .appendPath("v0002")
                .appendQueryParameter("appid", "730")
                .appendQueryParameter("key", apiKey)
                .appendQueryParameter("steamid", sprefSteam64Id);
        myUrlStats = builderStats.build().toString();
    }

    private class GetSteamUserData extends AsyncTask<Request, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Request... req) {
            Response response;

            int cacheSize = 5 * 1024 * 1024; // 5 MiB
            Cache cache = new Cache(new File(MyApplication.getAppContext().getCacheDir(), "steam_user_data_cache"), cacheSize);

            OkHttpClient client = new OkHttpClient();
            client.setCache(cache);

            try {
                response = client.newCall(req[0]).execute();
                String jsonData = response.body().string();
                response.body().close();
                JSONObject dataJsonObj = new JSONObject(jsonData).getJSONObject("response").getJSONArray("players").getJSONObject(0);
                String personaname = dataJsonObj.getString("personaname");
                steamWebHeader.clear();
                steamWebHeader.add(personaname);
                String avatarfull = dataJsonObj.getString("avatarfull");
                steamWebHeader.add(avatarfull);
                String personastate = dataJsonObj.getString("personastate");
                steamWebHeader.add(personastate);
                String dateStr = response.header("Date");
                SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                Date date = df.parse(dateStr);
                df.setTimeZone(TimeZone.getDefault());
                df.applyPattern("dd.MM.yy - HH:mm");
                String formattedDate = df.format(date);
                steamWebHeader.add(formattedDate);
                return "okay";
            } catch (IOException | JSONException e) {
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            setDisableRefresher(getDisableRefresher() + 1);
            if (getDisableRefresher() == 2) {
                swipeRefreshLayout.setRefreshing(false);
            }
            mProgressView.setVisibility(View.GONE);
            if (result != null) {
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MyApplication.getAppContext(), "Error connecting to steam API. Please try again later.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetSteamGameStats extends AsyncTask<Request, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Request... req) {
            Response response;

            int cacheSize = 5 * 1024 * 1024; // 5 MiB
            Cache cache = new Cache(new File(MyApplication.getAppContext().getCacheDir(), "steam_user_stats_cache"), cacheSize);

            OkHttpClient client = new OkHttpClient();
            client.setCache(cache);

            try {
                response = client.newCall(req[0]).execute();
                String jsonData = response.body().string();
                response.body().close();
                JSONArray dataJsonArr = new JSONObject(jsonData).getJSONObject("playerstats").getJSONArray("stats");
                parseJsonStats(dataJsonArr);
                createTextViewsHeader();
                createTextViewsBestMaps();
                createTextViewsBestWeapons();
                createTextViewsOtherStats();
                return "okay";
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            setDisableRefresher(getDisableRefresher() + 1);
            if (getDisableRefresher() == 2) {
                swipeRefreshLayout.setRefreshing(false);
            }
            mProgressView.setVisibility(View.GONE);
            if (result != null) {
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MyApplication.getAppContext(), "Error connecting to steam API. Please try again later.", Toast.LENGTH_LONG).show();
            }
        }
    }
    //_______________________________________________________________

    private void parseJsonStats(JSONArray dataJsonArr) {

        // loop through all stats
        for (int i = 0; i < dataJsonArr.length(); i++) {

            try {
                JSONObject c = dataJsonArr.getJSONObject(i);

                // Storing each json item in variable
                String name = c.getString("name");
                int intValue = c.getInt("value");

                switch (name) {
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

                if (name.contains("_map_")) {
                    String temp = name.replace("total_", "").replace("wins_", "").replace("rounds_", "").replace("map_", "");
                    switch (temp) {
                        case "de_aztec":
                            if (name.contains("wins_")) {
                                aztec.setWins(intValue);
                            } else {
                                aztec.setRounds(intValue);
                            }
                            break;
                        case "de_cbble":
                            if (name.contains("wins_")) {
                                cbble.setWins(intValue);
                            } else {
                                cbble.setRounds(intValue);
                            }
                            break;
                        case "de_dust":
                            if (name.contains("wins_")) {
                                dust.setWins(intValue);
                            } else {
                                dust.setRounds(intValue);
                            }
                            break;
                        case "de_dust2":
                            if (name.contains("wins_")) {
                                dust2.setWins(intValue);
                            } else {
                                dust2.setRounds(intValue);
                            }
                            break;
                        case "de_nuke":
                            if (name.contains("wins_")) {
                                nuke.setWins(intValue);
                            } else {
                                nuke.setRounds(intValue);
                            }
                            break;
                        case "de_inferno":
                            if (name.contains("wins_")) {
                                inferno.setWins(intValue);
                            } else {
                                inferno.setRounds(intValue);
                            }
                            break;
                        case "de_train":
                            if (name.contains("wins_")) {
                                train.setWins(intValue);
                            } else {
                                train.setRounds(intValue);
                            }
                            break;
                        case "de_lake":
                            if (name.contains("wins_")) {
                                lake.setWins(intValue);
                            } else {
                                lake.setRounds(intValue);
                            }
                            break;
                        case "de_safehouse":
                            if (name.contains("wins_")) {
                                safehouse.setWins(intValue);
                            } else {
                                safehouse.setRounds(intValue);
                            }
                            break;
                        case "de_sugarcane":
                            if (name.contains("wins_")) {
                                sugarcane.setWins(intValue);
                            } else {
                                sugarcane.setRounds(intValue);
                            }
                            break;
                        case "de_stmarc":
                            if (name.contains("wins_")) {
                                stmarc.setWins(intValue);
                            } else {
                                stmarc.setRounds(intValue);
                            }
                            break;
                        case "de_bank":
                            if (name.contains("wins_")) {
                                bank.setWins(intValue);
                            } else {
                                bank.setRounds(intValue);
                            }
                            break;
                        case "de_shorttrain":
                            if (name.contains("wins_")) {
                                shorttrain.setWins(intValue);
                            } else {
                                shorttrain.setRounds(intValue);
                            }
                            break;
                        case "de_vertigo":
                            if (name.contains("wins_")) {
                                vertigo.setWins(intValue);
                            } else {
                                vertigo.setRounds(intValue);
                            }
                            break;
                        case "cs_assault":
                            if (name.contains("wins_")) {
                                assault.setWins(intValue);
                            } else {
                                assault.setRounds(intValue);
                            }
                            break;
                        case "cs_italy":
                            if (name.contains("wins_")) {
                                italy.setWins(intValue);
                            } else {
                                italy.setRounds(intValue);
                            }
                            break;
                        case "cs_office":
                            if (name.contains("wins_")) {
                                office.setWins(intValue);
                            } else {
                                office.setRounds(intValue);
                            }
                            break;
                        case "cs_militia":
                            if (name.contains("wins_")) {
                                militia.setWins(intValue);
                            } else {
                                militia.setRounds(intValue);
                            }
                            break;
                        case "ar_monastery":
                            if (name.contains("wins_")) {
                                monastery.setWins(intValue);
                            } else {
                                monastery.setRounds(intValue);
                            }
                            break;
                        case "ar_shoots":
                            if (name.contains("wins_")) {
                                shoots.setWins(intValue);
                            } else {
                                shoots.setRounds(intValue);
                            }
                            break;
                        case "ar_baggage":
                            if (name.contains("wins_")) {
                                baggage.setWins(intValue);
                            } else {
                                baggage.setRounds(intValue);
                            }
                            break;
                        default:
                            break;
                    }
                }

                if (name.contains("total_")) {
                    String temp = name.replace("total_", "").replace("kills_", "").replace("shots_", "").replace("hits_", "");

                    switch (temp) {
                        case "ak47":
                            if (name.contains("kills_")) {
                                ak47.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                ak47.setShots(intValue);
                            } else {
                                ak47.setHits(intValue);
                            }
                            break;
                        case "aug":
                            if (name.contains("kills_")) {
                                aug.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                aug.setShots(intValue);
                            } else {
                                aug.setHits(intValue);
                            }
                            break;
                        case "awp":
                            if (name.contains("kills_")) {
                                awp.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                awp.setShots(intValue);
                            } else {
                                awp.setHits(intValue);
                            }
                            break;
                        case "bizon":
                            if (name.contains("kills_")) {
                                bizon.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                bizon.setShots(intValue);
                            } else {
                                bizon.setHits(intValue);
                            }
                            break;
                        case "deagle":
                            if (name.contains("kills_")) {
                                deagle.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                deagle.setShots(intValue);
                            } else {
                                deagle.setHits(intValue);
                            }
                            break;
                        case "elite":
                            if (name.contains("kills_")) {
                                elite.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                elite.setShots(intValue);
                            } else {
                                elite.setHits(intValue);
                            }
                            break;
                        case "famas":
                            if (name.contains("kills_")) {
                                famas.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                famas.setShots(intValue);
                            } else {
                                famas.setHits(intValue);
                            }
                            break;
                        case "fiveseven":
                            if (name.contains("kills_")) {
                                fiveseven.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                fiveseven.setShots(intValue);
                            } else {
                                fiveseven.setHits(intValue);
                            }
                            break;
                        case "g3sg1":
                            if (name.contains("kills_")) {
                                g3sg1.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                g3sg1.setShots(intValue);
                            } else {
                                g3sg1.setHits(intValue);
                            }
                            break;
                        case "galilar":
                            if (name.contains("kills_")) {
                                galilar.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                galilar.setShots(intValue);
                            } else {
                                galilar.setHits(intValue);
                            }
                            break;
                        case "glock":
                            if (name.contains("kills_")) {
                                glock.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                glock.setShots(intValue);
                            } else {
                                glock.setHits(intValue);
                            }
                            break;
                        case "hkp2000":
                            if (name.contains("kills_")) {
                                hkp2000.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                hkp2000.setShots(intValue);
                            } else {
                                hkp2000.setHits(intValue);
                            }
                            break;
                        case "m249":
                            if (name.contains("kills_")) {
                                m249.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                m249.setShots(intValue);
                            } else {
                                m249.setHits(intValue);
                            }
                            break;
                        case "m4a1":
                            if (name.contains("kills_")) {
                                m4a1.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                m4a1.setShots(intValue);
                            } else {
                                m4a1.setHits(intValue);
                            }
                            break;
                        case "mac10":
                            if (name.contains("kills_")) {
                                mac10.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                mac10.setShots(intValue);
                            } else {
                                mac10.setHits(intValue);
                            }
                            break;
                        case "mag7":
                            if (name.contains("kills_")) {
                                mag7.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                mag7.setShots(intValue);
                            } else {
                                mag7.setHits(intValue);
                            }
                            break;
                        case "mp7":
                            if (name.contains("kills_")) {
                                mp7.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                mp7.setShots(intValue);
                            } else {
                                mp7.setHits(intValue);
                            }
                            break;
                        case "mp9":
                            if (name.contains("kills_")) {
                                mp9.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                mp9.setShots(intValue);
                            } else {
                                mp9.setHits(intValue);
                            }
                            break;
                        case "negev":
                            if (name.contains("kills_")) {
                                negev.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                negev.setShots(intValue);
                            } else {
                                negev.setHits(intValue);
                            }
                            break;
                        case "nova":
                            if (name.contains("kills_")) {
                                nova.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                nova.setShots(intValue);
                            } else {
                                nova.setHits(intValue);
                            }
                            break;
                        case "p250":
                            if (name.contains("kills_")) {
                                p250.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                p250.setShots(intValue);
                            } else {
                                p250.setHits(intValue);
                            }
                            break;
                        case "p90":
                            if (name.contains("kills_")) {
                                p90.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                p90.setShots(intValue);
                            } else {
                                p90.setHits(intValue);
                            }
                            break;
                        case "sawedoff":
                            if (name.contains("kills_")) {
                                sawedoff.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                sawedoff.setShots(intValue);
                            } else {
                                sawedoff.setHits(intValue);
                            }
                            break;
                        case "scar20":
                            if (name.contains("kills_")) {
                                scar20.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                scar20.setShots(intValue);
                            } else {
                                scar20.setHits(intValue);
                            }
                            break;
                        case "sg556":
                            if (name.contains("kills_")) {
                                sg556.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                sg556.setShots(intValue);
                            } else {
                                sg556.setHits(intValue);
                            }
                            break;
                        case "ssg08":
                            if (name.contains("kills_")) {
                                ssg08.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                ssg08.setShots(intValue);
                            } else {
                                ssg08.setHits(intValue);
                            }
                            break;
                        case "tec9":
                            if (name.contains("kills_")) {
                                tec9.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                tec9.setShots(intValue);
                            } else {
                                tec9.setHits(intValue);
                            }
                            break;
                        case "ump45":
                            if (name.contains("kills_")) {
                                ump45.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                ump45.setShots(intValue);
                            } else {
                                ump45.setHits(intValue);
                            }
                            break;
                        case "xm1014":
                            if (name.contains("kills_")) {
                                xm1014.setKills(intValue);
                            } else if (name.contains("shots_")) {
                                xm1014.setShots(intValue);
                            } else {
                                xm1014.setHits(intValue);
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (JSONException e) {
                //this catch block should be caught earlier and never happen
                //just in case, throw Toast out there
                Toast.makeText(MyApplication.getAppContext(), "Response Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createTextViewsHeader() {
        //HEADER START
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

        steamWebOverall.clear();
        steamWebOverall.add(String.valueOf(getTotalKd()));
        steamWebOverall.add(String.valueOf(getTotalKills()));
        steamWebOverall.add(win_perc + "%");
        steamWebOverall.add(hs_perc + "%");
        steamWebOverall.add(accuracy + "%");
    }

    private void createTextViewsBestMaps() {

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

            if (tempWinPerc > highestWinPerc) {
                highestWinPerc = tempWinPerc;
                highestWinPercName = ci.getWebMapName();
            }
            if (tempRounds > highestRounds) {
                highestRounds = tempRounds;
                highestRoundsName = ci.getWebMapName();
            }
            if (tempWins > highestWins) {
                highestWins = tempWins;
                highestWinsName = ci.getWebMapName();
            }
        }

        steamWebWebMap.clear();
        steamWebWebMap.add(highestWinPercName);
        steamWebWebMap.add(highestWinPerc + " %");
        steamWebWebMap.add(highestWinsName);
        steamWebWebMap.add(String.valueOf(highestWins));
        steamWebWebMap.add(highestRoundsName);
        steamWebWebMap.add(String.valueOf(highestRounds));

    }

    @SuppressWarnings("ConstantConditions")
    private void createTextViewsBestWeapons() {

        getWebGunList().clear();
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

            if (tempShots > highestShots) {
                highestShots = tempShots;
                highestShotsName = ci.getGunName();
            }
            if (tempHits > highestHits) {
                highestHits = tempHits;
                highestHitsName = ci.getGunName();
            }
            if (tempKills > highestKills) {
                highestKills = tempKills;
                highestKillsName = ci.getGunName();
            }
            if (tempAcc > highestAcc) {
                highestAcc = tempAcc;
                highestAccName = ci.getGunName();
            }
        }

        steamWebWebGun.clear();
        steamWebWebGun.add(highestShotsName);
        steamWebWebGun.add(String.valueOf(highestShots));

        steamWebWebGun.add(highestHitsName);
        steamWebWebGun.add(String.valueOf(highestHits));

        steamWebWebGun.add(highestKillsName);
        steamWebWebGun.add(String.valueOf(highestKills));

        steamWebWebGun.add(highestAccName);
        steamWebWebGun.add(highestAcc + " %");
    }

    private void createTextViewsOtherStats() {

        steamWebOther.clear();
        steamWebOther.add(String.valueOf(getTotalKills()));
        steamWebOther.add(String.valueOf(getTotalKillsHeadshot()));
        steamWebOther.add(String.valueOf(getTotalDeaths()));
        steamWebOther.add(String.valueOf(getTotalWins()));
        steamWebOther.add(String.valueOf(getTotalRounds()));
        steamWebOther.add(String.valueOf(getTotalPlanted()));
        steamWebOther.add(String.valueOf(getTotalDefused()));
        steamWebOther.add(String.valueOf(getTotalDamageDone()));
        steamWebOther.add("$" + String.valueOf(getTotalMoneyEarned()));
        steamWebOther.add(String.valueOf(getTotalRescuedHostages()));
        steamWebOther.add(String.valueOf(getTotalMvps()));
        steamWebOther.add(String.valueOf(getTotalKillsEnemyWeapon()));

        steamWebOther.add(String.valueOf(getTotalShotsFired()));
        steamWebOther.add(String.valueOf(getTotalShotsHit()));
        steamWebOther.add(String.valueOf(getTotalShotsTaser()));
        steamWebOther.add(String.valueOf(getTotalKillsTaser()));
        steamWebOther.add(String.valueOf(getTotalKillsMolotov()));
        steamWebOther.add(String.valueOf(getTotalKillsHeGrenade()));
        steamWebOther.add(String.valueOf(getTotalKillsKnife()));
        steamWebOther.add(String.valueOf(getTotalKillsEnemyBlinded()));
        steamWebOther.add(String.valueOf(getTotalKillsAgainstZoomedSniper()));
        steamWebOther.add(String.valueOf(getTotalDominations()));
        steamWebOther.add(String.valueOf(getTotalRevenges()));
        steamWebOther.add(String.valueOf(getTotalWeaponsDonated()));
        steamWebOther.add(String.valueOf(getTotalBrokenWindows()));
    }

    //_______________________________________________________________

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_e, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.fragment_e_update) {
            if (sprefSteam64Success == 1) {
                Request request = new Request.Builder()
                        .cacheControl(new CacheControl.Builder()
                                .maxStale(1, TimeUnit.DAYS)
                                .build())
                        .url(myUrlAvatar)
                        .build();

                new GetSteamUserData().execute(request);
            } else {
                Toast.makeText(getActivity(), "ID must be filled in settings to refresh stats", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.fragment_e_web) {
            if (sprefSteam64Success == 1) {
                Request request = new Request.Builder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .url(myUrlAvatar)
                        .build();

                new GetSteamUserData().execute(request);
            } else {
                Toast.makeText(getActivity(), "ID must be filled in settings to refresh stats", Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.fragment_e_id) {
            String reCheckShared = spref.getString("prefs_steam_name", "");
            assert reCheckShared != null;
            if (reCheckShared.equals("")) {
                Toast.makeText(getActivity(), "ID must be filled in settings in order to refresh it", Toast.LENGTH_SHORT).show();
            } else {
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

        return super.onOptionsItemSelected(item);
    }

    private class BackgroundWebRunner extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... url) {
            Response response;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
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

                        buildUrls(jsonSteamId);

                        Request requestAvatar = new Request.Builder()
                                .cacheControl(CacheControl.FORCE_NETWORK)
                                .url(myUrlAvatar)
                                .build();

                        new GetSteamUserData().execute(requestAvatar);

                        Request requestStats = new Request.Builder()
                                .cacheControl(CacheControl.FORCE_NETWORK)
                                .url(myUrlStats)
                                .build();

                        new GetSteamGameStats().execute(requestStats);
                        if (importPanel != null) {
                            importPanel.setVisibility(View.GONE);
                        }
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

    //_______________________________________________________________

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

    public int getDisableRefresher() {
        return disableRefresher;
    }

    public void setDisableRefresher(int disableRefresher) {
        this.disableRefresher = disableRefresher;
    }
}
