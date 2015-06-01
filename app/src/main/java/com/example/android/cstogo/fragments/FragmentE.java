/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.adapters.MyTwitchStreamsAdapter;
import com.example.android.cstogo.helpers.TwitchStream;
import com.rey.material.widget.ProgressView;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentE extends Fragment {

    private static ArrayList<TwitchStream> twitchStreamArrayList = new ArrayList<>();

    private MyTwitchStreamsAdapter mAdapter;

    private Request requestTwitch;
    private Request requestTwitchForceNetwork;

    private SwipeRefreshLayout twitchSwipeRefresh;
    private ProgressView mProgressView;

    private boolean isTwitchPresent = false;

    public FragmentE() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri.Builder builderStats = new Uri.Builder();
        builderStats.scheme("https")
                .authority("api.twitch.tv")
                .appendPath("kraken")
                .appendPath("streams")
                .appendQueryParameter("game", "Counter-Strike: Global Offensive")
                .appendQueryParameter("limit", "10");
        String twitchUrl = builderStats.build().toString();

        requestTwitch = new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .maxStale(30, TimeUnit.MINUTES)
                        .build())
                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
                .url(twitchUrl)
                .build();

        requestTwitchForceNetwork = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
                .url(twitchUrl)
                .build();

        isTwitchPresent = isPackageInstalled("tv.twitch.android.app", getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e, container, false);

        mProgressView = (ProgressView) view.findViewById(R.id.twitch_progress_line);
        mProgressView.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.twitch_recycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyTwitchStreamsAdapter(getActivity(), twitchStreamArrayList);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetTwitchStreams().execute(requestTwitch);

        mAdapter.SetOnItemClickListener(new MyTwitchStreamsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isTwitchPresent){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndNormalize(twitchStreamArrayList.get(position).getTwitchURL());
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Twitch app doesn't seem to be installed. Get it from play store, it's pretty good", Toast.LENGTH_LONG).show();
                }
            }
        });

        twitchSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.twitch_swipe_refresh);
        twitchSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetTwitchStreams().execute(requestTwitchForceNetwork);
            }
        });
        twitchSwipeRefresh.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    private class GetTwitchStreams extends AsyncTask<Request, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Request... req) {
            Response response;

            int cacheSize = 5 * 1024 * 1024; // 5 MiB
            Cache twitchCache = new Cache(new File(MyApplication.getAppContext().getCacheDir(), "twitch_cache"), cacheSize);

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(15, TimeUnit.SECONDS);    // socket timeout
            client.setCache(twitchCache);

            try {
                response = client.newCall(req[0]).execute();
                String responseString = response.body().string();
                response.body().close();

                JSONArray twitchJsonStreamsArray = new JSONObject(responseString).getJSONArray("streams");
                createStreamList(twitchJsonStreamsArray);

                if (!response.isSuccessful()){
                    return 3;
                }
            } catch (SocketTimeoutException ste){
                return 2;
            } catch (IOException | JSONException e) {
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
                    Toast.makeText(getActivity(), "Unknown error while getting data.", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            twitchSwipeRefresh.setRefreshing(false);
            mProgressView.setVisibility(View.GONE);
        }
    }

    private void createStreamList(JSONArray array) throws JSONException{

        twitchStreamArrayList.clear();

        for (int i = 0; i < array.length(); i++) {

            JSONObject arrayObject = array.getJSONObject(i);
            JSONObject arrayObjectChannel = arrayObject.getJSONObject("channel");
            JSONObject arrayObjectPreview = arrayObject.getJSONObject("preview");

            String twitchViewers = arrayObject.getString("viewers");
            String twitchPreviewUrl = arrayObjectPreview.getString("large");
            String twitchTitle = arrayObjectChannel.getString("status");
            String twitchName = arrayObjectChannel.getString("display_name");
            Uri twitchURL = Uri.parse("twitch://stream/" + arrayObjectChannel.getString("name"));

            TwitchStream twitchStream = new TwitchStream(
                    twitchViewers,
                    twitchPreviewUrl,
                    twitchTitle,
                    twitchName,
                    twitchURL);

            twitchStreamArrayList.add(twitchStream);
        }
    }

    private boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
