package com.droid.treasures.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 系统应用
 * Created by zhanghaifeng on 16/4/4.
 */
public class SystemUtils {


    /**
     * 发送短信
     */
    public static void sendMsg(Context context) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        context.startActivity(intent);
    }
}
