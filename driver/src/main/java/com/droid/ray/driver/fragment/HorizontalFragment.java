package com.droid.ray.driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.ray.driver.R;
import com.droid.ray.driver.adapter.ViewPagerAdapter;
import com.droid.treasures.widget.HorizontalIndicatorView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends Fragment {


    private ViewPager mViewPager;
    private HorizontalIndicatorView mIndicator;
    private ViewPagerAdapter mPagerAdapter;

    private String[] mTitles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_container);
        mIndicator = (HorizontalIndicatorView) view.findViewById(R.id.vp_indicator);

        mTitles = getArguments().getStringArray("titles");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String title : mTitles) {
            Fragment fragment = ItemFragment.newInstance(title);
            fragments.add(fragment);
        }

        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
    }
}
