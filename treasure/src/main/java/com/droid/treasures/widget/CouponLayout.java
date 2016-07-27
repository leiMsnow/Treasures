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

        mGap = ta.getInteger(R.styleable.CouponLayout_gap, 16);
        mCircleCount = ta.getInteger(R.styleable.CouponLayout_circle_count, 6);
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
        mRadius = (h - (mGap * (mCircleCount + 1))) / mCircleCount / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCircleCount; i++) {
            float cy = mGap + mRadius + ((mGap + mRadius * 2) * i);
            canvas.drawCircle(getWidth(), cy, mRadius, mPaint);
        }
    }
}
