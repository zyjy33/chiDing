package com.yunsen.enjoy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.common.AppContext;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by tiansj on 14/12/30.
 */
public class DeviceUtil {

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp,字体的转换
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取DisplayMetrics，包括屏幕高宽，密度等
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获得屏幕宽度 px
     *
     * @param context
     * @return
     */
    public static int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度 px
     *
     * @param context
     * @return
     */
    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static String getIMSI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        } catch (Exception exception1) {
        }
        return "";
    }

    public static String getIMEI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei != null && !imei.equals("")) {
                return imei;
            }
        } catch (Exception exception1) {
        }

        return "";
    }

    public static String getDeviceId(Context context) {
        try {
            String strIMEI = getIMEI(context);
            if (strIMEI == null || strIMEI.equals("")) {
                strIMEI = getIMSI(context);
                if (strIMEI == null || strIMEI.equals("")) {
                    return "";
                }
            }
            String strTemp = strIMEI + strIMEI + strIMEI;
            String strMd5 = MiscUtil.getMD5(strTemp.getBytes());
            return strMd5;
        } catch (Exception exception1) {
        }
        return "";
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }


    /**
     * 获取当前程序的版本信息
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;

    }

    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    public static int getScreenWidth() {
        if (sScreenWidth != 0) {
            return sScreenWidth;
        }
        Resources resources = AppContext.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenWidth;
    }

    public static int getScreenHeight() {
        if (sScreenHeight != 0) {
            return sScreenHeight;
        }
        Resources resources = AppContext.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenHeight;
    }

    /**
     * 获取虚拟键盘的高度
     *
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totlaHeight = getScreenHeight();
        int contentHeight = getScreenHeight(context);
        return totlaHeight - contentHeight;
    }
    /**
     * 获取底部虚拟键盘的高度
     *
     * @param context
     * @return
     */
    private static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(out);
        return out.heightPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenDPI(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dpi;
    }

}
