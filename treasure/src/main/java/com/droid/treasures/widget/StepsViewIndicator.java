package com.droid.treasures.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.droid.treasures.R;

import java.util.ArrayList;
import java.util.List;


public class StepsViewIndicator extends View {

    private static final int THUMB_SIZE = 48;

    private Paint paint = new Paint();
    private Paint selectedPaint = new Paint();
    private int mNumOfStep = 2;
    private float mLineHeight;
    private float mThumbRadius;
    private float mCircleRadius;
    private float mPadding;
    private int mProgressColor = Color.YELLOW;
    private int mBarColor = Color.BLACK;

    private float mCenterY;
    private float mLeftX;
    private float mLeftY;
    private float mRightX;
    private float mRightY;
    private float mDelta;
    private List<Float> mThumbContainerXPosition = new ArrayList<>();
    private List<Float> mTextXPosition = new ArrayList<>();
    private int mCompletedPosition;
    private OnDrawListener mDrawListener;

    public StepsViewIndicator(Context context) {
        this(context, null);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.StepsViewIndicator);
        mNumOfStep = a.getInt(R.styleable.StepsViewIndicator_numOfSteps, 0);
        a.recycle();

        init();
    }

    private void init() {
        mLineHeight = 0.2f * THUMB_SIZE;
        mThumbRadius = 0.4f * THUMB_SIZE;
        mCircleRadius = 0.7f * mThumbRadius;
        mPadding = 0.5f * THUMB_SIZE;
    }

    public void setStepSize(int size) {
        mNumOfStep = size + 1;
        invalidate();
    }

    public void setDrawListener(OnDrawListener drawListener) {
        mDrawListener = drawListener;
    }

    public List<Float> getTextXPosition() {
        return mTextXPosition;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterY = 0.5f * getHeight();
        mLeftX = mPadding;
        mLeftY = mCenterY - (mLineHeight / 2);
        mRightX = getWidth() - mPadding;
        mRightY = 0.5f * (getHeight() + mLineHeight);
        mDelta = (mRightX - mLeftX) / (mNumOfStep - 1);
        mThumbContainerXPosition.add(mLeftX);
        mTextXPosition.add(mDelta / 2);
        for (int i = 1; i < mNumOfStep - 1; i++) {
            mThumbContainerXPosition.add(mLeftX + (i * mDelta));
            mTextXPosition.add((mDelta * i + (mDelta / 2)));
        }
        mThumbContainerXPosition.add(mRightX);
        mDrawListener.onReady();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 200;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = THUMB_SIZE + 20;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width, height);
    }

    public void setCompletedPosition(int position) {
        mCompletedPosition = position;
    }


    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public void setBarColor(int barColor) {
        mBarColor = barColor;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawListener.onReady();
        paint.setAntiAlias(true);
        paint.setColor(mBarColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(mProgressColor);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setStrokeWidth(2);

        // Draw rest of the circle'Bounds
        for (int i = 0; i < mThumbContainerXPosition.size(); i++) {
            canvas.drawCircle(mThumbContainerXPosition.get(i), mCenterY, mCircleRadius,
                    (i <= mCompletedPosition) ? selectedPaint : paint);
        }

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mThumbContainerXPosition.get(0), mLeftY,
                mThumbContainerXPosition.get(mThumbContainerXPosition.size() - 1), mRightY,
                paint);

        selectedPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mThumbContainerXPosition.size() - 1; i++) {
            if (i < mCompletedPosition) {
                final float pos = mThumbContainerXPosition.get(i);
                final float selectPos = mThumbContainerXPosition.get(i + 1);
                canvas.drawRect(pos, mLeftY, selectPos, mRightY, selectedPaint);
            } else if (i == mCompletedPosition) {
                final float pos = mThumbContainerXPosition.get(i);
                final float selectPos = mThumbContainerXPosition.get(i + 1) -
                        mThumbContainerXPosition.get(1) / 2;
                canvas.drawRect(pos, mLeftY, selectPos, mRightY, selectedPaint);
            }
        }

        // Draw rest of circle
        for (int i = 0; i < mThumbContainerXPosition.size(); i++) {
            final float pos = mThumbContainerXPosition.get(i);
            canvas.drawCircle(pos, mCenterY, mCircleRadius,
                    (i <= mCompletedPosition) ? selectedPaint : paint);

            if (i == mCompletedPosition) {
                selectedPaint.setColor(getColorWithAlpha(mProgressColor, 0.2f));
                canvas.drawCircle(pos, mCenterY, mCircleRadius * 1.8f, selectedPaint);
            }

        }
    }

    public static int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

    public interface OnDrawListener {

        void onReady();
    }
}
