package jp.aknot.bugreport.util;

import jp.aknot.bugreport.MyApplication;
import android.util.Log;

public final class LogUtil {

    private LogUtil() {
    }

    public static void d(String tag, String msg) {
        if (MyApplication.isDebuggable()) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (MyApplication.isDebuggable()) {
            Log.d(tag,msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (MyApplication.isDebuggable()) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (MyApplication.isDebuggable()) {
            Log.i(tag,msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (MyApplication.isDebuggable()) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (MyApplication.isDebuggable()) {
            Log.w(tag,msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (MyApplication.isDebuggable()) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (MyApplication.isDebuggable()) {
            Log.e(tag,msg, tr);
        }
    }
}
