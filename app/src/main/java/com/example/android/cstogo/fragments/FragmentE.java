/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.fragments;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentE extends Fragment {


    public FragmentE() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // https://api.twitch.tv/kraken/search/streams?q=Counter-Strike%3A%20Global%20Offensive&limit=10

        Uri.Builder builderStats = new Uri.Builder();
        builderStats.scheme("https")
                .authority("api.twitch.tv")
                .appendPath("kraken")
                .appendPath("search")
                .appendPath("streams")
                .appendQueryParameter("q", "Counter-Strike: Global Offensive")
                .appendQueryParameter("limit", "10");
        String twitchUrl = builderStats.build().toString();

        Request requestTwitch = new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .maxStale(1, TimeUnit.HOURS)
                        .build())
                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
                .url(twitchUrl)
                .build();

        new GetTwitchStreams().execute(requestTwitch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e, container, false);
    }

    private class GetTwitchStreams extends AsyncTask<Request, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Request... req) {
            Response response;

            int cacheSize = 5 * 1024 * 1024; // 5 MiB
            Cache cache = new Cache(new File(MyApplication.getAppContext().getCacheDir(), "twitch_cache"), cacheSize);

            OkHttpClient client = new OkHttpClient();
            client.setCache(cache);

            try {
                response = client.newCall(req[0]).execute();
                String jsonData = response.body().string();
                response.body().close();

                Log.d("TAG", "headers : " + response.headers().toString());
                Log.d("TAG", "______________");
                Log.d("TAG", "whole : " + response.toString());
                Log.d("TAG", "______________");
                Log.d("TAG", "body : " + jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //swipeRefreshLayout.setRefreshing(false);
            //mProgressView.setVisibility(View.GONE);
        }
    }

}
