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
    private List<String> steamWebOverall;
    private List<String> steamWebWebMap;
    private List<String> steamWebWebGun;
    private List<String> steamWebOther;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_OVERALL_STATS = 1;
    private static final int TYPE_WEBMAP = 2;
    private static final int TYPE_WEBGUN = 3;
    private static final int TYPE_OTHER = 4;
    private static final int TYPE_FOOTER = 5;

    public MySteamWebStatsAdapter(Context context,
                                  List<String> steamWebHeader,
                                  List<String> steamWebOverall,
                                  List<String> steamWebWebMap,
                                  List<String> steamWebWebGun,
                                  List<String> steamWebOther) {
        this.mContext = context;
        this.steamWebHeader = steamWebHeader;
        this.steamWebOverall = steamWebOverall;
        this.steamWebWebMap = steamWebWebMap;
        this.steamWebWebGun = steamWebWebGun;
        this.steamWebOther = steamWebOther;
    }

    @Override
    public int getItemCount() {
        if (steamWebHeader.size() == 0 ||
                steamWebOverall.size() == 0 ||
                steamWebWebMap.size() == 0 ||
                steamWebWebGun.size() == 0 ||
                steamWebOther.size() == 0){
            return 0;
        } else {
            return 6;
        }
    }

    @Override
    public void onBindViewHolder(MySteamWebStatsAdapter.WebStatsViewHolder viewHolder, int i) {

        switch (getItemViewType(i)){
            case TYPE_HEADER:
                SteamWebStatsHeaderViewHolder headerHolder = (SteamWebStatsHeaderViewHolder) viewHolder;
                headerHolder.vHeaderNickname.setText(steamWebHeader.get(0));
                Picasso.with(mContext).load(steamWebHeader.get(1)).transform(new CircleTransform()).fit().centerCrop().into(headerHolder.vHeaderAvatar);
                int onlineStatus = Integer.valueOf(steamWebHeader.get(2));
                if (onlineStatus == 0){
                    headerHolder.vHeaderStatus.setText("Offline");
                } else {
                    headerHolder.vHeaderStatus.setText("Online");
                }
                break;
            case TYPE_OVERALL_STATS:
                SteamWebStatsOverallViewHolder overallHolder = (SteamWebStatsOverallViewHolder) viewHolder;
                overallHolder.vOverallKD.setText(steamWebOverall.get(0));
                overallHolder.vOverallKills.setText(steamWebOverall.get(1));
                overallHolder.vOverallWinPerc.setText(steamWebOverall.get(2));
                overallHolder.vOverallHeadshotPerc.setText(steamWebOverall.get(3));
                overallHolder.vOverallAccuracy.setText(steamWebOverall.get(4));
                break;
            case TYPE_WEBMAP:
                SteamWebStatsWebMapViewHolder webMapHolder = (SteamWebStatsWebMapViewHolder) viewHolder;
                webMapHolder.vWebMapWinPercName.setText(steamWebWebMap.get(0));
                webMapHolder.vWebMapWinPercNumber.setText(steamWebWebMap.get(1));
                int winPercResource = Integer.parseInt(steamWebWebMap.get(2));
                Picasso.with(mContext).load(winPercResource).transform(new CircleTransform()).fit().centerCrop().into(webMapHolder.vWebMapWinPercPicture);

                webMapHolder.vWebMapWinsName.setText(steamWebWebMap.get(3));
                webMapHolder.vWebMapWinsNumber.setText(steamWebWebMap.get(4));
                int winsResource = Integer.parseInt(steamWebWebMap.get(5));
                Picasso.with(mContext).load(winsResource).transform(new CircleTransform()).fit().centerCrop().into(webMapHolder.vWebMapWinsPicture);

                webMapHolder.vWebMapRoundsName.setText(steamWebWebMap.get(6));
                webMapHolder.vWebMapRoundsNumber.setText(steamWebWebMap.get(7));
                int roundsResource = Integer.parseInt(steamWebWebMap.get(8));
                Picasso.with(mContext).load(roundsResource).transform(new CircleTransform()).fit().centerCrop().into(webMapHolder.vWebMapRoundsPicture);

                break;
            case TYPE_WEBGUN:
                SteamWebStatsWebGunViewHolder webGunHolder = (SteamWebStatsWebGunViewHolder) viewHolder;
                webGunHolder.vWebGunShotsName.setText(steamWebWebGun.get(0));
                webGunHolder.vWebGunShotsNumber.setText(steamWebWebGun.get(1));
                webGunHolder.vWebGunHitsName.setText(steamWebWebGun.get(2));
                webGunHolder.vWebGunHitsNumber.setText(steamWebWebGun.get(3));
                webGunHolder.vWebGunKillsName.setText(steamWebWebGun.get(4));
                webGunHolder.vWebGunKillsNumber.setText(steamWebWebGun.get(5));
                webGunHolder.vWebGunAccuracyName.setText(steamWebWebGun.get(6));
                webGunHolder.vWebGunAccuracyNumber.setText(steamWebWebGun.get(7));
                break;
            case TYPE_OTHER:
                SteamWebStatsOtherViewHolder otherHolder = (SteamWebStatsOtherViewHolder) viewHolder;

                otherHolder.vOtherKills.setText(steamWebOther.get(0));
                otherHolder.vOtherHeadshots.setText(steamWebOther.get(1));
                otherHolder.vOtherDeaths.setText(steamWebOther.get(2));
                otherHolder.vOtherWins.setText(steamWebOther.get(3));
                otherHolder.vOtherRounds.setText(steamWebOther.get(4));
                otherHolder.vOtherPlanted.setText(steamWebOther.get(5));
                otherHolder.vOtherDefused.setText(steamWebOther.get(6));
                otherHolder.vOtherDamage.setText(steamWebOther.get(7));
                otherHolder.vOtherMoney.setText(steamWebOther.get(8));
                otherHolder.vOtherHostages.setText(steamWebOther.get(9));
                otherHolder.vOtherMvps.setText(steamWebOther.get(10));
                otherHolder.vOtherEnemyWeaponKills.setText(steamWebOther.get(11));

                otherHolder.vOtherShotsFired.setText(steamWebOther.get(12));
                otherHolder.vOtherShotsHit.setText(steamWebOther.get(13));
                otherHolder.vOtherZeusShots.setText(steamWebOther.get(14));
                otherHolder.vOtherZeusKills.setText(steamWebOther.get(15));
                otherHolder.vOtherMolotovKills.setText(steamWebOther.get(16));
                otherHolder.vOtherNadeKills.setText(steamWebOther.get(17));
                otherHolder.vOtherKnifeKills.setText(steamWebOther.get(18));
                otherHolder.vOtherFlashKills.setText(steamWebOther.get(19));
                otherHolder.vOtherZoomedEnemyKills.setText(steamWebOther.get(20));
                otherHolder.vOtherDominations.setText(steamWebOther.get(21));
                otherHolder.vOtherRevenges.setText(steamWebOther.get(22));
                otherHolder.vOtherDonations.setText(steamWebOther.get(23));
                otherHolder.vOtherBrokenWindows.setText(steamWebOther.get(24));
                break;
            case TYPE_FOOTER:
                SteamWebStatsFooterViewHolder footerHolder = (SteamWebStatsFooterViewHolder) viewHolder;
                footerHolder.vFooterLastUpdate.setText("Last update : " + steamWebHeader.get(3));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_OVERALL_STATS;
            case 2:
                return TYPE_WEBMAP;
            case 3:
                return TYPE_WEBGUN;
            case 4:
                return TYPE_OTHER;
            case 5:
                return TYPE_FOOTER;
            default:
                return 5;
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

                holder = new SteamWebStatsHeaderViewHolder(v);
                break;
            case TYPE_OVERALL_STATS:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_overall, viewGroup, false);

                holder = new SteamWebStatsOverallViewHolder(v);
                break;
            case TYPE_WEBMAP:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_webmap, viewGroup, false);
                holder = new SteamWebStatsWebMapViewHolder(v);
                break;
            case TYPE_WEBGUN:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_webgun, viewGroup, false);
                holder = new SteamWebStatsWebGunViewHolder(v);
                break;
            case TYPE_OTHER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_other, viewGroup, false);
                holder = new SteamWebStatsOtherViewHolder(v);
                break;
            case TYPE_FOOTER:
                v = LayoutInflater.from(context)
                        .inflate(R.layout.steam_web_stats_footer, viewGroup, false);
                holder = new SteamWebStatsFooterViewHolder(v);
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

    public class SteamWebStatsOverallViewHolder extends WebStatsViewHolder {

        protected TextView vOverallKD;
        protected TextView vOverallKills;
        protected TextView vOverallWinPerc;
        protected TextView vOverallHeadshotPerc;
        protected TextView vOverallAccuracy;

        public SteamWebStatsOverallViewHolder(View itemView) {
            super(itemView);
            vOverallKD = (TextView) itemView.findViewById(R.id.steam_web_stats_overall_kd);
            vOverallKills = (TextView) itemView.findViewById(R.id.steam_web_stats_overall_total_kills_number);
            vOverallWinPerc = (TextView) itemView.findViewById(R.id.steam_web_stats_overall_win_percent_number);
            vOverallHeadshotPerc = (TextView) itemView.findViewById(R.id.steam_web_stats_overall_headshot_number);
            vOverallAccuracy = (TextView) itemView.findViewById(R.id.steam_web_stats_overall_accuracy_number);

        }
    }

    public class SteamWebStatsWebMapViewHolder extends WebStatsViewHolder {

        protected TextView vWebMapWinPercName;
        protected TextView vWebMapWinPercNumber;
        protected ImageView vWebMapWinPercPicture;

        protected TextView vWebMapWinsName;
        protected TextView vWebMapWinsNumber;
        protected ImageView vWebMapWinsPicture;

        protected TextView vWebMapRoundsName;
        protected TextView vWebMapRoundsNumber;
        protected ImageView vWebMapRoundsPicture;

        public SteamWebStatsWebMapViewHolder(View itemView) {
            super(itemView);
            vWebMapWinPercName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_win_perc_map);
            vWebMapWinPercNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_win_perc_number);
            vWebMapWinPercPicture = (ImageView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_win_perc_picture);

            vWebMapWinsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_wins_map);
            vWebMapWinsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_wins_number);
            vWebMapWinsPicture = (ImageView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_wins_picture);

            vWebMapRoundsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_rounds_map);
            vWebMapRoundsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_rounds_number);
            vWebMapRoundsPicture = (ImageView) itemView.findViewById(R.id.steam_web_stats_webmap_highest_rounds_picture);
        }
    }

    public class SteamWebStatsWebGunViewHolder extends WebStatsViewHolder {

        protected TextView vWebGunShotsName;
        protected TextView vWebGunShotsNumber;
        protected TextView vWebGunHitsName;
        protected TextView vWebGunHitsNumber;
        protected TextView vWebGunKillsName;
        protected TextView vWebGunKillsNumber;
        protected TextView vWebGunAccuracyName;
        protected TextView vWebGunAccuracyNumber;

        public SteamWebStatsWebGunViewHolder(View itemView) {
            super(itemView);
            vWebGunShotsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_shots_name);
            vWebGunShotsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_shots_number);
            vWebGunHitsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_hits_name);
            vWebGunHitsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_hits_number);
            vWebGunKillsName = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_kills_name);
            vWebGunKillsNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_kills_number);
            vWebGunAccuracyName = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_accuracy_name);
            vWebGunAccuracyNumber = (TextView) itemView.findViewById(R.id.steam_web_stats_webgun_accuracy_number);
        }
    }

    public class SteamWebStatsOtherViewHolder extends WebStatsViewHolder {

        protected TextView vOtherKills;
        protected TextView vOtherHeadshots;
        protected TextView vOtherDeaths;
        protected TextView vOtherWins;
        protected TextView vOtherRounds;
        protected TextView vOtherPlanted;
        protected TextView vOtherDefused;
        protected TextView vOtherDamage;
        protected TextView vOtherMoney;
        protected TextView vOtherHostages;
        protected TextView vOtherMvps;
        protected TextView vOtherEnemyWeaponKills;

        protected TextView vOtherShotsFired;
        protected TextView vOtherShotsHit;
        protected TextView vOtherZeusShots;
        protected TextView vOtherZeusKills;
        protected TextView vOtherMolotovKills;
        protected TextView vOtherNadeKills;
        protected TextView vOtherKnifeKills;
        protected TextView vOtherFlashKills;
        protected TextView vOtherZoomedEnemyKills;
        protected TextView vOtherDominations;
        protected TextView vOtherRevenges;
        protected TextView vOtherDonations;
        protected TextView vOtherBrokenWindows;

        public SteamWebStatsOtherViewHolder(View itemView){
            super(itemView);
            vOtherKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_kills);
            vOtherHeadshots = (TextView) itemView.findViewById(R.id.steam_web_stats_other_headshots);
            vOtherDeaths = (TextView) itemView.findViewById(R.id.steam_web_stats_other_deaths);
            vOtherWins = (TextView) itemView.findViewById(R.id.steam_web_stats_other_wins);
            vOtherRounds = (TextView) itemView.findViewById(R.id.steam_web_stats_other_rounds);
            vOtherPlanted = (TextView) itemView.findViewById(R.id.steam_web_stats_other_planted);
            vOtherDefused = (TextView) itemView.findViewById(R.id.steam_web_stats_other_defused);
            vOtherDamage = (TextView) itemView.findViewById(R.id.steam_web_stats_other_damage_done);
            vOtherMoney = (TextView) itemView.findViewById(R.id.steam_web_stats_other_money_earned);
            vOtherHostages = (TextView) itemView.findViewById(R.id.steam_web_stats_other_hostages_rescued);
            vOtherMvps = (TextView) itemView.findViewById(R.id.steam_web_stats_other_mvps);
            vOtherEnemyWeaponKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_enemy_weapon);

            vOtherShotsFired = (TextView) itemView.findViewById(R.id.steam_web_stats_other_shots_fired);
            vOtherShotsHit = (TextView) itemView.findViewById(R.id.steam_web_stats_other_shots_hit);
            vOtherZeusShots = (TextView) itemView.findViewById(R.id.steam_web_stats_other_zeus_shots);
            vOtherZeusKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_zeus_kills);
            vOtherMolotovKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_molotov);
            vOtherNadeKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_nade);
            vOtherKnifeKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_knife);
            vOtherFlashKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_flash);
            vOtherZoomedEnemyKills = (TextView) itemView.findViewById(R.id.steam_web_stats_other_zoomed);
            vOtherDominations = (TextView) itemView.findViewById(R.id.steam_web_stats_other_dominations);
            vOtherRevenges = (TextView) itemView.findViewById(R.id.steam_web_stats_other_revenges);
            vOtherDonations = (TextView) itemView.findViewById(R.id.steam_web_stats_other_donated);
            vOtherBrokenWindows = (TextView) itemView.findViewById(R.id.steam_web_stats_other_broken_windows);
        }

    }

    public class SteamWebStatsFooterViewHolder extends WebStatsViewHolder {

        protected TextView vFooterLastUpdate;

        public SteamWebStatsFooterViewHolder(View itemView) {
            super(itemView);
            vFooterLastUpdate = (TextView) itemView.findViewById(R.id.steam_web_stats_footer_last_update);
        }
    }
}
