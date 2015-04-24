package com.example.android.cstogo.helpers;

import java.util.ArrayList;

/**
 * - Yuro - 24.3.2015.
 */
public class MatchList {

    public ArrayList<Match> matchList;

    private MatchList(){
        matchList = new ArrayList<>();
    }

    private static MatchList instance;

    public static MatchList getInstance(){
        if (instance == null) instance = new MatchList();
        return instance;
    }
}
