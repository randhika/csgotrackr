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
import com.palascak.android.cstogo.helpers.Match;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyMatchAdapterBigCard extends RecyclerView.Adapter<MyMatchAdapterBigCard.MatchViewHolder>{

    private Context mContext;
    private List<Match> matchList;
    OnItemClickListener mItemClickListener;

    public MyMatchAdapterBigCard(Context context, List<Match> matchList){
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

        matchViewHolder.vMap.setText(ci.getMap());
        matchViewHolder.vMvps.setText(Integer.toString(ci.getMvps()));
        Picasso.with(mContext).load(ci.getDrawable()).fit().centerCrop().into(matchViewHolder.vPicture);

        String dot = " · ";

        String kills = Integer.toString(ci.getKills());
        StringBuilder stats = new StringBuilder(kills)
                .append(dot)
                .append(ci.getAssists())
                .append(dot)
                .append(ci.getDeaths());

        int killsLength = kills.length();

        final SpannableStringBuilder sb = new SpannableStringBuilder(stats);
        // Span to set text color to some RGB value
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = mContext.getTheme();
        theme.resolveAttribute(R.attr.accentColor, typedValue, true);
        int accentColor = typedValue.data;
        final ForegroundColorSpan spanAccent = new ForegroundColorSpan(accentColor);
        // Set the text color for first characters based on how many numbers Wins have
        sb.setSpan(spanAccent, 0, killsLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        matchViewHolder.vStats.setText(sb);

        String score = ci.getMatchResult();

        StringBuilder scorePlusKad = new StringBuilder(score)
                .append(dot)
                .append(ci.getKad());

        int scoreLength = score.length();

        final SpannableStringBuilder sbScoreKad = new SpannableStringBuilder(scorePlusKad);
        sbScoreKad.setSpan(spanAccent, 0, scoreLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        matchViewHolder.vScoreKad.setText(sbScoreKad);

    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.match_big_card, viewGroup, false);

        return new MatchViewHolder(itemView);
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        protected TextView vScoreKad;
        protected TextView vMap;
        protected TextView vStats;
        protected TextView vMvps;
        protected ImageView vPicture;

        public MatchViewHolder(View v) {
            super(v);
            vScoreKad = (TextView) v.findViewById(R.id.match_big_card_score_plus_kad);
            vMap = (TextView) v.findViewById(R.id.match_big_card_map);
            vStats = (TextView) v.findViewById(R.id.match_big_card_stats);
            vMvps = (TextView) v.findViewById(R.id.match_big_card_mvps);
            vPicture = (ImageView) v.findViewById(R.id.match_big_card_picture);

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
