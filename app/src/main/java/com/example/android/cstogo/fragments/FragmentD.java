package com.example.android.cstogo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.R;
import com.example.android.cstogo.activities.SmokesActivity;
import com.example.android.cstogo.adapters.MySmokesListAdapter;
import com.example.android.cstogo.helpers.Smoke;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentD extends Fragment{

    private ArrayList<String> smokeMapList = new ArrayList<>();

    public FragmentD() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smokeMapList.clear();
        smokeMapList.add("de_dust2");
        smokeMapList.add("de_inferno");
        smokeMapList.add("de_mirage");
        smokeMapList.add("de_cbble");
        smokeMapList.add("de_overpass");
        smokeMapList.add("de_cache");
        smokeMapList.add("de_season");
        smokeMapList.add("de_train");
        smokeMapList.add("de_nuke");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.smokesMapList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // specify an adapter
        MySmokesListAdapter mAdapter = new MySmokesListAdapter(getActivity(), smokeMapList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MySmokesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final int tempPosition = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(getActivity(), SmokesActivity.class);
                        switch (tempPosition){
                            case 0:
                                myIntent.putExtra("TEMP", createItems("de_dust"));
                                break;
                            case 1:
                                myIntent.putExtra("TEMP", createItems("de_inferno"));
                                break;
                            default:
                                myIntent.putExtra("TEMP", createItems("de_69"));
                                break;

                        }
                        getActivity().startActivity(myIntent);
                    }
                }, 600);
            }
        });

        return view;
    }

    private ArrayList<Smoke> createItems(String name) {
        ArrayList<Smoke> tempList = new ArrayList<>();
        tempList.clear();
        for (int i = 0; i < 15; i++) {
            switch (name) {
                case "de_dust":
                    tempList.add(new Smoke(name + i, R.drawable.de_dust2, R.drawable.de_dust2));
                    break;
                case "de_inferno":
                    tempList.add(new Smoke(name + i, R.drawable.de_inferno, R.drawable.de_inferno));
                    break;
                case "de_69":
                    tempList.add(new Smoke(name + i, R.drawable.de_season, R.drawable.de_season));
                    break;
            }

        }
        return tempList;
    }


}
