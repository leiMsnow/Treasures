package com.droid.ray.driver;

import android.app.Application;

import com.droid.treasures.utils.LogUtils;

/**
 * Created by zhangleilei on 7/29/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.isDebug = BuildConfig.LOG_DEBUG;
    }
}
