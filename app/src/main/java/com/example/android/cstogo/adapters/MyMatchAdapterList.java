package com.example.android.cstogo.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cstogo.R;
import com.example.android.cstogo.helpers.Match;

import java.util.List;

/**
 * - Yuro - 24.3.2015.
 */
public class MyMatchAdapterList extends RecyclerView.Adapter<MyMatchAdapterList.MatchViewHolder> {

    private Context mContext;
    private List<Match> matchList;

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

        String title = ci.getMap().substring(3,4).toUpperCase();
        matchViewHolder.vCircle.setText(title);

        matchViewHolder.vShapeDrawable.setColor(mContext.getResources().getColor(ci.getDominantColor()));
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.match_list, viewGroup, false);

        return new MatchViewHolder(itemView);
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

        protected TextView vMapName;
        protected TextView vCircle;
        protected GradientDrawable vShapeDrawable;

        public MatchViewHolder(View v) {
            super(v);
            vCircle = (TextView) v.findViewById(R.id.matchListCircle);
            vMapName = (TextView) v.findViewById(R.id.matchListMapName);

            vShapeDrawable = (GradientDrawable) vCircle.getBackground();
        }
    }

}
