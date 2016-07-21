package com.droid.ray.driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.ray.driver.R;
import com.droid.treasures.widget.VerticalIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerticalFragment extends Fragment {


    private VerticalIndicatorView mIndicatorView;
    private ItemFragment mItemFragment;

    private String[] mTitles;

    private List<String> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vertical, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIndicatorView = (VerticalIndicatorView) view.findViewById(R.id.viv_indicator);

        mTitles = getArguments().getStringArray("titles");

        replaceFragment(0);
        Collections.addAll(items, mTitles);
        mIndicatorView.addItemViews(items);
        mIndicatorView.setOnItemClickListener(new VerticalIndicatorView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                replaceFragment(position);
            }
        });

    }

    private void replaceFragment(int position) {
        mItemFragment = ItemFragment.newInstance(mTitles[position]);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mItemFragment)
                .commit();
    }
}
