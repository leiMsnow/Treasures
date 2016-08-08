package com.droid.ray.driver.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * 自定义点击span类
 * 
 * @author zhangleilei
 * 
 */
public abstract class TouchableSpan extends ClickableSpan {
	private boolean mIsPressed;
	private int mNormalTextColor;
	private int mPressedTextColor;

	public TouchableSpan(int normalTextColor, int pressedTextColor) {
		mNormalTextColor = normalTextColor;
		mPressedTextColor = pressedTextColor;
	}

	public void setPressed(boolean isSelected) {
		mIsPressed = isSelected;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);
		ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
	}
}
