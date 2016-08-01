package com.droid.treasures.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by zhangleilei on 7/29/16.
 */

public class NumberTextView extends TextView {

    final static int[] SIZE_THRESHOLD = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};


    private float mPowNumber;
    private float mNumber;

    private int mDuration = 1500;

    private DecimalFormat mDecimalFormat;


    public NumberTextView(Context context) {
        this(context, null);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDecimalFormat = new DecimalFormat("0.00");
    }


    private void runNumber() {
        final ValueAnimator runAnimator = ValueAnimator.ofFloat(mPowNumber, mNumber);
        runAnimator.setDuration(mDuration);
        runAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setText(mDecimalFormat.format(Float.parseFloat(runAnimator
                        .getAnimatedValue().toString())));
            }
        });

        runAnimator.start();
    }

    public void setNumber(float number) {
        this.mNumber = number;
        if (mNumber < 0) {
            mNumber = 0;
        }
        this.mPowNumber = (float) (mNumber - Math.pow(10, sizeOf(number) - 1));
        if (mPowNumber < 0) {
            mPowNumber = 0;
        }
        runNumber();
    }


    private float sizeOf(float x) {
        int i = 0;
        while (true) {
            if (x <= SIZE_THRESHOLD[i]) {
                return i + 1;
            }
            i++;
        }
    }

}
