/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.helpers;

public class TwitchStream {

    private String twitchViewers;
    private String twitchPreviewUrl;
    private String twitchTitle;
    private String twitchName;
    private String twitchURL;

    public TwitchStream(String twitchViewers, String twitchPreviewUrl, String twitchTitle, String twitchName, String twitchURL) {
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

    public String getTwitchURL() {
        return twitchURL;
    }
}
