package com.droid.ray.driver.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static com.droid.ray.driver.activity.ShowActivity.ARG_TITLE;

/**
 *
 * Created by zhangleilei on 7/19/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getArguments().getString(ARG_TITLE);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
