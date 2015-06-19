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

    }

    public int calculateDominantColor() {
        switch (getMap()) {
            case "de_dust2":
                return R.color.de_dust2;
            case "de_train":
                return R.color.de_train;
            case "de_mirage":
                return R.color.de_mirage;
            case "de_inferno":
                return R.color.de_inferno;
            case "de_cbble":
                return R.color.de_cbble;
            case "de_overpass":
                return R.color.de_overpass;
            case "de_cache":
                return R.color.de_cache;
            case "de_rails":
                return R.color.de_rails;
            case "de_resort":
                return R.color.de_resort;
            case "de_zoo":
                return R.color.de_zoo;
            case "de_log":
                return R.color.de_log;
            case "de_season":
                return R.color.de_season;
            case "cs_agency":
                return R.color.cs_agency;
            case "de_aztec":
                return R.color.de_aztec;
            case "de_dust":
                return R.color.de_dust;
            case "de_vertigo":
                return R.color.de_vertigo;
            case "de_nuke":
                return R.color.de_nuke;
            case "cs_office":
                return R.color.cs_office;
            case "cs_italy":
                return R.color.cs_italy;
            case "cs_assault":
                return R.color.cs_assault;
            case "cs_militia":
                return R.color.cs_militia;
            default:
                return R.color.de_dust2;
        }
    }

    public int calculateDrawable() {
        switch (getMap()) {
            case "de_dust2":
                return R.drawable.de_dust2;
            case "de_train":
                return R.drawable.de_train;
            case "de_mirage":
                return R.drawable.de_mirage;
            case "de_inferno":
                return R.drawable.de_inferno;
            case "de_cbble":
                return R.drawable.de_cbble;
            case "de_overpass":
                return R.drawable.de_overpass;
            case "de_cache":
                return R.drawable.de_cache;
            case "de_rails":
                return R.drawable.de_rails;
            case "de_resort":
                return R.drawable.de_resort;
            case "de_zoo":
                return R.drawable.de_zoo;
            case "de_log":
                return R.drawable.de_log;
            case "de_season":
                return R.drawable.de_season;
            case "cs_agency":
                return R.drawable.cs_agency;
            case "de_aztec":
                return R.drawable.de_aztec;
            case "de_dust":
                return R.drawable.de_dust;
            case "de_vertigo":
                return R.drawable.de_vertigo;
            case "de_nuke":
                return R.drawable.de_nuke;
            case "cs_office":
                return R.drawable.cs_office;
            case "cs_italy":
                return R.drawable.cs_italy;
            case "cs_assault":
                return R.drawable.cs_assault;
            case "cs_militia":
                return R.drawable.cs_militia;
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
}
