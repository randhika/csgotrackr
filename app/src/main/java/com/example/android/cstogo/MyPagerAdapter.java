package com.example.android.cstogo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * - Yuro - 18.3.2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
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
            default:
                return null;
        }
        //return (position == 0)? "My Matches" : "Stats" ;
    }
    @Override
    public int getCount() {
        return 3;
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
            default:
                return null;
        }
        //return (position == 0)? new FragmentA() : new FragmentB() ;
    }
}
