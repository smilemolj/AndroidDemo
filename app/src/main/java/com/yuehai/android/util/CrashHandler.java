package com.yuehai.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import library.base.BaseApplication;

/**
 * Created by Twan on 2016/8/3.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static Thread.UncaughtExceptionHandler defaultHandler = null;

    private Context context = null;

    private final String TAG = CrashHandler.class.getSimpleName();

    public CrashHandler(Context context) {
        this.context = context;
    }

    /**
     * 初始化,设置该CrashHandler为程序的默认处理器
     */
    public void init() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println(ex.toString());
        LogUtil.INSTANCE.e(ex.toString());
        LogUtil.INSTANCE.e(collectCrashDeviceInfo());
        LogUtil.INSTANCE.e(getCrashInfo(ex));
        // 调用系统错误机制
        defaultHandler.uncaughtException(thread, ex);
        BaseApplication.Companion.getInstance().exitApp();
    }

    /**
     * 得到程序崩溃的详细信息
     */
    public String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * 收集程序崩溃的设备信息
     */
    public String collectCrashDeviceInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = pi.versionName;
            String model = android.os.Build.MODEL;
            String androidVersion = android.os.Build.VERSION.RELEASE;
            String manufacturer = android.os.Build.MANUFACTURER;
            return versionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
