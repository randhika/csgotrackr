package com.example.android.cstogo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * - Yuro - 24.3.2015.
 */
public class MyMatchAdapter extends RecyclerView.Adapter<MyMatchAdapter.MatchViewHolder> {

    private Context mContext;
    private List<Match> matchList;

    public MyMatchAdapter(Context context, List<Match> matchList){
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
        matchViewHolder.vKills.setText(Integer.toString(ci.getKills()));
        matchViewHolder.vAssists.setText(Integer.toString(ci.getAssists()));
        matchViewHolder.vDeaths.setText(Integer.toString(ci.getDeaths()));
        matchViewHolder.vMapName.setText(ci.getMap());
        matchViewHolder.vKad.setText(ci.getKad().toString());
        matchViewHolder.vMatchResult.setText(ci.getMatchResult());

        if (ci.getTeamRounds() > ci.getEnemyRounds()) {
            matchViewHolder.vResultStrip.setBackgroundColor(Color.rgb(9, 178, 24));
        } else if (ci.getTeamRounds() < ci.getEnemyRounds()) {
            matchViewHolder.vResultStrip.setBackgroundColor(Color.rgb(255, 64, 129));
        } else {
            matchViewHolder.vResultStrip.setBackgroundColor(Color.rgb(114, 137, 186));
        }

        switch (ci.getMap()) {
            case "de_nuke":
                Picasso.with(mContext).load(R.drawable.nuke_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_dust2":
                Picasso.with(mContext).load(R.drawable.dust2_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_inferno":
                Picasso.with(mContext).load(R.drawable.inferno_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_cache":
                Picasso.with(mContext).load(R.drawable.cache_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_cbble":
                Picasso.with(mContext).load(R.drawable.cbble_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_overpass":
                Picasso.with(mContext).load(R.drawable.overpass_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_season":
                Picasso.with(mContext).load(R.drawable.season_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            case "de_mirage":
                Picasso.with(mContext).load(R.drawable.mirage_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
            default:
                Picasso.with(mContext).load(R.drawable.dust2_res).fit().centerCrop().into(matchViewHolder.vMapPic);
                break;
        }

    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.match_card, viewGroup, false);

        return new MatchViewHolder(itemView);
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

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
        }
    }

}
