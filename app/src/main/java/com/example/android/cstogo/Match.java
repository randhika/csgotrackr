package com.example.android.cstogo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * - Yuro - 20.3.2015.
 */
public class Match {

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

        BigDecimal bKills = new BigDecimal(Integer.toString(kills));
        BigDecimal bDeaths = new BigDecimal(Integer.toString(deaths));
        BigDecimal bAssists = new BigDecimal(Integer.toString(assists));

        this.kd = bKills.divide(bDeaths, 2, RoundingMode.HALF_UP);
        this.kad = (bKills.add(bAssists)).divide(bDeaths, 2, RoundingMode.HALF_UP);
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
