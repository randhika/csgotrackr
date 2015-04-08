package com.example.android.cstogo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

/**
 * - Yuro - 18.3.2015.
 */
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
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 4;
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
            default:
                return null;
        }
    }

    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(mContext).inflate(R.layout.ripple, viewGroup, false);
    }
}
