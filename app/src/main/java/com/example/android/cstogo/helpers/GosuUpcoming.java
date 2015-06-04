/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.helpers;

import android.net.Uri;

public class GosuUpcoming {

    private Uri matchUrl;
    private String homeTeam;
    private String awayTeam;
    private String when;
    private Uri pictureUrl;

    public GosuUpcoming(Uri matchUrl, String homeTeam, String awayTeam, String when, Uri pictureUrl) {
        this.matchUrl = matchUrl;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.when = when;
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

    public String getWhen() {
        return when;
    }

    public Uri getPictureUrl() {
        return pictureUrl;
    }
}
