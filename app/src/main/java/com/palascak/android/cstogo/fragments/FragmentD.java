package com.palascak.android.cstogo.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.activities.SmokesActivity;
import com.palascak.android.cstogo.activities.SmokesHelpActivity;
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
        smokeMapList.add("help");
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
                        Intent myIntent;
                        if (tempPosition < 8) {
                            myIntent = new Intent(getActivity(), SmokesActivity.class);
                        } else {
                            myIntent = new Intent(getActivity(), SmokesHelpActivity.class);
                        }
                        switch (tempPosition) {
                            case 0:
                                myIntent.putExtra("TEMP", createItems("de_dust"));
                                break;
                            case 1:
                                myIntent.putExtra("TEMP", createItems("de_inferno"));
                                break;
                            case 2:
                                myIntent.putExtra("TEMP", createItems("de_mirage"));
                                break;
                            case 3:
                                myIntent.putExtra("TEMP", createItems("de_cbble"));
                                break;
                            case 4:
                                myIntent.putExtra("TEMP", createItems("de_overpass"));
                                break;
                            case 5:
                                myIntent.putExtra("TEMP", createItems("de_cache"));
                                break;
                            case 6:
                                myIntent.putExtra("TEMP", createItems("de_season"));
                                break;
                            case 7:
                                myIntent.putExtra("TEMP", createItems("de_train"));
                                break;
                            default:
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
                tempList.add(new Smoke("banana - ct spawn", R.drawable.de_inferno, R.drawable.inferno_smoke_1));
                tempList.add(new Smoke("banana - spools", R.drawable.de_inferno, R.drawable.inferno_smoke_2));
                tempList.add(new Smoke("molly, banana - new box", R.drawable.de_inferno, R.drawable.inferno_smoke_3));
                tempList.add(new Smoke("arch side - b cart", R.drawable.de_inferno, R.drawable.inferno_smoke_4));
                tempList.add(new Smoke("apartments - pit", R.drawable.de_inferno, R.drawable.inferno_smoke_5));
                tempList.add(new Smoke("hay stack - pit car", R.drawable.de_inferno, R.drawable.inferno_smoke_6));
                tempList.add(new Smoke("hay stack - pit", R.drawable.de_inferno, R.drawable.inferno_smoke_7));
                tempList.add(new Smoke("hay stack - arch", R.drawable.de_inferno, R.drawable.inferno_smoke_8));
                tempList.add(new Smoke("ct spawn - banana", R.drawable.de_inferno, R.drawable.inferno_smoke_9));
                break;
            case "de_mirage":
                tempList.add(new Smoke("t left - a stairs", R.drawable.de_mirage, R.drawable.mirage_smoke_1));
                tempList.add(new Smoke("stairs - b kitchen window", R.drawable.de_mirage, R.drawable.mirage_smoke_2));
                tempList.add(new Smoke("t left - ticket", R.drawable.de_mirage, R.drawable.mirage_smoke_3));
                tempList.add(new Smoke("stairs - bench", R.drawable.de_mirage, R.drawable.mirage_smoke_4));
                tempList.add(new Smoke("t right - connector", R.drawable.de_mirage, R.drawable.mirage_smoke_5));
                tempList.add(new Smoke("t left - jungle/connector", R.drawable.de_mirage, R.drawable.mirage_smoke_6));
                tempList.add(new Smoke("stairs - mid window", R.drawable.de_mirage, R.drawable.mirage_smoke_7));
                tempList.add(new Smoke("cart - short", R.drawable.de_mirage, R.drawable.mirage_smoke_8));
                break;
            case "de_cbble":
                tempList.add(new Smoke("balcony - long a", R.drawable.de_cbble, R.drawable.cbble_smoke_1));
                tempList.add(new Smoke("long a - double doors", R.drawable.de_cbble, R.drawable.cbble_smoke_2));
                tempList.add(new Smoke("b main - site", R.drawable.de_cbble, R.drawable.cbble_smoke_3));
                tempList.add(new Smoke("long a - ct truck", R.drawable.de_cbble, R.drawable.cbble_smoke_4));
                tempList.add(new Smoke("b main - hut", R.drawable.de_cbble, R.drawable.cbble_smoke_5));
                break;
            case "de_overpass":
                tempList.add(new Smoke("construction - sniper", R.drawable.de_overpass, R.drawable.overpass_smoke_1));
                tempList.add(new Smoke("track - bridge", R.drawable.de_overpass, R.drawable.overpass_smoke_2));
                tempList.add(new Smoke("construction - safe", R.drawable.de_overpass, R.drawable.overpass_smoke_3));
                tempList.add(new Smoke("molly, squeaky - toxic", R.drawable.de_overpass, R.drawable.overpass_smoke_4));
                tempList.add(new Smoke("tracks - pillar", R.drawable.de_overpass, R.drawable.overpass_smoke_5));
                tempList.add(new Smoke("construction - safe (2)", R.drawable.de_overpass, R.drawable.overpass_smoke_6));
                tempList.add(new Smoke("short a - bank", R.drawable.de_overpass, R.drawable.overpass_smoke_7));
                tempList.add(new Smoke("cafe - back a", R.drawable.de_overpass, R.drawable.overpass_smoke_8));
                tempList.add(new Smoke("cafe - ct truck", R.drawable.de_overpass, R.drawable.overpass_smoke_9));
                break;
            case "de_cache":
                tempList.add(new Smoke("a long - quad", R.drawable.de_cache, R.drawable.cache_smoke_1));
                tempList.add(new Smoke("a main - forklift", R.drawable.de_cache, R.drawable.cache_smoke_2));
                tempList.add(new Smoke("b pop flash", R.drawable.de_cache, R.drawable.cache_smoke_3));
                tempList.add(new Smoke("a long - crate", R.drawable.de_cache, R.drawable.cache_smoke_4));
                tempList.add(new Smoke("molly, flash window - generator", R.drawable.de_cache, R.drawable.cache_smoke_5));
                tempList.add(new Smoke("boost boxes - ct connector", R.drawable.de_cache, R.drawable.cache_smoke_6));
                break;
            case "de_season":
                tempList.add(new Smoke("t vents - ct b lower", R.drawable.de_season, R.drawable.season_smoke_1));
                tempList.add(new Smoke("ct spawn - mid", R.drawable.de_season, R.drawable.season_smoke_2));
                tempList.add(new Smoke("mid - J", R.drawable.de_season, R.drawable.season_smoke_3));
                tempList.add(new Smoke("outside - upper b", R.drawable.de_season, R.drawable.season_smoke_4));
                break;
            case "de_train":
                tempList.add(new Smoke("dumpster - z connector", R.drawable.de_train, R.drawable.train_smoke_1));
                tempList.add(new Smoke("upper ladder - 1", R.drawable.de_train, R.drawable.train_smoke_2));
                tempList.add(new Smoke("t connector - between trains", R.drawable.de_train, R.drawable.train_smoke_3));
                tempList.add(new Smoke("ladder room - between right and site", R.drawable.de_train, R.drawable.train_smoke_4));
                tempList.add(new Smoke("boilers - b site", R.drawable.de_train, R.drawable.train_smoke_5));
                tempList.add(new Smoke("b upper", R.drawable.de_train, R.drawable.train_smoke_6));
                tempList.add(new Smoke("A wall close", R.drawable.de_train, R.drawable.train_smoke_7));
                tempList.add(new Smoke("A wall top", R.drawable.de_train, R.drawable.train_smoke_8));
                tempList.add(new Smoke("A wall connector", R.drawable.de_train, R.drawable.train_smoke_9));
                tempList.add(new Smoke("A site wall", R.drawable.de_train, R.drawable.train_smoke_nip_wall));
                break;
        }

        return tempList;
    }


}
