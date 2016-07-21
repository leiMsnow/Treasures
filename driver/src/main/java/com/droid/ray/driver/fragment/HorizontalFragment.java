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
import com.droid.treasures.widget.ViewPagerIndicator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends Fragment {


    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private ViewPagerAdapter mPagerAdapter;

    private static String[] mTitles = new String[]{
            "全部", "酒店", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他"
    };


    public HorizontalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_container);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.vp_indicator);

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
