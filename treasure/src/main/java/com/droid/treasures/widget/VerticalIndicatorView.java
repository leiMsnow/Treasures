package com.droid.treasures.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.droid.treasures.utils.DensityUtils;

import java.util.List;

/**
 * 纵向滑动tabs
 * Created by zhangleilei on 7/19/16.
 */
public class VerticalIndicatorView extends ScrollView {

    private Scroller mScroller;

    private LinearLayout mContainer;

    private int mDefaultTextColor = Color.DKGRAY;
    private int mSelectedTextColor = Color.RED;
    private int mDefaultBGColor = Color.WHITE;
    private int mSelectedBGColor = Color.parseColor("#F0F0F0");
    private boolean isExpant = false;
    private int mItemCount = 0;
    private int mLastScrollY = 0;

    private int mScreenHeight = 0;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public VerticalIndicatorView(Context context) {
        this(context, null);
    }

    public VerticalIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setFillViewport(true);
        mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        mScroller = new Scroller(getContext());
        initView();
    }


    private void initView() {

        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(layoutParams);

        addView(mContainer);
    }

    public void addItemViews(final List<String> items) {
        mItemCount = items.size();
        mContainer.removeAllViews();
        for (int i = 0; i < mItemCount; i++) {
            final String item = items.get(i);
            final int position = i;
            TextView textView = new TextView(getContext());
            textView.setText(item);
            textView.setSingleLine();
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(mDefaultTextColor);
            textView.setBackgroundColor(mDefaultBGColor);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSelectItemColor(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
            int height = DensityUtils.dp2px(getContext(), 40);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height);

            mContainer.addView(textView, layoutParams);

        }
    }

    private void moveScrollY(int position) {
        int scrollY = mContainer.getChildAt(position).getTop();
        if (position > 0) {
            scrollY -= mScreenHeight / 3;
        }
        if (scrollY != mLastScrollY) {
            smoothScrollToSlow(0,scrollY,300);
            mLastScrollY = scrollY;
        }
    }

    //调用此方法滚动到目标位置  duration滚动时间
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy,int duration) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/4);
    }

    private void changeSelectItemColor(int position) {
        for (int i = 0; i < mItemCount; i++) {
            TextView textView = (TextView) mContainer.getChildAt(i);
            textView.setTextColor(mDefaultTextColor);
            textView.setBackgroundColor(mDefaultBGColor);
            if (position == i) {
                textView.setTextColor(mSelectedTextColor);
                textView.setBackgroundColor(mSelectedBGColor);
            }
        }
        moveScrollY(position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
