/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.helpers.GosuCurrent;
import com.palascak.android.cstogo.helpers.GosuPlayed;
import com.palascak.android.cstogo.helpers.GosuUpcoming;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyGosuMatchesAdapter extends RecyclerView.Adapter<MyGosuMatchesAdapter.GosuMatchViewHolder> {

    private Context mContext;
    OnItemClickListener mItemClickListener;

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
                GosuCurrent currentMatch = mGosuCurrentList.get(position - 1);

                currentMatchesHolder.homeTeam.setText(currentMatch.getHomeTeam());
                currentMatchesHolder.awayTeam.setText(currentMatch.getAwayTeam());
                Picasso.with(mContext).load(currentMatch.getPictureUrl()).fit().centerCrop().into(currentMatchesHolder.competitionPicture);
                break;
            case TYPE_UPCOMING_HEADER:
                GosuUpcomingHeaderViewHolder upcomingHeaderHolder = (GosuUpcomingHeaderViewHolder) holder;
                break;
            case TYPE_UPCOMING_MATCHES:
                GosuUpcomingMatchesViewHolder upcomingMatchesHolder = (GosuUpcomingMatchesViewHolder) holder;
                GosuUpcoming upcomingMatch = mGosuUpcomingList.get(position - mGosuCurrentList.size() - 2);

                upcomingMatchesHolder.homeTeam.setText(upcomingMatch.getHomeTeam());
                upcomingMatchesHolder.awayTeam.setText(upcomingMatch.getAwayTeam());
                upcomingMatchesHolder.when.setText(upcomingMatch.getWhen());
                Picasso.with(mContext).load(upcomingMatch.getPictureUrl()).fit().centerCrop().into(upcomingMatchesHolder.competitionPicture);
                break;
            case TYPE_PLAYED_HEADER:
                GosuPlayedHeaderViewHolder playedHeaderHolder = (GosuPlayedHeaderViewHolder) holder;
                break;
            case TYPE_PLAYED_MATCHES:
                GosuPlayedMatchesViewHolder playedMatchesHolder = (GosuPlayedMatchesViewHolder) holder;
                GosuPlayed playedMatch = mGosuPlayedList.get(position - mGosuCurrentList.size() - mGosuUpcomingList.size() - 3);

                playedMatchesHolder.homeTeam.setText(playedMatch.getHomeTeam());
                playedMatchesHolder.awayTeam.setText(playedMatch.getAwayTeam());
                //playedMatchesHolder.homeScore.setText(String.valueOf(playedMatch.getHomeScore()));
                playedMatchesHolder.awayScore.setText(String.valueOf(playedMatch.getAwayScore()));
                Picasso.with(mContext).load(playedMatch.getPictureUrl()).fit().centerCrop().into(playedMatchesHolder.competitionPicture);

                String homeScore = Integer.toString(playedMatch.getHomeScore());
                String awayScore = Integer.toString(playedMatch.getAwayScore());
                if (playedMatch.getHomeScore() > playedMatch.getAwayScore()){
                    int scoreLength = homeScore.length();

                    final SpannableStringBuilder sb = new SpannableStringBuilder(homeScore);
                    // Span to set text color to some RGB value
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = mContext.getTheme();
                    theme.resolveAttribute(R.attr.accentColor, typedValue, true);
                    int accentColor = typedValue.data;
                    final ForegroundColorSpan spanAccent = new ForegroundColorSpan(accentColor);

                    // Set the text color for first characters based on how many numbers Wins have
                    sb.setSpan(spanAccent, 0, scoreLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    playedMatchesHolder.homeScore.setText(sb);
                } else {
                    playedMatchesHolder.homeScore.setText(homeScore);
                }

                if (playedMatch.getHomeScore() < playedMatch.getAwayScore()){
                    int scoreLength = awayScore.length();

                    final SpannableStringBuilder sb = new SpannableStringBuilder(awayScore);
                    // Span to set text color to some RGB value
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = mContext.getTheme();
                    theme.resolveAttribute(R.attr.accentColor, typedValue, true);
                    int accentColor = typedValue.data;
                    final ForegroundColorSpan spanAccent = new ForegroundColorSpan(accentColor);

                    // Set the text color for first characters based on how many numbers Wins have
                    sb.setSpan(spanAccent, 0, scoreLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    playedMatchesHolder.awayScore.setText(sb);
                } else {
                    playedMatchesHolder.awayScore.setText(awayScore);
                }

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

        private TextView homeTeam;
        private TextView awayTeam;
        private ImageView competitionPicture;

        public GosuCurrentMatchesViewHolder(View itemView) {
            super(itemView);
            homeTeam = (TextView) itemView.findViewById(R.id.gosu_current_matches_home_team);
            awayTeam = (TextView) itemView.findViewById(R.id.gosu_current_matches_away_team);
            competitionPicture = (ImageView) itemView.findViewById(R.id.gosu_current_matches_image);
        }
    }

    public class GosuUpcomingHeaderViewHolder extends GosuMatchViewHolder {

        public GosuUpcomingHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuUpcomingMatchesViewHolder extends GosuMatchViewHolder {

        private TextView homeTeam;
        private TextView awayTeam;
        private TextView when;
        private ImageView competitionPicture;

        public GosuUpcomingMatchesViewHolder(View itemView) {
            super(itemView);
            homeTeam = (TextView) itemView.findViewById(R.id.gosu_upcoming_matches_home_team);
            awayTeam = (TextView) itemView.findViewById(R.id.gosu_upcoming_matches_away_team);
            when = (TextView) itemView.findViewById(R.id.gosu_upcoming_matches_when);
            competitionPicture = (ImageView) itemView.findViewById(R.id.gosu_upcoming_matches_image);
        }
    }

    public class GosuPlayedHeaderViewHolder extends GosuMatchViewHolder {

        public GosuPlayedHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GosuPlayedMatchesViewHolder extends GosuMatchViewHolder implements View.OnClickListener{

        private TextView homeTeam;
        private TextView awayTeam;
        private TextView homeScore;
        private TextView awayScore;
        private ImageView competitionPicture;

        public GosuPlayedMatchesViewHolder(View itemView) {
            super(itemView);
            homeTeam = (TextView) itemView.findViewById(R.id.gosu_played_matches_home_team);
            awayTeam = (TextView) itemView.findViewById(R.id.gosu_played_matches_away_team);
            homeScore = (TextView) itemView.findViewById(R.id.gosu_played_matches_home_score);
            awayScore = (TextView) itemView.findViewById(R.id.gosu_played_matches_away_score);
            competitionPicture = (ImageView) itemView.findViewById(R.id.gosu_played_matches_image);

            competitionPicture.setClickable(true);
            competitionPicture.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public class GosuFooterViewHolder extends GosuMatchViewHolder {

        public GosuFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
