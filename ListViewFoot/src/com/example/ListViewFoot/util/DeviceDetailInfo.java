package com.example.ListViewFoot.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by xff on 14-7-18.
 */
public class DeviceDetailInfo {

    public static String getAppVersionName(){
        return Build.VERSION.CODENAME;
    }

    public static int getAppVersion(Context context){
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
