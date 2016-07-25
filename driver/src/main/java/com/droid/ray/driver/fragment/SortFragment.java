package com.droid.ray.driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.ray.driver.R;
import com.droid.treasures.widget.SortGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends Fragment {


    private List<SortGroup.Item> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vertical, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SortGroup mIndicatorView = (SortGroup) view.findViewById(R.id.viv_indicator);
        List<SortGroup.Item> items = new ArrayList<>();
        items.add(new SortGroup.Item("默认").setAsc("default_0").create());
        items.add(new SortGroup.Item("销量").setAsc("sales_0").setDesc("sales_1").create());
        items.add(new SortGroup.Item("价格").setAsc("price_0").setDesc("price_1").create());
        items.add(new SortGroup.Item("好评").setAsc("score_1").create());
        items.add(new SortGroup.Item("时间").setAsc("time_0").setDesc("time_1").create());
        mIndicatorView.addItems(items);
        mIndicatorView.setOnItemClickListener(new SortGroup.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String orderBy) {
                replaceFragment(orderBy);
            }
        });

        replaceFragment(items.get(0).getTitle());

    }

    private void replaceFragment(String orderBy) {
        ItemFragment mItemFragment = ItemFragment.newInstance(orderBy);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mItemFragment)
                .commit();
    }
}
