package com.droid.ray.driver.widget;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 自定义超链接点击类
 * 
 * @author zhangleilei
 * 
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {

	private TouchableSpan mPressedSpan;
	private static LinkTouchMovementMethod sInstance;

	public static LinkTouchMovementMethod getInstance() {
		if (sInstance == null)
			sInstance = new LinkTouchMovementMethod();
		return sInstance;
	}

	private LinkTouchMovementMethod() {
	}

	@Override
	public boolean onTouchEvent(TextView textView, Spannable spannable,
			MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mPressedSpan = getPressedSpan(textView, spannable, event);
			if (mPressedSpan != null) {
				mPressedSpan.setPressed(true);
				Selection.setSelection(spannable,
						spannable.getSpanStart(mPressedSpan),
						spannable.getSpanEnd(mPressedSpan));
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			TouchableSpan touchedSpan = getPressedSpan(textView, spannable,
					event);
			if (mPressedSpan != null && touchedSpan != mPressedSpan) {
				mPressedSpan.setPressed(false);
				mPressedSpan = null;
				Selection.removeSelection(spannable);
			}
		} else {
			if (mPressedSpan != null) {
				mPressedSpan.setPressed(false);
				super.onTouchEvent(textView, spannable, event);
			}
			mPressedSpan = null;
			Selection.removeSelection(spannable);
		}
		return true;
	}

	private TouchableSpan getPressedSpan(TextView textView,
			Spannable spannable, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		x -= textView.getTotalPaddingLeft();
		y -= textView.getTotalPaddingTop();

		x += textView.getScrollX();
		y += textView.getScrollY();

		Layout layout = textView.getLayout();
		int line = layout.getLineForVertical(y);
		int off = layout.getOffsetForHorizontal(line, x);

		TouchableSpan[] link = spannable
				.getSpans(off, off, TouchableSpan.class);
		TouchableSpan touchedSpan = null;
		if (link.length > 0) {
			touchedSpan = link[0];
		}
		return touchedSpan;
	}

}
