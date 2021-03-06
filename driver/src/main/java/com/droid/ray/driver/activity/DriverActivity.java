package com.droid.ray.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.droid.ray.driver.R;
import com.droid.ray.driver.adapter.DriverAdapter;
import com.droid.ray.driver.fragment.CouponFragment;
import com.droid.ray.driver.fragment.HorizontalFragment;
import com.droid.ray.driver.fragment.MoveFragment;
import com.droid.ray.driver.fragment.SortFragment;
import com.droid.ray.driver.fragment.SpannableFragment;
import com.droid.ray.driver.fragment.StepsFragment;
import com.droid.ray.driver.fragment.VerticalFragment;
import com.droid.treasures.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private DriverAdapter mRecyclerAdapter;
    private Context mContext;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_view);

        addTestData();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addTestData() {
        mList = new ArrayList<>();
        mList.add(HorizontalFragment.class.getName());
        mList.add(VerticalFragment.class.getName());
        mList.add(SortFragment.class.getName());
        mList.add(CouponFragment.class.getName());
        mList.add(SpannableFragment.class.getName());
        mList.add(MoveFragment.class.getName());
        mList.add(StepsFragment.class.getName());
    }

    private void initAdapter() {

        mRecyclerAdapter = new DriverAdapter(mContext, mList, R.layout.item_activity_driver);
        mRecyclerAdapter.setOnClickListener(new RecyclerViewClickImpl());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private class RecyclerViewClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_item:
                    LogUtils.i(this.getClass().getSimpleName(), "fragment:" + v.getTag().toString());
                    Intent intent = new Intent(mContext, ShowActivity.class);
                    intent.putExtra("fragment", v.getTag().toString());
                    startActivity(intent);
                    break;
            }
        }
    }

}
