package com.droid.ray.driver.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by zhangleilei on 8/11/16.
 */

public class ScrollerView extends ScrollView {

    Scroller mScroller;

    public ScrollerView(Context context) {
        this(context, null);
    }

    public ScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public void smoothScrollToSlow(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;

        int scrollY = getScrollY();
        int deltaY = destY - scrollY;

        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
