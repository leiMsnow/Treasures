package com.droid.ray.driver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.droid.ray.driver.R;
import com.droid.treasures.utils.LogUtils;

public class ShowActivity extends AppCompatActivity {


    public static final String ARG_TITLE = "title";

    private static String[] mTitles = new String[]{
            "全部", "Spannable", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他",
            "全部", "酒店", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他",
            "全部", "酒店", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他",
            "全部", "酒店", "儿童剧", "户外", "科学", "场馆", "夏令营", "境外游", "国内游", "手工", "其他"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        LogUtils.d(this.getClass().getSimpleName(), "fragment:" + getIntent()
                .getStringExtra("fragment"));
        try {

            Bundle bundle = new Bundle();
            bundle.putStringArray("titles", mTitles);
            Fragment fragment = (Fragment) Class.forName(getIntent()
                    .getStringExtra("fragment")).newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
