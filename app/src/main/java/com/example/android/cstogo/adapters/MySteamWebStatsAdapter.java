package com.example.android.cstogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.R;

import java.util.List;


public class MySteamWebStatsAdapter extends RecyclerView.Adapter<MySteamWebStatsAdapter.WebStatsViewHolder> {

    private Context mContext;
    private List<String> smokeMapList;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_WEBMAP = 1;
    private static final int TYPE_WEBGUN = 2;

    public MySteamWebStatsAdapter(Context context, List<String> smokeMapList){
        this.mContext= context;
    }

    @Override
    public int getItemCount() {
        return smokeMapList.size();
    }

    @Override
    public void onBindViewHolder(MySteamWebStatsAdapter.WebStatsViewHolder viewHolder, int i){

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


    public class WebStatsViewHolder extends RecyclerView.ViewHolder{
        public WebStatsViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class SteamWebStatsHeaderViewHolder extends WebStatsViewHolder{
        public SteamWebStatsHeaderViewHolder(View itemView){
            super(itemView);
        }
    }

    public class SteamWebStatsWebMapViewHolder extends WebStatsViewHolder{
        public SteamWebStatsWebMapViewHolder(View itemView){
            super(itemView);
        }
    }
}
