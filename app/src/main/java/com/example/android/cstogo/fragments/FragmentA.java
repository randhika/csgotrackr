/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.android.cstogo.R;
import com.example.android.cstogo.UpdateStatsEvent;
import com.example.android.cstogo.activities.NewMatchActivity;
import com.example.android.cstogo.adapters.MyMatchAdapter;
import com.example.android.cstogo.adapters.MyMatchAdapterBigCard;
import com.example.android.cstogo.adapters.MyMatchAdapterList;
import com.example.android.cstogo.helpers.MatchList;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.melnykov.fab.FloatingActionButton;

import de.greenrobot.event.EventBus;


public class FragmentA extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ObservableRecyclerView mRecyclerView;


    public FragmentA() {
        // Required empty public constructor
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.cardList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String matchpageStyle = prefs.getString("prefs_style_matchpage", "small_card");

        // specify an adapter
        if (matchpageStyle != null) {
            switch (matchpageStyle){
                case "big_card":
                    mAdapter = new MyMatchAdapterBigCard(getActivity(), MatchList.getInstance().matchList);
                    break;
                case "small_card":
                    mAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);
                    break;
                case "list":
                    mAdapter = new MyMatchAdapterList(getActivity(), MatchList.getInstance().matchList);
                    break;
                default:
                    mAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);
                    break;
            }
        } else {
            mAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);
        }
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        Animation fabGrow = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_fab);
        fab.startAnimation(fabGrow);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), NewMatchActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //mAdapter.notifyDataSetChanged();
                mAdapter.notifyItemInserted(0);
                EventBus.getDefault().post(new UpdateStatsEvent());
            }
        }
    }


}
