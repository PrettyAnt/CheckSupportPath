package com.example.checksupportpath;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 2:34 下午  21/10/21
 * PackageName : com.example.checksupportpath
 * describle : 获取支持的设备存储路径
 */
public class FileUtil {

    public static String getSandboxPath(Context context, String type) {
        if (isGreaterThanQ()) {
            if (sdCardIsAvailableNew(context)) {
                return context.getExternalFilesDir(type).getAbsolutePath();
            } else {
                return context.getFilesDir().getAbsolutePath();
            }
        } else {
            return getRootFilePath();
        }

    }

    /**
     * 判断系统是否大于Q并且没有开启兼容模式
     *
     * @return
     */
    private static boolean isGreaterThanQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy();

    }

    /**
     * 检查sd卡
     *
     * @return
     */
    private static boolean sdCardIsAvailableNew(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                File sd = new File(context.getExternalFilesDir(null).getAbsolutePath());
                return sd.canWrite();
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static String getRootFilePath() {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();// filePath:/sdcard
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath: /data/data
        }
    }

    /**
     * 检查sd卡
     *
     * @return
     */
    private static boolean sdCardIsAvailable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else {
            return false;
        }
    }
}
