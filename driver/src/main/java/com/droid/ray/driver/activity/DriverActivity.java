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
import com.droid.ray.driver.fragment.HorizontalFragment;

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

    private void addTestData() {
        mList = new ArrayList<>();
        mList.add(HorizontalFragment.class.getName());
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
                    Intent intent = new Intent(mContext,ShowActivity.class);
                    intent.putExtra("fragment",v.getTag().toString());
                    startActivity(intent);
                    break;
            }
        }
    }

}
