package com.example.android.cstogo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * - Yuro - 10.4.2015.
 */
public class MySmokesGridAdapter extends RecyclerView.Adapter<MySmokesGridAdapter.SmokeViewHolder> {

    private Context mContext;
    private List<Smoke> mSmokeList;
    OnItemClickListener mItemClickListener;

    public MySmokesGridAdapter(Context context, List<Smoke> smokeList) {
        this.mContext = context;
        this.mSmokeList = smokeList;
    }

    @Override
    public SmokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                //inflate(R.layout.row, parent, false);
                inflate(R.layout.square_smoke_th, parent, false);

        return new SmokeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmokeViewHolder holder, int position) {
        Smoke smokeItem = mSmokeList.get(position);

        holder.title.setText(smokeItem.getMapId());

        Picasso.with(mContext).load(R.drawable.de_mirage).fit().centerCrop().into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mSmokeList.size();
    }


    public class SmokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView thumbnail;
        protected TextView title;

        public SmokeViewHolder(View v){
            super(v);
            //thumbnail = (ImageView) v.findViewById(R.id.rowImage);
            thumbnail = (ImageView) v.findViewById(R.id.square_thumb);
            //title = (TextView) v.findViewById(R.id.rowMapName);
            title = (TextView) v.findViewById(R.id.square_title);
            v.setClickable(true);
            v.setOnClickListener(this);
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
