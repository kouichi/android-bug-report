package jp.aknot.bugreport;

import java.io.File;

import jp.aknot.bugreport.util.LogUtil;
import android.app.Application;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "@" + MyApplication.class.getSimpleName();

    private static boolean debuggable;
    private static File externalFilesDir;

    @Override
    public void onCreate() {
        LogUtil.d(TAG, "onCreate()");
        super.onCreate();


        // Debug判定
        setDebuggable();
        Log.d(TAG, "isDebuggable: " + debuggable);

        setExternalFilesDir(null);

        Log.d(TAG, "externalFilesDir: " + externalFilesDir);



        // BugReport
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    private void setDebuggable() {
        PackageManager pm = getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(getPackageName(), 0);

            if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE) {
                debuggable = true;
            } else {
                debuggable = false;
            }
        } catch (NameNotFoundException e) {
            debuggable = false;
        }
    }

    public static boolean isDebuggable() {
        return debuggable;
    }

    public void setExternalFilesDir(String type) {
        externalFilesDir = ((ContextWrapper) this).getExternalFilesDir(type);
    }

    public static File getExternalFilesDir() {
        return externalFilesDir;
    }

}
