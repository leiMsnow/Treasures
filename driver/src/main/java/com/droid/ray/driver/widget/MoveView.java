package com.droid.ray.driver.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * Created by zhangleilei on 8/11/16.
 */

public class MoveView extends View {

    int mLastX = 0;
    int mLastY = 0;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                int translationX = (int) (getTranslationX() + deltaX);
                int translationY = (int) (getTranslationY() + deltaY);
                setTranslationX(translationX);
                setTranslationY(translationY);

                Log.d("move", "rawX: " + x + " rawY: " + y);
                Log.d("move", "translationX: " + translationX + " translationY: " + translationY);
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;

    }
}
