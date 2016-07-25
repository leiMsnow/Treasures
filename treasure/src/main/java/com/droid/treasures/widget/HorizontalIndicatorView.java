package com.droid.treasures.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droid.treasures.R;
import com.droid.treasures.utils.DensityUtils;

/**
 * ViewPager滑动指示器
 * Created by zhangleilei on 7/19/16.
 */
public class HorizontalIndicatorView extends HorizontalScrollView {

    private LinearLayout mContainer;
    private ViewPager mViewPager;

    private Paint mLinePaint;

    private int mLastScrollX = 0;
    private int mCurrentPosition = 0;
    private float mCurrentOffset = 0;
    private int mItemCount = 0;
    private int mItemPadding = 0;
    private int mScreenWidth = 0;
    private boolean isEquallyItem = false;
    private int mIndicatorColor = Color.RED;
    private int mIndicatorDefaultColor = Color.DKGRAY;
    private boolean isMeasure = false;

    public HorizontalIndicatorView(Context context) {
        this(context, null);
    }

    public HorizontalIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint();
        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalIndicatorView);
        isEquallyItem = typedArray.getBoolean(R.styleable.HorizontalIndicatorView_equally_item, false);
        mIndicatorColor = typedArray.getColor(R.styleable.HorizontalIndicatorView_indicator_color, Color.RED);
        typedArray.recycle();

        initView();
    }


    private void initView() {

        setFillViewport(true);

        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(layoutParams);
        addView(mContainer);
    }


    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;

        if (mViewPager == null) {
            throw new NullPointerException("viewPager is null");
        }

        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("viewPager must set adapter first");
        }

        mViewPager.addOnPageChangeListener(new ViewPagerListener());
        notifyDataSetChange();
    }

    private void addTextView(final int position, String title) {

        mItemPadding = DensityUtils.dp2px(getContext(), 8);

        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setTextSize(14);
        textView.setTextColor(mIndicatorDefaultColor);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(mItemPadding, 0, mItemPadding, 0);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mContainer.addView(textView, layoutParams);
    }

    private class ViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mCurrentPosition = position;
            mCurrentOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * mContainer.getChildAt(position).getWidth()));
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(mViewPager.getCurrentItem(), 0);
                setSelectedChild(mCurrentPosition);
            }
        }
    }

    private void setSelectedChild(int position) {
        for (int i = 0; i < mItemCount; i++) {
            TextView child = (TextView) mContainer.getChildAt(i);
            child.setTextColor(mIndicatorDefaultColor);
            if (position == i) {
                child.setTextColor(mIndicatorColor);
            }
        }
    }

    private void scrollToChild(int position, int positionOffset) {
        int scrollX = mContainer.getChildAt(position).getLeft() + positionOffset;
        if ((position > 0 && position < mItemCount) || positionOffset > 0) {
            scrollX -= mScreenWidth / 3;
        }
        if (scrollX != mLastScrollX) {
            scrollTo(scrollX, 0);
            mLastScrollX = scrollX;
        }
    }


    public void notifyDataSetChange() {
        mContainer.removeAllViews();
        mCurrentPosition = mViewPager.getCurrentItem();
        mItemCount = mViewPager.getAdapter().getCount();

        for (int i = 0; i < mItemCount; i++) {
            addTextView(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }

        setSelectedChild(mCurrentPosition);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!isEquallyItem) return;
        if (isMeasure) return;

        isMeasure = true;

        int childWidthCount = 0;
        for (int i = 0; i < mItemCount; i++) {
            childWidthCount += mContainer.getChildAt(i).getMeasuredWidth();
        }

        if (childWidthCount <= mScreenWidth) {
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            for (int i = 0; i < mItemCount; i++) {
                mContainer.getChildAt(i).setLayoutParams(layoutParams);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineHeight = DensityUtils.dp2px(getContext(), 2);
        TextView currentChild = (TextView) mContainer.getChildAt(mCurrentPosition);

        float lineBottom = getHeight();
        float lineTop = lineBottom - lineHeight;
        float lineLeft = currentChild.getLeft();
        float lineRight = currentChild.getRight();

        if (mCurrentOffset > 0f && mCurrentPosition < mItemCount - 1) {
            TextView nextChild = (TextView) mContainer.getChildAt(mCurrentPosition + 1);
            lineLeft = (mCurrentOffset * nextChild.getLeft()) + ((1f - mCurrentOffset) * lineLeft);
            lineRight = (mCurrentOffset * nextChild.getRight()) + ((1f - mCurrentOffset) * lineRight);

            drawTextColor(currentChild, mIndicatorColor, mIndicatorDefaultColor);
            drawTextColor(nextChild, mIndicatorDefaultColor, mIndicatorColor);
        }


        mLinePaint.setColor(Color.LTGRAY);
        canvas.drawRect(mContainer.getLeft(), lineTop, mContainer.getWidth(), lineBottom, mLinePaint);

        int splitMargin = DensityUtils.dp2px(getContext(), 12);
        int splitWidth = DensityUtils.dp2px(getContext(), 1);
        int splitTop = getTop() + splitMargin;
        int splitBottom = getBottom() - splitMargin;
        for (int i = 0; i < mItemCount; i++) {
            canvas.drawRect(mContainer.getChildAt(i).getRight(), splitTop,
                    mContainer.getChildAt(i).getRight() + splitWidth, splitBottom, mLinePaint);
        }

        mLinePaint.setColor(mIndicatorColor);
        canvas.drawRect(lineLeft + mItemPadding, lineTop, lineRight - mItemPadding, lineBottom, mLinePaint);

    }

    private void drawTextColor(TextView view, int beforeColor, int nextColor) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int changeColor = (int) evaluator.evaluate(mCurrentOffset, beforeColor, nextColor);
        view.setTextColor(changeColor);
    }


    private static final String INSTANCE_STATUS = "INSTANCE_STATUS";
    private static final String STATE_POSITION = "STATE_POSITION";

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putInt(STATE_POSITION, mCurrentPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentPosition = bundle.getInt(STATE_POSITION);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
