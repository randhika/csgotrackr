package com.example.android.cstogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cstogo.R;
import com.example.android.cstogo.helpers.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MySteamWebStatsAdapter extends RecyclerView.Adapter<MySteamWebStatsAdapter.WebStatsViewHolder> {

    private Context mContext;
    private List<String> steamWebHeader;
    private List<String> steamWebWebMap;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_WEBMAP = 1;
    private static final int TYPE_WEBGUN = 2;

    public MySteamWebStatsAdapter(Context context, List<String> steamWebHeader, List<String> steamWebWebMap) {
        this.mContext = context;
        this.steamWebHeader = steamWebHeader;
        this.steamWebWebMap = steamWebWebMap;
    }

    @Override
    public int getItemCount() {
        if (steamWebHeader.size() == 0 || steamWebWebMap.size() == 0){
            return 0;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(MySteamWebStatsAdapter.WebStatsViewHolder viewHolder, int i) {

            if (getItemViewType(i) == TYPE_HEADER) {
                SteamWebStatsHeaderViewHolder headerHolder = (SteamWebStatsHeaderViewHolder) viewHolder;
                headerHolder.vHeaderNickname.setText(steamWebHeader.get(0));
                Picasso.with(mContext).load(steamWebHeader.get(1)).transform(new CircleTransform()).fit().centerCrop().into(headerHolder.vHeaderAvatar);
                headerHolder.vHeaderStatus.setText(steamWebHeader.get(2));
            } else {
                SteamWebStatsWebMapViewHolder webMapHolder = (SteamWebStatsWebMapViewHolder) viewHolder;
                webMapHolder.vWebMapWinPercName.setText(steamWebWebMap.get(0));
                webMapHolder.vWebMapWinPercNumber.setText(steamWebWebMap.get(1));
                webMapHolder.vWebMapWinsName.setText(steamWebWebMap.get(2));
                webMapHolder.vWebMapWinsNumber.setText(steamWebWebMap.get(3));
                webMapHolder.vWebMapRoundsName.setText(steamWebWebMap.get(4));
                webMapHolder.vWebMapRoundsNumber.setText(steamWebWebMap.get(5));
            }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_WEBMAP;
            case 2:
                return TYPE_WEBGUN;
            default:
                return 3;
        }
    }

    @Override
    public WebStatsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        WebStatsViewHolder holder;
        View v;
        Context context = viewGroup.getContext();

        switch (viewType) {
            case TYPE_HEADER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_header, viewGroup, false);

                holder = new SteamWebStatsHeaderViewHolder(v); //Of type GeneralViewHolder
                break;
            case TYPE_WEBMAP:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_webmap, viewGroup, false);
                holder = new SteamWebStatsWebMapViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_header, viewGroup, false);
                holder = new SteamWebStatsWebMapViewHolder(v);
        }
        return holder;
    }


    public class WebStatsViewHolder extends RecyclerView.ViewHolder {
        public WebStatsViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class SteamWebStatsHeaderViewHolder extends WebStatsViewHolder {

        protected TextView vHeaderNickname;
        protected TextView vHeaderStatus;
        protected ImageView vHeaderAvatar;

        public SteamWebStatsHeaderViewHolder(View itemView) {
            super(itemView);
            vHeaderNickname = (TextView) itemView.findViewById(R.id.steam_web_stats_header_nickname);
            vHeaderStatus = (TextView) itemView.findViewById(R.id.steam_web_stats_header_status);
            vHeaderAvatar = (ImageView) itemView.findViewById(R.id.steam_web_stats_header_avatar);
        }
    }

    public class SteamWebStatsWebMapViewHolder extends WebStatsViewHolder {

        protected TextView vWebMapWinPercName;
        protected TextView vWebMapWinPercNumber;
        protected TextView vWebMapWinsName;
        protected TextView vWebMapWinsNumber;
        protected TextView vWebMapRoundsName;
        protected TextView vWebMapRoundsNumber;

        public SteamWebStatsWebMapViewHolder(View itemView) {
            super(itemView);
            vWebMapWinPercName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_win_perc_map);
            vWebMapWinPercNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_win_perc_number);

            vWebMapWinsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_wins_map);
            vWebMapWinsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_wins_number);

            vWebMapRoundsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_rounds_map);
            vWebMapRoundsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_rounds_number);
        }
    }
}
