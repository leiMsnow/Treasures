package com.droid.treasures.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droid.treasures.utils.DensityUtils;

/**
 * ViewPager滑动指示器
 * Created by zhangleilei on 7/19/16.
 */
public class ViewPagerIndicator extends HorizontalScrollView {

    private Context mContext;
    private LinearLayout mContainer;

    private ViewPager mViewPager;


    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {

        setMinimumHeight(DensityUtils.dp2px(mContext, 40));

        mContainer = new LinearLayout(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(layoutParams);
        // add container layout
        addView(mContainer);
    }


    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;

        if (mViewPager == null) {
            throw new NullPointerException("viewPager is null");
        }

        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("viewPager must set adapter first");
        }

        addItems();
        mViewPager.addOnPageChangeListener(new ViewPagerListener());

    }

    private void addItems() {
        int itemCount = mViewPager.getAdapter().getCount();
        for (int i = 0; i < itemCount; i++) {
            addTextView(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }
    }

    private void addTextView(final int position, String title) {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(14);
        textView.setTextColor(Color.GRAY);
        textView.setText(title);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });
        mContainer.addView(textView);
    }

    private class ViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
