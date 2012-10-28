package jp.aknot.bugreport.util;

import android.os.Environment;

public final class ExternalStorageUtil {

    private ExternalStorageUtil() {
    }

    public static boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }
        return hasEnoughFreeSpace();
    }

    public static boolean hasEnoughFreeSpace() {
        // TODO: MyApplicationにて、設定ファイルから取得した情報を元にチェックを行う.
        return true;
    }

}
