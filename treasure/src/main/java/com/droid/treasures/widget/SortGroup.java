package com.droid.treasures.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.droid.treasures.R;

import java.util.List;

/**
 * 排序组件
 * Created by zhangleilei on 7/22/16.
 */
public class SortGroup extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;

    private int mItemCount = 0;
    private int mDefaultColor = Color.DKGRAY;
    private int mSelectedColor = Color.RED;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public SortGroup(Context context) {
        this(context, null);
    }

    public SortGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
    }

    public void addItems(List<Item> items) {

        removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        mItemCount = items.size();
        for (int i = 0; i < mItemCount; i++) {

            final int position = i;
            final Item item = items.get(i);
            final SortButton childView = new SortButton(getContext());
            childView.setTag(item);
            childView.setText(item.title);
            childView.setGravity(Gravity.CENTER);
            childView.setTextColor(mDefaultColor);
            setDrawableRes(childView, R.mipmap.arrow_price_normal);
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((!item.isAsc) && (!item.canSort))
                        return;

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, item.getOrderBy());
                    }

                    setSelectedItem(position);
                }
            });
            addView(childView, layoutParams);
        }
        setSelectedItem(0);
    }

    private void setDrawableRes(SortButton childView, int resId) {
        Item item = (Item) childView.getTag();
        if (item.canSort) {
            childView.setImageResource(resId);
        }
    }

    private void setSelectedItem(int position) {
        for (int i = 0; i < mItemCount; i++) {
            SortButton child = ((SortButton) getChildAt(i));
            Item item = (Item) child.getTag();
            child.setTextColor(mDefaultColor);
            setDrawableRes(child, R.mipmap.arrow_price_normal);
            if (i == position) {
                child.setTextColor(mSelectedColor);
                setDrawableRes(child, item.isAsc ? R.mipmap.arrow_price_up : R.mipmap.arrow_price_down);
                item.isAsc = !item.isAsc;
            } else {
                item.isAsc = true;
            }
        }
    }

    public static class Item {

        private String title;
        private String asc;
        private String desc;
        private boolean isAsc = true;
        private boolean canSort = false;


        public Item setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getTitle() {
            return title;
        }


        public Item setAsc(String asc) {
            this.asc = asc;
            return this;
        }

        public Item setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Item create() {
            if ((!TextUtils.isEmpty(asc)) && (!TextUtils.isEmpty(desc))) {
                canSort = true;
            }
            return this;
        }

        public Item(String title) {
            this.title = title;
        }

        public String getOrderBy() {
            return isAsc ? asc : desc;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String orderBy);
    }

}
