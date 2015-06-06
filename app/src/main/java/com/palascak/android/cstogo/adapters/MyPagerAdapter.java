/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.fragments.FragmentA;
import com.palascak.android.cstogo.fragments.FragmentB;
import com.palascak.android.cstogo.fragments.FragmentC;
import com.palascak.android.cstogo.fragments.FragmentD;
import com.palascak.android.cstogo.fragments.FragmentE;
import com.palascak.android.cstogo.fragments.FragmentF;

public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider{

    Context mContext;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "My Matches";
            case 1:
                return "Stats";
            case 2:
                return "All Games";
            case 3:
                return "Smokes";
            case 4:
                return "Twitch";
            case 5:
                return "Results";
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 6;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentA();
            case 1:
                return new FragmentB();
            case 2:
                return new FragmentC();
            case 3:
                return new FragmentD();
            case 4:
                return new FragmentE();
            case 5:
                return new FragmentF();
            default:
                return null;
        }
    }

    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(mContext).inflate(R.layout.ripple, viewGroup, false);
    }
}
