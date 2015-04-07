package com.example.android.cstogo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * - Yuro - 7.4.2015.
 */
public class MySmokesAdapter extends RecyclerView.Adapter<MySmokesAdapter.SmokesViewHolder> {

    private Context mContext;
    private List<String> smokeMapList;

    public MySmokesAdapter(Context context, List<String> smokeMapList){
        this.smokeMapList = smokeMapList;
        this.mContext= context;
    }

    @Override
    public int getItemCount() {
        return smokeMapList.size();
    }

    @Override
    public void onBindViewHolder(SmokesViewHolder smokesViewHolder, int i){
        String ci = smokeMapList.get(i);

        smokesViewHolder.vRowName.setText(ci);

        switch (ci) {
            case "de_nuke":
                Picasso.with(mContext).load(R.drawable.nuke_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_dust2":
                Picasso.with(mContext).load(R.drawable.dust2_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_inferno":
                Picasso.with(mContext).load(R.drawable.inferno_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_cache":
                Picasso.with(mContext).load(R.drawable.cache_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_cbble":
                Picasso.with(mContext).load(R.drawable.cbble_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_overpass":
                Picasso.with(mContext).load(R.drawable.overpass_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_season":
                Picasso.with(mContext).load(R.drawable.season_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_mirage":
                Picasso.with(mContext).load(R.drawable.mirage_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
            default:
                Picasso.with(mContext).load(R.drawable.dust2_res).fit().centerCrop().noFade().into(smokesViewHolder.vRowImage);
                break;
        }

    }

    @Override
    public SmokesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row, viewGroup, false);

        return new SmokesViewHolder(itemView);
    }

    public static class SmokesViewHolder extends RecyclerView.ViewHolder {

        protected TextView vRowName;
        protected CircleImageView vRowImage;

        public SmokesViewHolder(View v) {
            super(v);
            vRowName =  (TextView) v.findViewById(R.id.rowMapName);
            vRowImage = (CircleImageView) v.findViewById(R.id.rowImage);
        }
    }

}
