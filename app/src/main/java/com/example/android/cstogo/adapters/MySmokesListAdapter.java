package com.example.android.cstogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.cstogo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * - Yuro - 7.4.2015.
 */
public class MySmokesListAdapter extends RecyclerView.Adapter<MySmokesListAdapter.SmokesViewHolder> {

    private Context mContext;
    private List<String> smokeMapList;
    OnItemClickListener mItemClickListener;

    public MySmokesListAdapter(Context context, List<String> smokeMapList){
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
        //todo: remove this switch
        switch (ci) {
            case "de_nuke":
                Picasso.with(mContext).load(R.drawable.de_nuke).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_dust2":
                Picasso.with(mContext).load(R.drawable.de_dust2).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_inferno":
                Picasso.with(mContext).load(R.drawable.de_inferno).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_cache":
                Picasso.with(mContext).load(R.drawable.de_cache).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_cbble":
                Picasso.with(mContext).load(R.drawable.de_cbble).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_overpass":
                Picasso.with(mContext).load(R.drawable.de_overpass).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_season":
                Picasso.with(mContext).load(R.drawable.de_season).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_mirage":
                Picasso.with(mContext).load(R.drawable.de_mirage).noFade().into(smokesViewHolder.vRowImage);
                break;
            case "de_train":
                Picasso.with(mContext).load(R.drawable.de_train).noFade().into(smokesViewHolder.vRowImage);
                break;
            default:
                Picasso.with(mContext).load(R.drawable.de_dust2).noFade().into(smokesViewHolder.vRowImage);
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

    public class SmokesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView vRowName;
        protected CircleImageView vRowImage;
        protected RelativeLayout vRowLayout;
        protected Context context;


        public SmokesViewHolder(View v) {
            super(v);
            context = v.getContext();
            v.setClickable(true);
            v.setOnClickListener(this);
            vRowName =  (TextView) v.findViewById(R.id.rowMapName);
            vRowImage = (CircleImageView) v.findViewById(R.id.rowImage);
            vRowLayout = (RelativeLayout) v.findViewById(R.id.rowRelLayout);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}