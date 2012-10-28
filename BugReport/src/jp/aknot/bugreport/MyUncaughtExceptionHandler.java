package jp.aknot.bugreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import jp.aknot.bugreport.util.LogUtil;

public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private static final String TAG = "@" + MyUncaughtExceptionHandler.class.getSimpleName();

    private static File bugReportFile;
    static {
        bugReportFile = new File(MyApplication.getExternalFilesDir(), "crash-log.txt");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.d(TAG, ex.getMessage());
        try {
            saveStacks(ex);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, "Failed to save bug report file!", e);
        }

    }

    private void saveStacks(Throwable tr) throws FileNotFoundException {
        LogUtil.d(TAG, MyApplication.getExternalFilesDir().toString());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(bugReportFile));

            StringBuilder sb = new StringBuilder();

            StackTraceElement[] stacks = tr.getStackTrace();
            for (StackTraceElement stack : stacks) {
                sb.setLength(0);
                sb.append(stack.getClassName()).append("#");
                sb.append(stack.getMethodName()).append(":");
                sb.append(stack.getLineNumber()).append("-");
                sb.append(stack.toString());
                pw.println(sb.toString());
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

}
