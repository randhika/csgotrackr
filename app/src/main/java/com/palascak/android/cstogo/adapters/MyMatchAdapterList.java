/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.helpers.Match;

import java.util.List;

public class MyMatchAdapterList extends RecyclerView.Adapter<MyMatchAdapterList.MatchViewHolder> {

    private Context mContext;
    private List<Match> matchList;
    OnItemClickListener mItemClickListener;

    public MyMatchAdapterList(Context context, List<Match> matchList){
        this.matchList = matchList;
        this.mContext= context;
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    @Override
    public void onBindViewHolder(MatchViewHolder matchViewHolder, int i){
        Match ci = matchList.get(i);

        matchViewHolder.vMapName.setText(ci.getMap());
        matchViewHolder.vResult.setText(ci.getMatchResult());
        //fun part
        String kills = Integer.toString(ci.getKills());
        int killsLength = kills.length();
        String assists = Integer.toString(ci.getAssists());
        String deaths = Integer.toString(ci.getDeaths());

        final SpannableStringBuilder sb = new SpannableStringBuilder(kills + ":" + assists + ":" + deaths);
        // Span to set text color to some RGB value
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = mContext.getTheme();
        theme.resolveAttribute(R.attr.accentColor, typedValue, true);
        int accentColor = typedValue.data;
        final ForegroundColorSpan spanAccent = new ForegroundColorSpan(accentColor);

        // Set the text color for first characters based on how many numbers Wins have
        sb.setSpan(spanAccent, 0, killsLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        matchViewHolder.vStats.setText(sb);

        matchViewHolder.vKAD.setText(ci.getKad().toString());

        String title = ci.getMap().substring(3,4).toUpperCase();
        matchViewHolder.vCircle.setText(title);

        matchViewHolder.vShapeDrawable.setColor(mContext.getResources().getColor(ci.calculateDominantColor()));
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.match_list, viewGroup, false);

        return new MatchViewHolder(itemView);
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        protected TextView vMapName;
        protected TextView vResult;
        protected TextView vStats;
        protected TextView vKAD;
        protected TextView vCircle;
        protected GradientDrawable vShapeDrawable;

        public MatchViewHolder(View v) {
            super(v);
            vCircle = (TextView) v.findViewById(R.id.matchListCircle);
            vMapName = (TextView) v.findViewById(R.id.matchListMapName);
            vResult = (TextView) v.findViewById(R.id.matchListResult);
            vStats = (TextView) v.findViewById(R.id.matchListMapStats);
            vKAD = (TextView) v.findViewById(R.id.matchListKAD);

            vShapeDrawable = (GradientDrawable) vCircle.getBackground();

            v.setClickable(true);
            v.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
