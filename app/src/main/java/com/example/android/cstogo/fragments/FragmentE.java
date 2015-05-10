package com.example.android.cstogo.fragments;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.adapters.MySteamWebStatsAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentE extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private List<String> steamWebHeader = new ArrayList<>();

    public FragmentE() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e, container, false);


        String apiKey = getResources().getString(R.string.api_key);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.steampowered.com")
                .appendPath("ISteamUser")
                .appendPath("GetPlayerSummaries")
                .appendPath("v0002")
                .appendQueryParameter("key", apiKey)
                .appendQueryParameter("steamids", "76561198011602043");
        String myUrl = builder.build().toString();

        new GetSteamUserData().execute(myUrl);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.steam_web_stats_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MySteamWebStatsAdapter(getActivity(), steamWebHeader);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    private class GetSteamUserData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            Response response;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject dataJsonObj = new JSONObject(jsonData).getJSONObject("response").getJSONArray("players").getJSONObject(0);
                String personaname = dataJsonObj.getString("personaname");
                steamWebHeader.add(personaname);
                String avatarfull = dataJsonObj.getString("avatarfull");
                steamWebHeader.add(avatarfull);
                String personastate = dataJsonObj.getString("personastate");
                steamWebHeader.add(personastate);
                return "okay";
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MyApplication.getAppContext(), "Error connecting to steam API. Please try again later.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
