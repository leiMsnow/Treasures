package com.droid.ray.driver.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droid.ray.driver.R;
import com.droid.treasures.utils.DensityUtils;
import com.droid.treasures.utils.ToastUtils;

import static com.droid.ray.driver.activity.ShowActivity.ARG_TITLE;


public class SpannableFragment extends Fragment {


    public static SpannableFragment newInstance(String title) {
        SpannableFragment fragment = new SpannableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return new SpannableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spannable, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvSpannable = (TextView) view.findViewById(R.id.tv_spannable);

        tvSpannable.setText(getSpannable());
//        tvSpannable.setHighlightColor(Color.TRANSPARENT);
        tvSpannable.setMovementMethod(LinkMovementMethod.getInstance());

    }


    private CharSequence getSpannable() {
        String text = "SpannableString可以设置\n背景色\n前景色\n相对大小\n绝对大小\n" +
                "上下偏移\n粗体\n斜体\n下划线\n删除线\n点击监听\n超链接\n添加表情(图标)";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        int startBG = text.indexOf("背景色");
        spannable.setSpan(new BackgroundColorSpan(Color.RED), startBG, startBG + 3,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        int startForeground = text.indexOf("前景色");
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), startForeground, startForeground + 3,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startBigText = text.indexOf("大");
        spannable.setSpan(new RelativeSizeSpan(1.5f), startBigText, startBigText + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startSmallText = text.indexOf("小");
        spannable.setSpan(new RelativeSizeSpan(0.5f), startSmallText, startSmallText + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startBigText1 = text.lastIndexOf("大");
        spannable.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(getContext(), 22)), startBigText1, startBigText1 + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        int startSmallText1 = text.lastIndexOf("小");
        spannable.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(getContext(), 12)), startSmallText1, startSmallText1 + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startSuperscript = text.indexOf("上");
        spannable.setSpan(new SuperscriptSpan(), startSuperscript, startSuperscript + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        int startSubscript = text.indexOf("下");
        spannable.setSpan(new SubscriptSpan(), startSubscript, startSubscript + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startStyleB = text.indexOf("粗");
        spannable.setSpan(new StyleSpan(Typeface.BOLD), startStyleB, startStyleB + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        int startStyleI = text.indexOf("斜");
        spannable.setSpan(new StyleSpan(Typeface.ITALIC), startStyleI, startStyleI + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startUnderline = text.indexOf("下划线");
        spannable.setSpan(new UnderlineSpan(), startUnderline, startUnderline + 3,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startStrikethrough = text.indexOf("删除线");
        spannable.setSpan(new StrikethroughSpan(), startStrikethrough, startStrikethrough + 3,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startClick = text.indexOf("点击监听");
        spannable.setSpan(new MyClickable(), startClick, startClick + 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startLink = text.indexOf("超链接");
        spannable.setSpan(new URLSpan("http://baidu.com"), startLink, startLink + 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        int startImage = text.indexOf("表情");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannable.setSpan(new ImageSpan(drawable),
                startImage, startImage + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return spannable;
    }

    class MyClickable extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            ToastUtils.getInstance(getContext()).showToast(widget.getClass().getName());
        }
    }
}
