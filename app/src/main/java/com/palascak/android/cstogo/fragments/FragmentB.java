package com.palascak.android.cstogo.fragments;


import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.UpdateStatsEvent;
import com.palascak.android.cstogo.helpers.Match;
import com.palascak.android.cstogo.helpers.MatchList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {//implements ObservableScrollViewCallbacks {

    private int mNoMatches;
    private int mAllKills;
    private int mAllAssists;
    private int mAllDeaths;
    private int mAllPoints;

    private int mWins;
    private int mDraws;
    private int mLoses;

    private BigDecimal mAvgKills;
    private BigDecimal mAvgAssists;
    private BigDecimal mAvgDeaths;
    private BigDecimal mAvgPoints;

    private BigDecimal mOverallKad;
    private BigDecimal mOverallKd;

    private BigDecimal mLast3Kills;
    private BigDecimal mLast3Assists;
    private BigDecimal mLast3Deaths;
    private BigDecimal mLast3Kad;

    private ArrayList<Entry> mPieYVals = new ArrayList<>();
    private ArrayList<String> mPieXVals = new ArrayList<>();

    private ArrayList<BarEntry> mBarYVals = new ArrayList<>();
    private ArrayList<String> mBarXVals = new ArrayList<>();

    private PieChart mPie;
    private HorizontalBarChart mKadBar;
    private HorizontalBarChart mLast3Bar;

    private View linear;
    private View inflated;

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        linear = view.findViewById(R.id.match_stats_linear_layout);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        
        if (MatchList.getInstance().matchList.size() > 0) {
            updateTextViews(view);
        }
        else {
            linear.setVisibility(View.GONE);
            ViewStub stub = (ViewStub) view.findViewById(R.id.match_stats_stub_import);
            inflated = stub.inflate();
        }

        return view;
    }


    private void updateTextViews(View view) {

        linear.setVisibility(View.VISIBLE);
        if(inflated != null){
            inflated.setVisibility(View.GONE);
        }

        TypedValue typedValueAccent = new TypedValue();
        TypedValue typedValuePrimary = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.accentColor, typedValueAccent, true);
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValuePrimary, true);
        int accentColor = typedValueAccent.data;
        int primaryColor = typedValuePrimary.data;

        TextView statsAllMatches = (TextView) view.findViewById(R.id.stats_number_matches);
        TextView statsAllKills = (TextView) view.findViewById(R.id.stats_number_kills);
        TextView statsAllAssists = (TextView) view.findViewById(R.id.stats_number_assists);
        TextView statsAllDeaths = (TextView) view.findViewById(R.id.stats_number_deaths);
        TextView statsAllPoints = (TextView) view.findViewById(R.id.stats_number_points);

        TextView statsWinsDrawsLoses = (TextView) view.findViewById(R.id.match_stats_wins_draws_loses);

        PieChart statsMapPie = (PieChart) view.findViewById(R.id.stats_map_pie);
        HorizontalBarChart statsKadBar = (HorizontalBarChart) view.findViewById(R.id.stats_kad_bar);
        HorizontalBarChart statsLast3Bar = (HorizontalBarChart) view.findViewById(R.id.stats_last3_bar);

        TextView statsAvgKills = (TextView) view.findViewById(R.id.stats_number_avgKills);
        TextView statsAvgAssists = (TextView) view.findViewById(R.id.stats_number_avgAssists);
        TextView statsAvgDeaths = (TextView) view.findViewById(R.id.stats_number_avgDeaths);
        TextView statsAvgPPM = (TextView) view.findViewById(R.id.stats_number_avgPPM);
        TextView statsAvgKAD = (TextView) view.findViewById(R.id.stats_number_avgKAD);
        TextView statsAvgKD = (TextView) view.findViewById(R.id.stats_number_avgKD);

        CardView last3card = (CardView) view.findViewById(R.id.stats_last3_card);
        TextView last3cardKad = (TextView) view.findViewById(R.id.stats_last3_kad);
        TextView last3cardKadAboveAverage = (TextView) view.findViewById(R.id.stats_last3_kad_above_average);

        generateStats();

        //Pie Data Start
        setPie(statsMapPie);

        getPie().setDrawHoleEnabled(true);
        getPie().setHoleColorTransparent(true);

        PieDataSet pieDataSet = new PieDataSet(getPieYVals(), "");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value) + ""; //literally the definition of hack
            }
        });
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(getPieXVals(), pieDataSet);
        getPie().setTouchEnabled(false);
        getPie().setData(data);
        Legend legend = getPie().getLegend();
        legend.setEnabled(false);
        getPie().setDescription("");

        //Pie Data End, KAD Bar Data Start
        setKadBar(statsKadBar);

        BarDataSet barDataset = new BarDataSet(getBarYVals(), "");
        barDataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(getBarXVals(), barDataset);
        barData.setValueTextColor(primaryColor);
        getKadBar().setData(barData);
        getKadBar().setTouchEnabled(false);
        getKadBar().setDrawGridBackground(false);
        Legend barLegend = getKadBar().getLegend();
        barLegend.setEnabled(false);
        getKadBar().setDescription("");

        XAxis xl = getKadBar().getXAxis();
        xl.setEnabled(true);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setTextColor(accentColor);
        YAxis yl = getKadBar().getAxisLeft();
        yl.setEnabled(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        YAxis yr = getKadBar().getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);

        getKadBar().getLayoutParams().height = (int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getBarXVals().size()*25, getResources().getDisplayMetrics());

        //KAD Bar Data End, LAST3 Bar Start

        setLast3Bar(statsLast3Bar);

        if (MatchList.getInstance().matchList.size() >= 4 && (getLast3Kad().compareTo(getOverallKad()))== 1){
            last3card.setVisibility(View.VISIBLE);

            ArrayList<BarEntry> mLast3BarYVals = new ArrayList<>();
            ArrayList<String> mLast3BarXVals = new ArrayList<>();

            float float1 = getOverallKad().floatValue();
            float float2 = (getLast3Kad().subtract(getOverallKad())).floatValue();

            last3cardKad.setText(String.valueOf(getLast3Kad()));
            last3cardKadAboveAverage.setText("+" + String.valueOf(float2));

            mLast3BarYVals.add(new BarEntry(new float[]{float2, float1}, 0));

            BarDataSet last3DataSet = new BarDataSet(mLast3BarYVals, "");

            final int[] COLORFUL_PLUS_GREEN = {
                    Color.rgb(76, 194, 82), Color.rgb(193, 37, 82)
            };

            last3DataSet.setColors(COLORFUL_PLUS_GREEN);

            mLast3BarXVals.add("   KaD");

            BarData last3Data = new BarData(mLast3BarXVals, last3DataSet);
            last3Data.setDrawValues(false);
            getLast3Bar().setTouchEnabled(false);
            getLast3Bar().setData(last3Data);
            Legend last3Legend = getLast3Bar().getLegend();
            last3Legend.setEnabled(false);
            getLast3Bar().setDescription("");
            getLast3Bar().setDrawValueAboveBar(false);


            XAxis lastX = getLast3Bar().getXAxis();
            lastX.setEnabled(true);
            lastX.setDrawAxisLine(false);
            lastX.setDrawGridLines(false);
            YAxis lastYl = getLast3Bar().getAxisLeft();
            lastYl.setEnabled(false);
            lastYl.setDrawAxisLine(false);
            lastYl.setDrawGridLines(false);
            YAxis lastYr = getLast3Bar().getAxisRight();
            lastYr.setEnabled(false);
            lastYr.setDrawAxisLine(false);
            lastYr.setDrawGridLines(false);

            getLast3Bar().invalidate();
        } else {
            last3card.setVisibility(View.GONE);
        }

        //LAST3Bar END

        statsAllMatches.setText(Integer.toString(getNoMatches()));
        statsAllKills.setText(Integer.toString(getAllKills()));
        statsAllAssists.setText(Integer.toString(getAllAssists()));
        statsAllDeaths.setText(Integer.toString(getAllDeaths()));
        statsAllPoints.setText(Integer.toString(getAllPoints()));

        String wins = Integer.toString(getWins());
        int winsLength = wins.length();
        String draws = Integer.toString(getDraws());
        String loses = Integer.toString(getLoses());

        final SpannableStringBuilder sb = new SpannableStringBuilder(wins + ":" + draws + ":" + loses);
        // Span to set text color to some RGB value
        final ForegroundColorSpan spanAccent = new ForegroundColorSpan(accentColor);

        // Set the text color for first characters based on how many numbers Wins have
        sb.setSpan(spanAccent, 0, winsLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        statsWinsDrawsLoses.setText(sb);

        statsAvgKills.setText(getAvgKills().toString());
        statsAvgAssists.setText(getAvgAssists().toString());
        statsAvgDeaths.setText(getAvgDeaths().toString());
        statsAvgPPM.setText(getAvgPoints().toString());
        statsAvgKAD.setText(getOverallKad().toString());
        statsAvgKD.setText(getOverallKd().toString());

    }

    @SuppressWarnings("ConstantConditions")
    private void generateStats() {
        nullStats();

        int pie_chart_map_count = 0;
        int de_dust_count = 0;
        BigDecimal de_dust_kad = new BigDecimal(0);
        int de_inferno_count = 0;
        BigDecimal de_inferno_kad = new BigDecimal(0);
        int de_nuke_count = 0;
        BigDecimal de_nuke_kad = new BigDecimal(0);
        int de_cache_count = 0;
        BigDecimal de_cache_kad = new BigDecimal(0);
        int de_mirage_count = 0;
        BigDecimal de_mirage_kad = new BigDecimal(0);
        int de_cbble_count = 0;
        BigDecimal de_cbble_kad = new BigDecimal(0);
        int de_overpass_count = 0;
        BigDecimal de_overpass_kad = new BigDecimal(0);
        int de_season_count = 0;
        BigDecimal de_season_kad = new BigDecimal(0);
        int de_train_count = 0;
        BigDecimal de_train_kad = new BigDecimal(0);

        ArrayList<Match> matchList = MatchList.getInstance().matchList;
        //generate All Kills, Assists, Deaths
        for (int i = 0; i < matchList.size(); i++) {
            Match ice = matchList.get(i);

            if (matchList.size() >= 4 && i <= 2){
                setLast3Kills(getLast3Kills().add(new BigDecimal(ice.getKills())));
                setLast3Assists(getLast3Assists().add(new BigDecimal(ice.getAssists())));
                setLast3Deaths(getLast3Deaths().add(new BigDecimal(ice.getDeaths())));
            }

            setAllKills(getAllKills() + ice.getKills());
            setAllAssists(getAllAssists() + ice.getAssists());
            setAllDeaths(getAllDeaths() + ice.getDeaths());
            setAllPoints(getAllPoints() + ice.getScore());

            if (ice.getTeamRounds() > ice.getEnemyRounds()){
                setWins(getWins() + 1);
            } else if (ice.getTeamRounds() < ice.getEnemyRounds()){
                setLoses(getLoses() + 1);
            } else {
                setDraws(getDraws() + 1);
            }

            switch (ice.getMap()) {
                case "de_dust2":
                    de_dust_count++;
                    de_dust_kad = de_dust_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_inferno":
                    de_inferno_count++;
                    de_inferno_kad = de_inferno_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_nuke":
                    de_nuke_count++;
                    de_nuke_kad = de_nuke_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_cache":
                    de_cache_count++;
                    de_cache_kad = de_cache_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_mirage":
                    de_mirage_count++;
                    de_mirage_kad = de_mirage_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_cbble":
                    de_cbble_count++;
                    de_cbble_kad = de_cbble_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_overpass":
                    de_overpass_count++;
                    de_overpass_kad = de_overpass_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_season":
                    de_season_count++;
                    de_season_kad = de_season_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_train":
                    de_train_count++;
                    de_train_kad = de_train_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
            }

        }
        setNoMatches(matchList.size());

        BigDecimal numberOfMatches = new BigDecimal(matchList.size());

        if (de_dust_count > 0){
            getPieYVals().add(new Entry((float) de_dust_count, pie_chart_map_count));
            getPieXVals().add("de_dust2");
            getBarYVals().add(new BarEntry((de_dust_kad.divide(new BigDecimal(de_dust_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_dust2");
            pie_chart_map_count++;
        }
        if (de_inferno_count > 0){
            getPieYVals().add(new Entry((float) de_inferno_count, pie_chart_map_count));
            getPieXVals().add("de_inferno");
            getBarYVals().add(new BarEntry((de_inferno_kad.divide(new BigDecimal(de_inferno_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_inferno");
            pie_chart_map_count++;
        }
        if (de_nuke_count > 0){
            getPieYVals().add(new Entry((float) de_nuke_count, pie_chart_map_count));
            getPieXVals().add("de_nuke");
            getBarYVals().add(new BarEntry((de_nuke_kad.divide(new BigDecimal(de_nuke_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_nuke");
            pie_chart_map_count++;
        }
        if (de_cache_count > 0){
            getPieYVals().add(new Entry((float) de_cache_count, pie_chart_map_count));
            getPieXVals().add("de_cache");
            getBarYVals().add(new BarEntry((de_cache_kad.divide(new BigDecimal(de_cache_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_cache");
            pie_chart_map_count++;
        }
        if (de_mirage_count > 0){
            getPieYVals().add(new Entry((float) de_mirage_count, pie_chart_map_count));
            getPieXVals().add("de_mirage");
            getBarYVals().add(new BarEntry((de_mirage_kad.divide(new BigDecimal(de_mirage_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_mirage");
            pie_chart_map_count++;
        }
        if (de_cbble_count > 0){
            getPieYVals().add(new Entry((float) de_cbble_count, pie_chart_map_count));
            getPieXVals().add("de_cbble");
            getBarYVals().add(new BarEntry((de_cbble_kad.divide(new BigDecimal(de_cbble_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_cbble");
            pie_chart_map_count++;
        }
        if (de_overpass_count > 0){
            getPieYVals().add(new Entry((float) de_overpass_count, pie_chart_map_count));
            getPieXVals().add("de_overpass");
            getBarYVals().add(new BarEntry((de_overpass_kad.divide(new BigDecimal(de_overpass_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_overpass");
            pie_chart_map_count++;
        }
        if (de_season_count > 0){
            getPieYVals().add(new Entry((float) de_season_count, pie_chart_map_count));
            getPieXVals().add("de_season");
            getBarYVals().add(new BarEntry((de_season_kad.divide(new BigDecimal(de_season_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_season");
            pie_chart_map_count++;
        }
        if (de_train_count > 0){
            getPieYVals().add(new Entry((float) de_train_count, pie_chart_map_count));
            getPieXVals().add("de_train");
            getBarYVals().add(new BarEntry((de_train_kad.divide(new BigDecimal(de_train_count), 2, RoundingMode.HALF_UP)).floatValue(), pie_chart_map_count));
            getBarXVals().add("   de_train");
        }


        BigDecimal allKills = new BigDecimal(getAllKills());
        BigDecimal allAssists = new BigDecimal(getAllAssists());
        BigDecimal allDeaths = new BigDecimal(getAllDeaths());
        BigDecimal allPoints = new BigDecimal(getAllPoints());
        setAvgKills(allKills.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgAssists(allAssists.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgDeaths(allDeaths.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgPoints(allPoints.divide(numberOfMatches, 2, RoundingMode.HALF_UP));

        try{
            setOverallKad((allKills.add(allAssists)).divide(allDeaths, 2, RoundingMode.HALF_UP));
        } catch (ArithmeticException aex){
            setOverallKad((allKills.add(allAssists)));
        }

        try{
            setOverallKd(allKills.divide(allDeaths, 2, RoundingMode.HALF_UP));
        } catch (ArithmeticException aex){
            setOverallKd(allKills);
        }

        if (matchList.size() >= 4){
            setLast3Kad((getLast3Kills().add(getLast3Assists())).divide(getLast3Deaths(), 2, RoundingMode.HALF_UP));
        }

    }

    private void nullStats() {
        setAllKills(0);
        setNoMatches(0);
        setAllPoints(0);
        setAllDeaths(0);
        setAllAssists(0);
        setWins(0);
        setDraws(0);
        setLoses(0);
        getPieYVals().clear();
        getPieXVals().clear();
        getBarXVals().clear();
        getBarYVals().clear();
        setLast3Kills(new BigDecimal(0));
        setLast3Assists(new BigDecimal(0));
        setLast3Deaths(new BigDecimal(0));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (MatchList.getInstance().matchList.size() > 0) {
                getPie().invalidate();
                getLast3Bar().invalidate();
                getKadBar().animateY(3000);
            }
        }
    }

    public int getAllKills() {
        return mAllKills;
    }

    public void setAllKills(int allKills) {
        mAllKills = allKills;
    }

    public int getAllAssists() {
        return mAllAssists;
    }

    public void setAllAssists(int allAssists) {
        mAllAssists = allAssists;
    }

    public int getAllDeaths() {
        return mAllDeaths;
    }

    public void setAllDeaths(int allDeaths) {
        mAllDeaths = allDeaths;
    }

    public int getAllPoints() {
        return mAllPoints;
    }

    public void setAllPoints(int allPoints) {
        mAllPoints = allPoints;
    }

    public int getNoMatches() {
        return mNoMatches;
    }

    public void setNoMatches(int noMatches) {
        mNoMatches = noMatches;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int wins) {
        mWins = wins;
    }

    public int getDraws() {
        return mDraws;
    }

    public void setDraws(int draws) {
        mDraws = draws;
    }

    public int getLoses() {
        return mLoses;
    }

    public void setLoses(int loses) {
        mLoses = loses;
    }

    public BigDecimal getAvgKills() {
        return mAvgKills;
    }

    public void setAvgKills(BigDecimal avgKills) {
        mAvgKills = avgKills;
    }

    public BigDecimal getAvgAssists() {
        return mAvgAssists;
    }

    public void setAvgAssists(BigDecimal avgAssists) {
        mAvgAssists = avgAssists;
    }

    public BigDecimal getAvgDeaths() {
        return mAvgDeaths;
    }

    public void setAvgDeaths(BigDecimal avgDeaths) {
        mAvgDeaths = avgDeaths;
    }

    public BigDecimal getAvgPoints() {
        return mAvgPoints;
    }

    public void setAvgPoints(BigDecimal avgPoints) {
        mAvgPoints = avgPoints;
    }

    public BigDecimal getOverallKad() {
        return mOverallKad;
    }

    public void setOverallKad(BigDecimal overallKad) {
        mOverallKad = overallKad;
    }

    public BigDecimal getOverallKd() {
        return mOverallKd;
    }

    public void setOverallKd(BigDecimal overallKd) {
        mOverallKd = overallKd;
    }

    public BigDecimal getLast3Kills() {
        return mLast3Kills;
    }

    public void setLast3Kills(BigDecimal last3Kills) {
        mLast3Kills = last3Kills;
    }

    public BigDecimal getLast3Assists() {
        return mLast3Assists;
    }

    public void setLast3Assists(BigDecimal last3Assists) {
        mLast3Assists = last3Assists;
    }

    public BigDecimal getLast3Deaths() {
        return mLast3Deaths;
    }

    public void setLast3Deaths(BigDecimal last3Deaths) {
        mLast3Deaths = last3Deaths;
    }

    public BigDecimal getLast3Kad() {
        return mLast3Kad;
    }

    public void setLast3Kad(BigDecimal last3Kad) {
        mLast3Kad = last3Kad;
    }

    public ArrayList<Entry> getPieYVals() {
        return mPieYVals;
    }

    public ArrayList<String> getPieXVals() {
        return mPieXVals;
    }

    public ArrayList<BarEntry> getBarYVals() {
        return mBarYVals;
    }

    public ArrayList<String> getBarXVals() {
        return mBarXVals;
    }

    public PieChart getPie() {
        return mPie;
    }

    public void setPie(PieChart pie) {
        mPie = pie;
    }

    public HorizontalBarChart getKadBar() {
        return mKadBar;
    }

    public void setKadBar(HorizontalBarChart kadBar) {
        mKadBar = kadBar;
    }

    public HorizontalBarChart getLast3Bar() {
        return mLast3Bar;
    }

    public void setLast3Bar(HorizontalBarChart last3Bar) {
        mLast3Bar = last3Bar;
    }

    @SuppressWarnings("unused")
    public void onEvent(UpdateStatsEvent event){

        if (MatchList.getInstance().matchList.size() > 0) {
            updateTextViews(getView());
        }
        else {
            linear.setVisibility(View.GONE);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
