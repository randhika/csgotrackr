/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.fragments;


import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.palascak.android.cstogo.MyApplication;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.adapters.MyGosuMatchesAdapter;
import com.palascak.android.cstogo.helpers.GosuCurrent;
import com.palascak.android.cstogo.helpers.GosuPlayed;
import com.palascak.android.cstogo.helpers.GosuUpcoming;
import com.rey.material.widget.ProgressView;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentF extends Fragment {

    private MyGosuMatchesAdapter mAdapter;

    private Request requestMatches;
    private Request requestMatchesForceNetwork;

    private SwipeRefreshLayout gosuSwipeRefresh;
    private ProgressView gosuProgressView;

    private ArrayList<GosuCurrent> gosuCurrentList = new ArrayList<>();
    private ArrayList<GosuUpcoming> gosuUpcomingList = new ArrayList<>();
    private ArrayList<GosuPlayed> gosuPlayedList = new ArrayList<>();

    public FragmentF() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri.Builder builderStats = new Uri.Builder();
        builderStats.scheme("http")
                .authority("www.gosugamers.net")
                .appendPath("counterstrike")
                .appendPath("gosubet");
        String gosuMatches = builderStats.build().toString();

        requestMatches = new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .maxStale(2, TimeUnit.HOURS)
                        .build())
                .url(gosuMatches)
                .build();

        requestMatchesForceNetwork = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .url(gosuMatches)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f, container, false);

        gosuProgressView = (ProgressView) view.findViewById(R.id.gosu_progress_line);
        gosuProgressView.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gosu_recycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyGosuMatchesAdapter(getActivity(), gosuCurrentList, gosuUpcomingList, gosuPlayedList);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetGosuMatches().execute(requestMatches);

        gosuSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.gosu_swipe_refresh);
        gosuSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetGosuMatches().execute(requestMatchesForceNetwork);
            }
        });
        gosuSwipeRefresh.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }


    private class GetGosuMatches extends AsyncTask<Request, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            gosuProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Request... req) {
            Response response;

            int cacheSize = 5 * 1024 * 1024; // 5 MiB
            Cache gosuCache = new Cache(new File(MyApplication.getAppContext().getCacheDir(), "gosu_cache"), cacheSize);

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(15, TimeUnit.SECONDS);    // socket timeout
            client.setCache(gosuCache);

            try {
                response = client.newCall(req[0]).execute();
                String responseString = response.body().string();
                response.body().close();

                Document gosuDocument = Jsoup.parse(responseString);
                gosuDocument.setBaseUri("http://www.gosugamers.net");
                Element gosuTableCol1 = gosuDocument.getElementById("col1");

                gosuCurrentList.clear();
                gosuUpcomingList.clear();
                gosuPlayedList.clear();

                Elements boxes = gosuTableCol1.getElementsByClass("box");
                Elements boxCurrent = boxes.get(0).select("tr");
                for (int i = 0; i < boxCurrent.size(); i++) {
                    Element matchRow = boxCurrent.get(i);

                    Elements matchRowALinks = matchRow.select("a");
                    Element firstALink = matchRowALinks.get(0);
                    String url = firstALink.absUrl("href");
                    String opp_1 = firstALink.select("span.opp.opp1").select("span").get(0).text();
                    String opp_2 = firstALink.select("span.opp.opp2").select("span").get(0).text();

                    Element secondALink = matchRowALinks.get(1);
                    String imgUrl = secondALink.select("img").get(0).absUrl("src");

                    GosuCurrent tempGosuCurrent = new GosuCurrent(Uri.parse(url), opp_1, opp_2, Uri.parse(imgUrl));
                    gosuCurrentList.add(tempGosuCurrent);
                    /*
                    Log.d("TAG", "url : " + url);
                    Log.d("TAG", "opp_1 : " + opp_1 + " vs " + opp_2);
                    Log.d("TAG", "imgUrl : " + imgUrl);
                    Log.d("TAG", "******************************");*/
                }

                Elements boxUpcoming = boxes.get(1).select("tr");
                for (int i = 0; i < boxUpcoming.size(); i++) {
                    Element matchRow = boxUpcoming.get(i);

                    Elements matchRowALinks = matchRow.select("a");
                    Element firstALink = matchRowALinks.get(0);
                    String url = firstALink.absUrl("href");
                    String opp_1 = firstALink.select("span.opp.opp1").select("span").get(0).text();
                    String opp_2 = firstALink.select("span.opp.opp2").select("span").get(0).text();
                    String when = matchRow.select("span.live-in").text();

                    Element secondALink = matchRowALinks.get(1);
                    String imgUrl = secondALink.select("img").get(0).absUrl("src");

                    GosuUpcoming tempUpcoming = new GosuUpcoming(Uri.parse(url), opp_1, opp_2, when, Uri.parse(imgUrl));
                    gosuUpcomingList.add(tempUpcoming);
                    /*
                    Log.d("TAG", "url : " + url);
                    Log.d("TAG", "opp_1 : " + opp_1 + " vs " + opp_2);
                    Log.d("TAG", "when : " + when);
                    Log.d("TAG", "imgUrl : " + imgUrl);
                    Log.d("TAG", "______________________________________");*/
                }

                Elements boxPlayed = boxes.get(2).select("tr");
                for (int i = 0; i < boxPlayed.size(); i++) {
                    Element matchRow = boxPlayed.get(i);

                    Elements matchRowALinks = matchRow.select("a");
                    Element firstALink = matchRowALinks.get(0);
                    String url = firstALink.absUrl("href");
                    String opp_1 = firstALink.select("span.opp.opp1").select("span").get(0).text();
                    String opp_2 = firstALink.select("span.opp.opp2").select("span").get(0).text();

                    Elements scores = matchRow.select("span.score-wrap").select("span");
                    String homeScore = scores.get(3).text();
                    String awayScore = scores.get(4).text();

                    Element secondALink = matchRowALinks.get(1);
                    String imgUrl = secondALink.select("img").get(0).absUrl("src");

                    GosuPlayed tempGosuPlayed = new GosuPlayed(Uri.parse(url), opp_1, opp_2, Integer.valueOf(homeScore), Integer.valueOf(awayScore), Uri.parse(imgUrl));
                    gosuPlayedList.add(tempGosuPlayed);
                    /*
                    Log.d("TAG", "url : " + url);
                    Log.d("TAG", "opp_1 : " + opp_1 + " vs " + opp_2);
                    Log.d("TAG", "score : " + homeScore + " : " + awayScore);
                    Log.d("TAG", "imgUrl : " + imgUrl);
                    Log.d("TAG", "/////////////////////////////////////");*/
                }
            } catch (SocketTimeoutException ste) {
                return 2;
            } catch (IOException e) {
                return 3;
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result){
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "Taking too long to connect to server. Try again later.", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "Unknown error while getting data. (Results)", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            gosuProgressView.setVisibility(View.GONE);
            gosuSwipeRefresh.setRefreshing(false);
        }
    }


}
