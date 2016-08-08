package com.droid.ray.driver.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.droid.ray.driver.R;

/**
 * Created by zhangleilei on 8/1/16.
 */

public class MyView extends View {

    private Bitmap mBitmap;
    private Bitmap mNewBitmap;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    private boolean mIsCircle;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttrs(attrs);
        init();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyView);
        mBitmap = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.MyView_src, 0));
        mIsCircle = ta.getBoolean(R.styleable.MyView_circle, false);
        ta.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else if (widthSpecMode == MeasureSpec.AT_MOST ||
                widthMeasureSpec == MeasureSpec.UNSPECIFIED) {
            mWidth = mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST ||
                heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
            mHeight = mBitmap.getHeight() + getPaddingLeft() + getPaddingRight();
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsCircle) {
            mNewBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas(mNewBitmap);
            newCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            newCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
            canvas.drawBitmap(mNewBitmap, 0, 0, null);
        } else {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }
}
