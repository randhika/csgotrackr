package com.palascak.android.cstogo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

/**
 * - Yuro - 24.3.2015.
 */
public class MyMatchAdapter extends RecyclerView.Adapter<MyMatchAdapter.MatchViewHolder> {

    private Context mContext;
    private List<Match> matchList;
    private int accentColor;
    OnItemClickListener mItemClickListener;

    public MyMatchAdapter(Context context, List<Match> matchList){
        this.matchList = matchList;
        this.mContext= context;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.accentColor, typedValue, true);
        this.accentColor = typedValue.data;
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    @Override
    public void onBindViewHolder(MatchViewHolder matchViewHolder, int i){
        Match ci = matchList.get(i);
        matchViewHolder.vKills.setText(Integer.toString(ci.getKills()));
        matchViewHolder.vAssists.setText(Integer.toString(ci.getAssists()));
        matchViewHolder.vDeaths.setText(Integer.toString(ci.getDeaths()));
        matchViewHolder.vMapName.setText(ci.getMap());
        matchViewHolder.vKad.setText(ci.getKad().toString());
        matchViewHolder.vMatchResult.setText(ci.getMatchResult());

        if (ci.getTeamRounds() > ci.getEnemyRounds()) {
            matchViewHolder.vResultStrip.setBackgroundColor(accentColor);
        } else if (ci.getTeamRounds() < ci.getEnemyRounds()) {
            matchViewHolder.vResultStrip.setBackgroundColor(Color.rgb(91, 91, 91));
        } else {
            matchViewHolder.vResultStrip.setBackgroundColor(Color.rgb(114, 137, 186));
        }

        Picasso.with(mContext).load(ci.calculateDrawable()).fit().centerCrop().into(matchViewHolder.vMapPic);

    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.match_card, viewGroup, false);

        return new MatchViewHolder(itemView);
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        protected TextView vKills;
        protected TextView vAssists;
        protected TextView vDeaths;
        protected TextView vMapName;
        protected TextView vMatchResult;
        protected TextView vKad;
        protected ImageView vMapPic;
        protected ImageView vResultStrip;

        public MatchViewHolder(View v) {
            super(v);
            vKills =  (TextView) v.findViewById(R.id.card_kills);
            vAssists = (TextView)  v.findViewById(R.id.card_assists);
            vDeaths = (TextView)  v.findViewById(R.id.card_deaths);
            vMapName = (TextView) v.findViewById(R.id.card_map_name);
            vKad = (TextView) v.findViewById(R.id.card_kad);
            vMatchResult = (TextView) v.findViewById(R.id.card_match_result);
            vMapPic = (ImageView) v.findViewById(R.id.card_map_pic);
            vResultStrip = (ImageView) v.findViewById(R.id.card_strip);

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
