/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.helpers;

import android.net.Uri;

public class GosuPlayed {

    private Uri matchUrl;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private Uri pictureUrl;

    public GosuPlayed(Uri matchUrl, String homeTeam, String awayTeam, int homeScore, int awayScore, Uri pictureUrl) {
        this.matchUrl = matchUrl;

        switch (homeTeam){
            case "Ninjas in...":
                this.homeTeam = "Ninjas in Pyjamas";
                break;
            case "SapphireKelow...":
                this.homeTeam = "SapphireKelownaDotCom";
                break;
            case "Luminosity...":
                this.homeTeam = "Luminosity Gaming";
                break;
            case "Key-Preisverg...":
                this.homeTeam = "Key-Preisvergleich.de";
                break;
            case "Counter...":
                this.homeTeam = "Counter Logic Gaming.CS";
                break;
            default:
                this.homeTeam = homeTeam;
                break;
        }

        switch (awayTeam){
            case "Ninjas in...":
                this.awayTeam = "Ninjas in Pyjamas";
                break;
            case "SapphireKelow...":
                this.awayTeam = "SapphireKelownaDotCom";
                break;
            case "Luminosity...":
                this.awayTeam = "Luminosity Gaming";
                break;
            case "Key-Preisverg...":
                this.awayTeam = "Key-Preisvergleich.de";
                break;
            case "Counter...":
                this.awayTeam = "Counter Logic Gaming.CS";
                break;
            default:
                this.awayTeam = awayTeam;
                break;
        }

        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.pictureUrl = pictureUrl;
    }

    public Uri getMatchUrl() {
        return matchUrl;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public Uri getPictureUrl() {
        return pictureUrl;
    }
}
