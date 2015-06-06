/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.helpers;

import android.net.Uri;

public class TwitchStream {

    private String twitchViewers;
    private String twitchPreviewUrl;
    private String twitchTitle;
    private String twitchName;
    private Uri twitchURL;

    public TwitchStream(String twitchViewers, String twitchPreviewUrl, String twitchTitle, String twitchName, Uri twitchURL) {
        this.twitchViewers = twitchViewers;
        this.twitchPreviewUrl = twitchPreviewUrl;
        this.twitchTitle = twitchTitle;
        this.twitchName = twitchName;
        this.twitchURL = twitchURL;
    }

    public String getTwitchViewers() {
        return twitchViewers;
    }

    public String getTwitchPreviewUrl() {
        return twitchPreviewUrl;
    }

    public String getTwitchTitle() {
        return twitchTitle;
    }

    public String getTwitchName() {
        return twitchName;
    }

    public Uri getTwitchURL() {
        return twitchURL;
    }
}
