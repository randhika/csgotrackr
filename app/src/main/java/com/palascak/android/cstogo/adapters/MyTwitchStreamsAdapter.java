/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.helpers.TwitchStream;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyTwitchStreamsAdapter extends RecyclerView.Adapter<MyTwitchStreamsAdapter.StreamViewHolder> {

    private Context mContext;
    private ArrayList<TwitchStream> mStreams;
    OnItemClickListener mItemClickListener;

    public MyTwitchStreamsAdapter(Context context, ArrayList<TwitchStream> streams){
        this.mContext = context;
        this.mStreams = streams;
    }

    @Override
    public StreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.twitch_stream_card, parent, false);

        return new StreamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StreamViewHolder holder, int position) {
        TwitchStream streamItem = mStreams.get(position);

        holder.twitchTitle.setText(streamItem.getTwitchTitle());
        holder.twitchName.setText(streamItem.getTwitchName());
        holder.twitchViewers.setText(streamItem.getTwitchViewers());
        Picasso.with(mContext).load(streamItem.getTwitchPreviewUrl()).fit().centerCrop().into(holder.twitchPicture);

    }

    @Override
    public int getItemCount() {
        return mStreams.size();
    }

    public class StreamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView twitchPicture;
        private TextView twitchTitle;
        private TextView twitchName;
        private TextView twitchViewers;
        private ImageView twitchLink;

        public StreamViewHolder(View v){
            super(v);
            twitchPicture = (ImageView) v.findViewById(R.id.twitch_stream_card_picture);
            twitchTitle = (TextView) v.findViewById(R.id.twitch_stream_card_title);
            twitchName = (TextView) v.findViewById(R.id.twitch_stream_card_name);
            twitchViewers  = (TextView) v.findViewById(R.id.twitch_stream_card_viewers);

            twitchLink = (ImageView) v.findViewById(R.id.twitch_stream_link);

            twitchLink.setClickable(true);
            twitchLink.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
