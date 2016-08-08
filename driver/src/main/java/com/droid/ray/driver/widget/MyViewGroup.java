package com.droid.ray.driver.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangleilei on 8/1/16.
 */

public class MyViewGroup extends ViewGroup {

    private int mWidth;
    private int mHeight;

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            mHeight += child.getHeight() + child.getPaddingTop() + child.getPaddingBottom()
                    + layoutParams.leftMargin + layoutParams.rightMargin;
            mWidth = Math.max(mWidth, child.getWidth());
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(), p);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            if (child.getVisibility() == GONE)
                continue;

            int left = layoutParams.leftMargin;
            int top = height + layoutParams.topMargin;
            int right = childWidth + layoutParams.rightMargin;
            int bottom = height + childHeight + layoutParams.bottomMargin;

            child.layout(left, top, right, bottom);
            height += childHeight;
        }
    }
}
