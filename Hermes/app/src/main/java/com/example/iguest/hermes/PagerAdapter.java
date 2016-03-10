package com.example.iguest.hermes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ivy on 3/9/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RequestFeedFragment tab1 = new RequestFeedFragment();
                return tab1;
            case 1:
                MyRequestsFragment tab2 = new MyRequestsFragment();
                return tab2;
            case 2:
                DeliveryFragment tab3 = new DeliveryFragment();
                return tab3;
            case 3:
                LeaderboardFragment tab4 = new LeaderboardFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}