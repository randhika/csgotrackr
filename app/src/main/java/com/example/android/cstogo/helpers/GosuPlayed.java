/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.helpers;

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
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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
