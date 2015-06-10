package com.palascak.android.cstogo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.activities.SmokesActivity;
import com.palascak.android.cstogo.adapters.MySmokesListAdapter;
import com.palascak.android.cstogo.helpers.Smoke;

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
                                myIntent.putExtra("TEMP", createItems("de_9"));
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

        switch (name) {
            case "de_dust":
                tempList.add(new Smoke("slope - back plat", R.drawable.de_dust2, R.drawable.dust_smoke_1));
                tempList.add(new Smoke("slope - b car", R.drawable.de_dust2, R.drawable.dust_smoke_2));
                tempList.add(new Smoke("slope - connector", R.drawable.de_dust2, R.drawable.dust_smoke_3));
                tempList.add(new Smoke("b door - b car", R.drawable.de_dust2, R.drawable.dust_smoke_4));
                tempList.add(new Smoke("b car - b window", R.drawable.de_dust2, R.drawable.dust_smoke_5));
                tempList.add(new Smoke("xbox - ct spawn", R.drawable.de_dust2, R.drawable.dust_smoke_6));
                tempList.add(new Smoke("long car - long door", R.drawable.de_dust2, R.drawable.dust_smoke_7));
                tempList.add(new Smoke("long car - long barrel", R.drawable.de_dust2, R.drawable.dust_smoke_8));
                tempList.add(new Smoke("ct spawn - mid", R.drawable.de_dust2, R.drawable.dust_smoke_9));
                tempList.add(new Smoke("short - a site", R.drawable.de_dust2, R.drawable.dust_smoke_10));
                break;
            case "de_inferno":
                for (int i = 0; i < 15; i++) {
                    tempList.add(new Smoke(name + i, R.drawable.de_inferno, R.drawable.de_inferno));
                }
                break;
            case "de_9":
                for (int i = 0; i < 15; i++) {
                    tempList.add(new Smoke(name + i, R.drawable.de_season, R.drawable.de_season));
                }
                break;
        }

        return tempList;
    }


}
