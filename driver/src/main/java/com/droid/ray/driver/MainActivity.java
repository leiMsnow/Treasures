package com.droid.ray.driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.droid.treasures.widget.ViewPagerIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private ViewPagerAdapter mPagerAdapter;

    private static String[] mTitles = new String[]{
            "全部", "酒店", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String title : mTitles) {
            Fragment fragment = ItemFragment.newInstance(title);
            fragments.add(fragment);
        }

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);

    }

}
