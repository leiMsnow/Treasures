package com.droid.treasures.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.droid.treasures.R;

/**
 * Created by zhangleilei on 7/26/16.
 */

public class CouponLayout extends RelativeLayout {

    private float mGap;
    private float mRadius;
    private float mFreeSpace;
    private int mCircleCount;

    private Paint mPaint;

    public CouponLayout(Context context) {
        this(context, null);
    }

    public CouponLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CouponLayout);

        mGap = ta.getInteger(R.styleable.CouponLayout_gap, 12);
        mRadius = ta.getInteger(R.styleable.CouponLayout_radius, 16);

        ta.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (mFreeSpace == 0) {
            mFreeSpace = (h - mGap) % (2 * mRadius + mGap);
        }
        mCircleCount = (int) ((h - mGap) / (2 * mRadius + mGap));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCircleCount; i++) {
            float cy = mGap + mRadius + (mFreeSpace / 2) + ((mGap + mRadius * 2) * i);
            canvas.drawCircle(getWidth(), cy, mRadius, mPaint);
        }
    }
}
