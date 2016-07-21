package com.droid.treasures.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * 纵向滑动tabs
 * Created by zhangleilei on 7/19/16.
 */
public class VerticalIndicatorView extends ScrollView {

    private Context mContext;
    private LinearLayout mContainer;


    public VerticalIndicatorView(Context context) {
        this(context, null);
    }

    public VerticalIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {

        setFillViewport(true);

        mContainer = new LinearLayout(mContext);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(layoutParams);

        addView(mContainer);
    }

    public void setItemData(List items) {
        int count = items.size();
        for (int i = 0; i < count; i++) {

        }

    }

}
