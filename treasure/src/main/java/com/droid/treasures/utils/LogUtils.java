package com.droid.treasures.utils;

import android.util.Log;

/**
 * Log统一管理类
 */
public class LogUtils {


    private enum LoginType {
        Verbose,
        Debug,
        Info,
        Warn,
        Error
    }

    public static boolean isDebug = true;

    private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void v(String tag, String msg) {
        printLog(LoginType.Verbose, tag, msg);
    }

    public static void d(String tag, String msg) {
        printLog(LoginType.Debug, tag, msg);
    }

    public static void i(String tag, String msg) {
        printLog(LoginType.Info, tag, msg);
    }

    public static void w(String tag, String msg) {
        printLog(LoginType.Warn, tag, msg);
    }

    public static void e(String tag, String msg) {
        printLog(LoginType.Error, tag, msg);
    }

    private static void printLog(LoginType type, String tag, String msg) {
        if (!isDebug)
            return;

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        int index = 4;
        String className = stackTraceElements[index].getFileName();
        String methodName = stackTraceElements[index].getMethodName();
        int lineNumber = stackTraceElements[index].getLineNumber();

        tag = tag == null ? className : tag;
        methodName = methodName.substring(0, 1) + methodName.substring(1);

        switch (type) {
            case Verbose:
                Log.v(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.v(tag, "|                                                                                                             |");
                Log.v(tag, "|   类名:(" + className + ":" + lineNumber + ")#方法:" + methodName);
                Log.v(tag, "|                                                                                                             |");
                Log.v(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.v(tag, "|                                                                                                             |");
                Log.v(tag, "|   结果:" + msg);
                Log.v(tag, "|                                                                                                             |");
                Log.v(tag, "---------------------------------------------------------------------------------------------------------------");
                break;
            case Debug:
                Log.d(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.d(tag, "|                                                                                                             |");
                Log.d(tag, "|   类名:(" + className + ":" + lineNumber + ")#方法:" + methodName);
                Log.d(tag, "|                                                                                                             |");
                Log.d(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.d(tag, "|                                                                                                             |");
                Log.d(tag, "|   结果:" + msg);
                Log.d(tag, "|                                                                                                             |");
                Log.d(tag, "---------------------------------------------------------------------------------------------------------------");
                break;
            case Info:
                Log.i(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.i(tag, "|                                                                                                             |");
                Log.i(tag, "|   类名:(" + className + ":" + lineNumber + ")#方法:" + methodName);
                Log.i(tag, "|                                                                                                             |");
                Log.i(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.i(tag, "|                                                                                                             |");
                Log.i(tag, "|   结果:" + msg);
                Log.i(tag, "|                                                                                                             |");
                Log.i(tag, "---------------------------------------------------------------------------------------------------------------");
                break;
            case Warn:
                Log.w(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.w(tag, "|                                                                                                             |");
                Log.w(tag, "|   类名:(" + className + ":" + lineNumber + ")#方法:" + methodName);
                Log.w(tag, "|                                                                                                             |");
                Log.w(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.w(tag, "|                                                                                                             |");
                Log.w(tag, "|   结果:" + msg);
                Log.w(tag, "|                                                                                                             |");
                Log.w(tag, "---------------------------------------------------------------------------------------------------------------");
                break;
            case Error:
                Log.e(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.e(tag, "|                                                                                                             |");
                Log.e(tag, "|   类名:(" + className + ":" + lineNumber + ")#方法:" + methodName);
                Log.e(tag, "|                                                                                                             |");
                Log.e(tag, "---------------------------------------------------------------------------------------------------------------");
                Log.e(tag, "|                                                                                                             |");
                Log.e(tag, "|   结果:" + msg);
                Log.e(tag, "|                                                                                                             |");
                Log.e(tag, "---------------------------------------------------------------------------------------------------------------");
                break;
        }

    }
}