/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.fragments;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private RecyclerView.Adapter mAdapter;
    private Request requestTwitch;

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

        requestTwitch = new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .maxStale(30, TimeUnit.MINUTES)
                        .build())
                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
                .url(twitchUrl)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.twitch_recycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyTwitchStreamsAdapter(getActivity(), twitchStreamArrayList);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetTwitchStreams().execute(requestTwitch);

        return view;
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
            //swipeRefreshLayout.setRefreshing(false);
            //mProgressView.setVisibility(View.GONE);
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
            String twitchURL = arrayObjectChannel.getString("url");

            TwitchStream twitchStream = new TwitchStream(
                    twitchViewers,
                    twitchPreviewUrl,
                    twitchTitle,
                    twitchName,
                    twitchURL);

            twitchStreamArrayList.add(twitchStream);
        }
    }

}
