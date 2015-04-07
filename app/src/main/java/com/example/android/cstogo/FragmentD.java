package com.example.android.cstogo;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentD extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> smokeMapList = new ArrayList<>();

    public FragmentD() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.smokesMapList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MySmokesAdapter(getActivity(), smokeMapList);
        mRecyclerView.setAdapter(mAdapter);

        smokeMapList.clear();
        smokeMapList.add("de_dust2");
        smokeMapList.add("de_inferno");
        smokeMapList.add("de_mirage");
        smokeMapList.add("de_cbble");
        smokeMapList.add("de_overpass");
        smokeMapList.add("de_cache");
        smokeMapList.add("de_season");
        smokeMapList.add("de_nuke");

        return view;
    }


}
