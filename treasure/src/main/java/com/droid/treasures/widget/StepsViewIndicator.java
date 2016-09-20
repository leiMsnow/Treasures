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
    private int mNumOfStep = 0;
    private float mLineHeight;
    private float mThumbRadius;
    private float mCircleRadius;
    private float mPadding;
    private int mProgressColor = Color.YELLOW;
    private int mBarColor = Color.GRAY;

    private float mCenterY;
    private float mLeftX;
    private float mLeftY;
    private float mRightX;
    private float mRightY;
    private float mDelta;
    private List<StepsView.StepEntity> mSteps = new ArrayList<>();
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
        a.recycle();
        init();
    }

    private void init() {
        mLineHeight = 0.2f * THUMB_SIZE;
        mThumbRadius = 0.4f * THUMB_SIZE;
        mCircleRadius = 0.7f * mThumbRadius;
        mPadding = 0.5f * THUMB_SIZE;
    }

    public void setSteps(List<StepsView.StepEntity> steps) {
        mSteps = steps;
        mNumOfStep = steps.size();
        invalidate();
    }

    public void setDrawListener(OnDrawListener drawListener) {
        mDrawListener = drawListener;
    }

    public List<StepsView.StepEntity> getTextXPosition() {
        return mSteps;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterY = 0.5f * getHeight();
        mLeftX = mPadding;
        mLeftY = mCenterY - (mLineHeight / 2);
        mRightX = getWidth() - mPadding;
        mRightY = 0.5f * (getHeight() + mLineHeight);
        mDelta = mRightX / (mNumOfStep - 1);
        for (int i = 0; i < mNumOfStep; i++) {
            if (i == mNumOfStep - 1) {
                mSteps.get(i).setStepPosition(mRightX);
            } else if (i == 0) {
                mSteps.get(i).setStepPosition(mLeftX);
            } else {
                mSteps.get(i).setStepPosition(mDelta * i);
            }
            mSteps.get(i).setLabelPosition((i * mDelta) - mDelta / 2);
        }
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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);

        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(mProgressColor);
        selectedPaint.setStyle(Paint.Style.FILL);
        selectedPaint.setStrokeWidth(2);

        drawCircle(canvas);
        drawSelectLine(canvas);
        drawSelectCircle(canvas);
    }

    public void drawCircle(Canvas canvas) {
        for (int i = 0; i < mNumOfStep; i++) {
            canvas.drawCircle(mSteps.get(i).getStepPosition(), mCenterY, mCircleRadius, paint);
        }
        canvas.drawRect(mSteps.get(0).getStepPosition(), mLeftY,
                mSteps.get(mSteps.size() - 1).getStepPosition(), mRightY, paint);
    }

    public void drawSelectLine(Canvas canvas) {
        for (int i = 1; i < mNumOfStep; i++) {
            int before = mSteps.get(i - 1).getStep();
            if (mCompletedPosition > 0 && mCompletedPosition <= mSteps.get(i).getStep()) {
                float currentDelta = (mDelta / (mSteps.get(i).getStep() - before));
                float currentStep = (mCompletedPosition - before) * currentDelta + (mDelta * (i - 1));
                canvas.drawRect(mLeftX, mLeftY, currentStep, mRightY, selectedPaint);
                break;
            }
        }
    }

    private void drawSelectCircle(Canvas canvas) {
        int lastCircle = -1;
        selectedPaint.setColor(mProgressColor);
        for (int i = 0; i < mNumOfStep; i++) {
            float pos = mSteps.get(i).getStepPosition();
            if (mCompletedPosition == 0) continue;
            if (mCompletedPosition >= mSteps.get(i).getStep()) {
                lastCircle = i;
                canvas.drawCircle(pos, mCenterY, mCircleRadius, selectedPaint);
            }
        }
        if (lastCircle >= 1) {
            selectedPaint.setColor(getColorWithAlpha(mProgressColor, 0.2f));
            canvas.drawCircle(mSteps.get(lastCircle).getStepPosition(),
                    mCenterY, mCircleRadius * 1.8f, selectedPaint);
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
