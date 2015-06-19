/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.melnykov.fab.FloatingActionButton;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.UpdateStatsEvent;
import com.palascak.android.cstogo.activities.NewMatchActivity;
import com.palascak.android.cstogo.adapters.MyMatchAdapter;
import com.palascak.android.cstogo.adapters.MyMatchAdapterBigCard;
import com.palascak.android.cstogo.adapters.MyMatchAdapterList;
import com.palascak.android.cstogo.helpers.Match;
import com.palascak.android.cstogo.helpers.MatchList;
import com.rey.material.app.Dialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import de.greenrobot.event.EventBus;


public class FragmentA extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private ImageView inflatedImage;

    public FragmentA() {
        // Required empty public constructor
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        if (MatchList.getInstance().matchList.size() == 0) {
            inflatedImage = (ImageView) view.findViewById(R.id.match_list_stub_imported_image);
            inflatedImage.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(R.drawable.add).fit().centerInside().into(inflatedImage);
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cardList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String matchpageStyle = prefs.getString("prefs_style_matchpage", "small_card");

        // specify an adapter
        switch (matchpageStyle){
            case "big_card":
                MyMatchAdapterBigCard bigCardAdapter = new MyMatchAdapterBigCard(getActivity(), MatchList.getInstance().matchList);

                bigCardAdapter.SetOnItemClickListener(new MyMatchAdapterBigCard.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final Dialog mDialog = new Dialog(getActivity());
                        mDialog.title("Do you wish to delete this match?")
                                .positiveAction("DELETE")
                                .negativeAction("CANCEL")
                                .cancelable(true)
                                .show();

                        mDialog.positiveActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeMatch(position);
                                mDialog.dismiss();
                            }
                        });

                        mDialog.negativeActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                    }
                });

                recyclerView.setAdapter(bigCardAdapter);
                mAdapter = bigCardAdapter;
                break;
            case "small_card":
                MyMatchAdapter classicAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);

                classicAdapter.SetOnItemClickListener(new MyMatchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final Dialog mDialog = new Dialog(getActivity());
                        mDialog.title("Do you wish to delete this match?")
                                .positiveAction("DELETE")
                                .negativeAction("CANCEL")
                                .cancelable(true)
                                .show();

                        mDialog.positiveActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeMatch(position);
                                mDialog.dismiss();
                            }
                        });

                        mDialog.negativeActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                    }
                });

                recyclerView.setAdapter(classicAdapter);
                mAdapter = classicAdapter;
                break;
            case "list":
                MyMatchAdapterList listAdapter = new MyMatchAdapterList(getActivity(), MatchList.getInstance().matchList);

                listAdapter.SetOnItemClickListener(new MyMatchAdapterList.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final Dialog mDialog = new Dialog(getActivity());
                        mDialog.title("Do you wish to delete this match?")
                                .positiveAction("DELETE")
                                .negativeAction("CANCEL")
                                .cancelable(true)
                                .show();

                        mDialog.positiveActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeMatch(position);
                                mDialog.dismiss();
                            }
                        });

                        mDialog.negativeActionClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                    }
                });

                recyclerView.setAdapter(listAdapter);
                mAdapter = listAdapter;
                break;
            default:
                MyMatchAdapter defaultAdapter = new MyMatchAdapter(getActivity(), MatchList.getInstance().matchList);
                recyclerView.setAdapter(defaultAdapter);
                mAdapter = defaultAdapter;
                break;
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);

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
            if(resultCode == Activity.RESULT_OK) {
                //mAdapter.notifyDataSetChanged();
                if (inflatedImage != null) {
                    inflatedImage.setVisibility(View.GONE);
                }

                mAdapter.notifyItemInserted(0);
                EventBus.getDefault().post(new UpdateStatsEvent());
            }
        }
    }

    private void removeMatch(int position){
        MatchList.getInstance().matchList.remove(position);

        File file = new File(getActivity().getFilesDir(), "match_list.dat");
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutStream;
        try {
            objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeInt(MatchList.getInstance().matchList.size()); // Save size first
            for(Match m:MatchList.getInstance().matchList)
                objectOutStream.writeObject(m);
            objectOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAdapter.notifyItemRemoved(position);
        EventBus.getDefault().post(new UpdateStatsEvent());
    }

    @SuppressWarnings("unused")
    private void createRandomMatches(){
        String [] names;
        names = getResources().getStringArray(R.array.maps_array);

        for (String name : names) {
            Match matchObject = new Match(16,
                    2,
                    20,
                    20,
                    20,
                    10,
                    25,
                    name);

            MatchList.getInstance().matchList.add(0, matchObject);
            mAdapter.notifyItemInserted(0);
        }
        EventBus.getDefault().post(new UpdateStatsEvent());
    }

}
