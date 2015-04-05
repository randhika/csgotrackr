package com.example.android.cstogo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * - Yuro - 4.4.2015.
 */
public class WebMap {

    private String webMapName;
    private int rounds;
    private int wins;

    public WebMap(String webMapName) {
        this.webMapName = webMapName;
    }

    public String getWebMapName() {
        return webMapName;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public float getWinPerc() {
        BigDecimal bdRounds = new BigDecimal(getRounds());
        BigDecimal bdWins = new BigDecimal(getWins());
        BigDecimal bdWinPerc = bdWins.divide(bdRounds.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
        return bdWinPerc.floatValue();
    }
}
