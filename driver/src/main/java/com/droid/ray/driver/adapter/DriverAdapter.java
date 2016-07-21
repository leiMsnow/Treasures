package com.droid.ray.driver.adapter;

import android.content.Context;
import android.view.View;

import com.droid.ray.driver.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class DriverAdapter extends SuperAdapter<String> {

    private View.OnClickListener mOnClickListener;

    public DriverAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onBind(SuperViewHolder helper, int viewType, int layoutPosition, String item) {
        helper.setTag(R.id.rl_item, item);
        helper.setOnClickListener(R.id.rl_item, mOnClickListener);
        helper.setText(R.id.tv_text, item);
    }

}