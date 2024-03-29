package com.palascak.android.cstogo.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * - Yuro - 4.4.2015.
 */
public class WebMap {

    private String webMapName;
    private int rounds;
    private int wins;
    private int drawable;

    public WebMap(String webMapName, int drawable) {
        this.webMapName = webMapName;
        this.drawable = drawable;
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
        try {
            BigDecimal bdWinPerc = bdWins.divide(bdRounds.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
            return bdWinPerc.floatValue();
        } catch (java.lang.ArithmeticException e){
            return 0;
        }

    }

    public int getDrawable(){
        return drawable;
    }
}
