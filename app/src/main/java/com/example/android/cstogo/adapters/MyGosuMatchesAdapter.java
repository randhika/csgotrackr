/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.R;
import com.example.android.cstogo.helpers.GosuCurrent;
import com.example.android.cstogo.helpers.GosuPlayed;
import com.example.android.cstogo.helpers.GosuUpcoming;

import java.util.ArrayList;

public class MyGosuMatchesAdapter extends RecyclerView.Adapter<MyGosuMatchesAdapter.GosuMatchViewHolder> {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Context mContext;
    private ArrayList<GosuCurrent> mGosuCurrentList;
    private ArrayList<GosuUpcoming> mGosuUpcomingList;
    private ArrayList<GosuPlayed> mGosuPlayedList;

    private static final int TYPE_CURRENT_HEADER = 0;
    private static final int TYPE_CURRENT_MATCHES = 1;
    private static final int TYPE_UPCOMING_HEADER = 2;
    private static final int TYPE_UPCOMING_MATCHES = 3;
    private static final int TYPE_PLAYED_HEADER = 4;
    private static final int TYPE_PLAYED_MATCHES = 5;
    private static final int TYPE_GOSU_FOOTER = 6;

    public MyGosuMatchesAdapter(Context context,
                                ArrayList<GosuCurrent> gosuCurrentList,
                                ArrayList<GosuUpcoming> gosuUpcomingList,
                                ArrayList<GosuPlayed> gosuPlayedList) {
        mContext = context;
        mGosuCurrentList = gosuCurrentList;
        mGosuUpcomingList = gosuUpcomingList;
        mGosuPlayedList = gosuPlayedList;
    }

    @Override
    public GosuMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GosuMatchViewHolder holder;
        View v;
        Context context = parent.getContext();

        switch (viewType) {
            case TYPE_CURRENT_HEADER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_current_header, parent, false);

                holder = new GosuCurrentHeaderViewHolder(v);
                break;
            case TYPE_CURRENT_MATCHES:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_current_matches, parent, false);

                holder = new GosuCurrentMatchesViewHolder(v);
                break;
            case TYPE_UPCOMING_HEADER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_upcoming_header, parent, false);

                holder = new GosuUpcomingHeaderViewHolder(v);
                break;
            case TYPE_UPCOMING_MATCHES:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_upcoming_matches, parent, false);

                holder = new GosuUpcomingMatchesViewHolder(v);
                break;
            case TYPE_PLAYED_HEADER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_played_header, parent, false);

                holder = new GosuPlayedHeaderViewHolder(v);
                break;
            case TYPE_PLAYED_MATCHES:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_played_matches, parent, false);

                holder = new GosuPlayedMatchesViewHolder(v);
                break;
            case TYPE_GOSU_FOOTER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_footer, parent, false);

                holder = new GosuFooterViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.gosu_footer, parent, false);
                holder = new GosuFooterViewHolder(v);
        }
        return holder;
    }

    @SuppressWarnings("unused")
    @Override
    public void onBindViewHolder(GosuMatchViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_CURRENT_HEADER:
                GosuCurrentHeaderViewHolder currentHeaderHolder = (GosuCurrentHeaderViewHolder) holder;
                break;
            case TYPE_CURRENT_MATCHES:
                GosuCurrentMatchesViewHolder currentMatchesHolder = (GosuCurrentMatchesViewHolder) holder;
                break;
            case TYPE_UPCOMING_HEADER:
                GosuUpcomingHeaderViewHolder upcomingHeaderHolder = (GosuUpcomingHeaderViewHolder) holder;
                break;
            case TYPE_UPCOMING_MATCHES:
                GosuUpcomingMatchesViewHolder upcomingMatchesHolder = (GosuUpcomingMatchesViewHolder) holder;
                break;
            case TYPE_PLAYED_HEADER:
                GosuPlayedHeaderViewHolder playedHeaderHolder = (GosuPlayedHeaderViewHolder) holder;
                break;
            case TYPE_PLAYED_MATCHES:
                GosuPlayedMatchesViewHolder playedMatchesHolder = (GosuPlayedMatchesViewHolder) holder;
                break;
            case TYPE_GOSU_FOOTER:
                GosuFooterViewHolder gosuFooterHolder = (GosuFooterViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mGosuCurrentList.size() == 0 && mGosuUpcomingList.size() == 0 && mGosuPlayedList.size() == 0 ) {
            return 0;
        } else {
            return mGosuCurrentList.size() + mGosuUpcomingList.size() + mGosuPlayedList.size() + 4;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CURRENT_HEADER;
        }
        if (position > 0 && position < mGosuCurrentList.size() + 1) {
            return TYPE_CURRENT_MATCHES;
        }
        if (position == mGosuCurrentList.size() + 1){
            return TYPE_UPCOMING_HEADER;
        }
        if (position > mGosuCurrentList.size() + 1 && position < mGosuUpcomingList.size() + mGosuCurrentList.size() + 2){
            return TYPE_UPCOMING_MATCHES;
        }
        if (position == mGosuUpcomingList.size() + mGosuCurrentList.size() + 2){
            return TYPE_PLAYED_HEADER;
        }
        if (position > mGosuUpcomingList.size() + mGosuCurrentList.size() + 2 && position < mGosuPlayedList.size() + mGosuUpcomingList.size() + mGosuCurrentList.size() + 3){
            return TYPE_PLAYED_MATCHES;
        }
        if (position >=  mGosuPlayedList.size() + mGosuUpcomingList.size() + mGosuCurrentList.size() + 3){
            return TYPE_GOSU_FOOTER;
        }
        return TYPE_GOSU_FOOTER;
    }

    public class GosuMatchViewHolder extends RecyclerView.ViewHolder {
        public GosuMatchViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuCurrentHeaderViewHolder extends GosuMatchViewHolder {

        public GosuCurrentHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuCurrentMatchesViewHolder extends GosuMatchViewHolder {

        public GosuCurrentMatchesViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuUpcomingHeaderViewHolder extends GosuMatchViewHolder {

        public GosuUpcomingHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuUpcomingMatchesViewHolder extends GosuMatchViewHolder {

        public GosuUpcomingMatchesViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuPlayedHeaderViewHolder extends GosuMatchViewHolder {

        public GosuPlayedHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuPlayedMatchesViewHolder extends GosuMatchViewHolder {

        public GosuPlayedMatchesViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuFooterViewHolder extends GosuMatchViewHolder {

        public GosuFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
