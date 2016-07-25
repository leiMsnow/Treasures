package com.droid.treasures.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 排序按钮
 * Created by zhangleilei on 7/22/16.
 */
public class SortButton extends RelativeLayout {

    private static final int IDS = Integer.MAX_VALUE / 3;

    private TextView mTextView;
    private ImageView mImageView;

    public SortButton(Context context) {
        this(context, null);
    }

    public SortButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutParams textParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTextView = new TextView(getContext());
        mTextView.setId(IDS);
        mTextView.setGravity(Gravity.CENTER);
        textParams.setMargins(0, 0, -8, 0);
        textParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTextView, textParams);

        LayoutParams imageParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView = new ImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
        imageParams.addRule(RelativeLayout.RIGHT_OF, IDS);

        addView(mImageView, imageParams);

    }

    public void setTextColor(int resId) {
        mTextView.setTextColor(resId);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setImageResource(int resId) {
        mImageView.setImageResource(resId);
    }

}
