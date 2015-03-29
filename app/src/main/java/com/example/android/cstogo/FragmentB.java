package com.example.android.cstogo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

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

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        EventBus.getDefault().register(this);

        updateTextViews(view);

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

        generateStats();

        statsMapPie.setDrawHoleEnabled(true);
        ArrayList<Entry> yVals1 = new ArrayList<>();

        yVals1.add(new Entry((float) 20.0, 0));
        yVals1.add(new Entry((float) 30.0, 1));

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("Ruben");
        xVals.add("Claudio");

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(xVals, dataSet);
        statsMapPie.setData(data);

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

    }

    private void generateStats() {
        nullStats();

        ArrayList<Match> matchList = MatchList.getInstance().matchList;
        //generate All Kills, Assists, Deaths
        for (int i = 0; i < matchList.size(); i++) {
            Match ice = matchList.get(i);
            setAllKills(getAllKills() + ice.getKills());
            setAllAssists(getAllAssists() + ice.getAssists());
            setAllDeaths(getAllDeaths() + ice.getDeaths());
            setAllPoints(getAllPoints() + ice.getScore());
        }
        setNoMatches(matchList.size());

        BigDecimal numberOfMatches = new BigDecimal(matchList.size());

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

    public void onEvent(UpdateStatsEvent event){
        updateTextViews(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
