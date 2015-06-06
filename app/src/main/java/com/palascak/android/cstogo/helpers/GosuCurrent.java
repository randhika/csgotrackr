/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.helpers;

import android.net.Uri;

public class GosuCurrent {

    private Uri matchUrl;
    private String homeTeam;
    private String awayTeam;
    private Uri pictureUrl;

    public GosuCurrent(Uri matchUrl, String homeTeam, String awayTeam, Uri pictureUrl) {
        this.matchUrl = matchUrl;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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

    public Uri getPictureUrl() {
        return pictureUrl;
    }
}
