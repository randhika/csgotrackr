package com.example.android.cstogo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.helpers.Match;
import com.example.android.cstogo.adapters.MyMatchAdapter;
import com.example.android.cstogo.R;
import com.example.android.cstogo.UpdateStatsEvent;
import com.example.android.cstogo.activities.NewMatchActivity;
import com.example.android.cstogo.helpers.MatchList;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.melnykov.fab.FloatingActionButton;

import de.greenrobot.event.EventBus;


public class FragmentA extends Fragment {

    private MyMatchAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ObservableRecyclerView mRecyclerView;
    private String[] mTestArray;


    public FragmentA() {
        // Required empty public constructor
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestArray = getResources().getStringArray(R.array.maps_array);

        for (int i = 0; i < 8; i++) {
            Match dummyTest = new Match(16, 4, i, 2, 3, 4, 9, mTestArray[i]);
            MatchList.getInstance().matchList.add(dummyTest);
        }
        MatchList.getInstance().matchList.add(0, new Match(16, 1, 25, 25, 7, 10, 10, "de_train"));
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

        // specify an adapter
        mAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setScrollViewCallbacks(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

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
                mAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new UpdateStatsEvent());
            }
        }
    }


}
