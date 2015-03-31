package com.example.android.cstogo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

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

    private BigDecimal mAvgKills;
    private BigDecimal mAvgAssists;
    private BigDecimal mAvgDeaths;
    private BigDecimal mAvgPoints;

    private BigDecimal mOverallKad;
    private BigDecimal mOverallKd;

    private BigDecimal mDustAvgKad = new BigDecimal(0);
    private BigDecimal mNukeAvgKad = new BigDecimal(0);

    ArrayList<Entry> mPieYVals = new ArrayList<>();
    ArrayList<String> mPieXVals = new ArrayList<>();

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        EventBus.getDefault().register(this);

        if (MatchList.getInstance().matchList.size() > 0) {
            updateTextViews(view);
        }
        else {
            TextView statsAllMatches = (TextView) view.findViewById(R.id.stats_number_matches);
            statsAllMatches.setText("Please add some matches to see stats");
        }

        return view;
    }


    private void updateTextViews(View view) {

        TextView statsAllMatches = (TextView) view.findViewById(R.id.stats_number_matches);
        TextView statsAllKills = (TextView) view.findViewById(R.id.stats_number_kills);
        TextView statsAllAssists = (TextView) view.findViewById(R.id.stats_number_assists);
        TextView statsAllDeaths = (TextView) view.findViewById(R.id.stats_number_deaths);
        TextView statsAllPoints = (TextView) view.findViewById(R.id.stats_number_points);

        PieChart statsMapPie = (PieChart) view.findViewById(R.id.stats_map_pie);

        TextView statsAvgKills = (TextView) view.findViewById(R.id.stats_number_avgKills);
        TextView statsAvgAssists = (TextView) view.findViewById(R.id.stats_number_avgAssists);
        TextView statsAvgDeaths = (TextView) view.findViewById(R.id.stats_number_avgDeaths);
        TextView statsAvgPPM = (TextView) view.findViewById(R.id.stats_number_avgPPM);
        TextView statsAvgKAD = (TextView) view.findViewById(R.id.stats_number_avgKAD);
        TextView statsDustKaD = (TextView) view.findViewById(R.id.stats_number_dust_KAD);
        TextView statsNukeKaD = (TextView) view.findViewById(R.id.stats_number_nuke_KAD);

        generateStats();

        //Pie Data Start

        statsMapPie.setDrawHoleEnabled(true);
        statsMapPie.setHoleColorTransparent(true);

        PieDataSet dataSet = new PieDataSet(getPieYVals(), "");
        dataSet.setSliceSpace(3f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value)+"";
            }
        });
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(getPieXVals(), dataSet);
        statsMapPie.setTouchEnabled(false);
        statsMapPie.setData(data);
        Legend legend = statsMapPie.getLegend();
        legend.setEnabled(false);
        statsMapPie.setDescription("");
        statsMapPie.invalidate();

        //Pie Data End

        statsAllMatches.setText(Integer.toString(getNoMatches()));
        statsAllKills.setText(Integer.toString(getAllKills()));
        statsAllAssists.setText(Integer.toString(getAllAssists()));
        statsAllDeaths.setText(Integer.toString(getAllDeaths()));
        statsAllPoints.setText(Integer.toString(getAllPoints()));
        statsAvgKills.setText(getAvgKills().toString());
        statsAvgAssists.setText(getAvgAssists().toString());
        statsAvgDeaths.setText(getAvgDeaths().toString());
        statsAvgPPM.setText(getAvgPoints().toString());
        statsAvgKAD.setText(getOverallKad().toString());
        statsDustKaD.setText(getDustAvgKad().toString());
        statsNukeKaD.setText(getNukeAvgKad().toString());

    }

    private void generateStats() {
        nullStats();

        int pie_chart_map_count = 0;
        int de_dust_count = 0;
        BigDecimal de_dust_kad = new BigDecimal(0);
        int de_inferno_count = 0;
        int de_nuke_count = 0;
        BigDecimal de_nuke_kad = new BigDecimal(0);
        int de_cache_count = 0;
        int de_mirage_count = 0;
        int de_cbble_count = 0;
        int de_overpass_count = 0;
        int de_season_count = 0;

        ArrayList<Match> matchList = MatchList.getInstance().matchList;
        //generate All Kills, Assists, Deaths
        for (int i = 0; i < matchList.size(); i++) {
            Match ice = matchList.get(i);
            setAllKills(getAllKills() + ice.getKills());
            setAllAssists(getAllAssists() + ice.getAssists());
            setAllDeaths(getAllDeaths() + ice.getDeaths());
            setAllPoints(getAllPoints() + ice.getScore());

            switch (ice.getMap()) {
                case "de_dust2":
                    de_dust_count++;
                    de_dust_kad = de_dust_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_inferno":
                    de_inferno_count++;
                    break;
                case "de_nuke":
                    de_nuke_count++;
                    de_nuke_kad = de_nuke_kad.add(new BigDecimal(String.valueOf(ice.getKad())));
                    break;
                case "de_cache":
                    de_cache_count++;
                    break;
                case "de_mirage":
                    de_mirage_count++;
                    break;
                case "de_cbble":
                    de_cbble_count++;
                    break;
                case "de_overpass":
                    de_overpass_count++;
                    break;
                case "de_season":
                    de_season_count++;
                    break;
            }

        }
        setNoMatches(matchList.size());

        BigDecimal numberOfMatches = new BigDecimal(matchList.size());

        if (de_dust_count > 0){
            getPieYVals().add(new Entry((float) de_dust_count, pie_chart_map_count));
            getPieXVals().add("de_dust2");
            pie_chart_map_count++;
            setDustAvgKad(de_dust_kad.divide(new BigDecimal(de_dust_count), 2, RoundingMode.HALF_UP));
        }
        if (de_inferno_count > 0){
            getPieYVals().add(new Entry((float) de_inferno_count, pie_chart_map_count));
            getPieXVals().add("de_inferno");
            pie_chart_map_count++;
        }
        if (de_nuke_count > 0){
            getPieYVals().add(new Entry((float) de_nuke_count, pie_chart_map_count));
            getPieXVals().add("de_nuke");
            pie_chart_map_count++;
            setNukeAvgKad(de_nuke_kad.divide(new BigDecimal(de_nuke_count), 2, RoundingMode.HALF_UP));
        }
        if (de_cache_count > 0){
            getPieYVals().add(new Entry((float) de_cache_count, pie_chart_map_count));
            getPieXVals().add("de_cache");
            pie_chart_map_count++;
        }
        if (de_mirage_count > 0){
            getPieYVals().add(new Entry((float) de_mirage_count, pie_chart_map_count));
            getPieXVals().add("de_mirage");
            pie_chart_map_count++;
        }
        if (de_cbble_count > 0){
            getPieYVals().add(new Entry((float) de_cbble_count, pie_chart_map_count));
            getPieXVals().add("de_cbble");
            pie_chart_map_count++;
        }
        if (de_overpass_count > 0){
            getPieYVals().add(new Entry((float) de_overpass_count, pie_chart_map_count));
            getPieXVals().add("de_overpass");
            pie_chart_map_count++;
        }
        if (de_season_count > 0){
            getPieYVals().add(new Entry((float) de_season_count, pie_chart_map_count));
            getPieXVals().add("de_season");
        }


        BigDecimal allKills = new BigDecimal(getAllKills());
        BigDecimal allAssists = new BigDecimal(getAllAssists());
        BigDecimal allDeaths = new BigDecimal(getAllDeaths());
        BigDecimal allPoints = new BigDecimal(getAllPoints());
        setAvgKills(allKills.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgAssists(allAssists.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgDeaths(allDeaths.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setAvgPoints(allPoints.divide(numberOfMatches, 2, RoundingMode.HALF_UP));
        setOverallKad((allKills.add(allAssists)).divide(allDeaths, 2, RoundingMode.HALF_UP));
        setOverallKd(allKills.divide(allDeaths, 2, RoundingMode.HALF_UP));
    }

    private void nullStats() {
        setAllKills(0);
        setNoMatches(0);
        setAllPoints(0);
        setAllDeaths(0);
        setAllAssists(0);
        getPieYVals().clear();
        getPieXVals().clear();
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

    public BigDecimal getDustAvgKad() {
        return mDustAvgKad;
    }

    public void setDustAvgKad(BigDecimal dustAvgKad) {
        mDustAvgKad = dustAvgKad;
    }

    public BigDecimal getNukeAvgKad() {
        return mNukeAvgKad;
    }

    public void setNukeAvgKad(BigDecimal nukeAvgKad) {
        mNukeAvgKad = nukeAvgKad;
    }

    public ArrayList<Entry> getPieYVals() {
        return mPieYVals;
    }

    public ArrayList<String> getPieXVals() {
        return mPieXVals;
    }

    public void onEvent(UpdateStatsEvent event){
        updateTextViews(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
