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

        TextView statsAllMatches = (TextView) view.findViewById(R.id.stats_matches_number);
        TextView statsAllKills = (TextView) view.findViewById(R.id.stats_kills_number);
        TextView statsAllAssists = (TextView) view.findViewById(R.id.stats_assists_number);
        TextView statsAllDeaths = (TextView) view.findViewById(R.id.stats_deaths_number);
        TextView statsAllPoints = (TextView) view.findViewById(R.id.stats_points_number);
        PieChart statsMapPie = (PieChart) view.findViewById(R.id.stats_map_pie);

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

    public void onEvent(UpdateStatsEvent event){
        updateTextViews(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
