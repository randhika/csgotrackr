package com.example.android.cstogo.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * - Yuro - 5.4.2015.
 */
public class WebGun {

    private String gunName;
    private int drawableResource;
    private int shots;
    private int hits;
    private int kills;

    public WebGun(String gunName, int drawableResource) {
        this.gunName = gunName;
        this.drawableResource = drawableResource;
    }

    public String getGunName() {
        return gunName;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public float getAccuracy() {
        BigDecimal shots = new BigDecimal(getShots());
        BigDecimal hits = new BigDecimal(getHits());
        try {
            BigDecimal acc = hits.divide(shots.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), 1, RoundingMode.HALF_UP);
            return acc.floatValue();
        } catch (java.lang.ArithmeticException e) {
            return 0;
        }
    }
}
