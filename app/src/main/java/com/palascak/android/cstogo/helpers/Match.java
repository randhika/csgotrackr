package com.palascak.android.cstogo.helpers;

import com.palascak.android.cstogo.R;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * - Yuro - 20.3.2015.
 */
public class Match implements Serializable{

    private int teamRounds;
    private int enemyRounds;

    private int kills;
    private int assists;
    private int deaths;
    private int mvps;
    private int score;
    private BigDecimal kd;
    private BigDecimal kad;
    private String map;
    private int drawable;
    private int dominantColor;


    public Match(int teamRounds, int enemyRounds, int kills, int assists, int deaths, int mvps, int score, String map) {
        this.teamRounds = teamRounds;
        this.enemyRounds = enemyRounds;
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.mvps = mvps;
        this.score = score;
        this.map = map;

        BigDecimal bKills = new BigDecimal(kills);
        BigDecimal bDeaths = new BigDecimal(deaths);
        BigDecimal bAssists = new BigDecimal(assists);

        try {
            this.kd = bKills.divide(bDeaths, 2, RoundingMode.HALF_UP);
        } catch (ArithmeticException aex) {
            this.kd = bKills;
        }

        try {
            this.kad = (bKills.add(bAssists)).divide(bDeaths, 2, RoundingMode.HALF_UP);
        } catch (ArithmeticException aex) {
            this.kad = bKills.add(bAssists);
        }

        this.drawable = calculateDrawable();
        this.dominantColor = calculateDominantColor();
    }

    private int calculateDominantColor() {
        switch (getMap()) {
            case "de_nuke":
                return R.color.de_nuke;
            case "de_dust2":
                return R.color.de_dust2;
            case "de_inferno":
                return R.color.de_inferno;
            case "de_cache":
                return R.color.de_cache;
            case "de_cbble":
                return R.color.de_cbble;
            case "de_overpass":
                return R.color.de_overpass;
            case "de_season":
                return R.color.de_season;
            case "de_mirage":
                return R.color.de_mirage;
            case "de_train":
                return R.color.de_train;
            default:
                return R.color.de_dust2;
        }
    }

    private int calculateDrawable() {
        switch (getMap()) {
            case "de_nuke":
                return R.drawable.de_nuke;
            case "de_dust2":
                return R.drawable.de_dust2;
            case "de_inferno":
                return R.drawable.de_inferno;
            case "de_cache":
                return R.drawable.de_cache;
            case "de_cbble":
                return R.drawable.de_cbble;
            case "de_overpass":
                return R.drawable.de_overpass;
            case "de_season":
                return R.drawable.de_season;
            case "de_mirage":
                return R.drawable.de_mirage;
            case "de_train":
                return R.drawable.de_train;
            default:
                return R.drawable.de_dust2;
        }
    }


    public int getTeamRounds() {
        return teamRounds;
    }

    public int getEnemyRounds() {
        return enemyRounds;
    }

    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getMvps() {
        return mvps;
    }

    public int getScore() {
        return score;
    }

    public BigDecimal getKd() {
        return kd;
    }

    public BigDecimal getKad() {
        return kad;
    }

    public String getMatchResult(){
        int team = getTeamRounds();
        int enemy = getEnemyRounds();
        return team + ":" + enemy;
    }

    public String getMap() {
        return map;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getDominantColor() {
        return dominantColor;
    }
}
