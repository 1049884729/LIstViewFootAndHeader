/**
 * 文件名称 : SharePrefrenceUtil.java
 * <p>
 * 作者信息 : hu_yanqiang
 * <p>
 * 创建时间 : 2013-4-19, 下午2:00:44
 * <p>
 * 版权声明 : Copyright (c) 2009-2012 CIeNET Ltd. All rights reserved
 * <p>
 * 评审记录 :
 * <p>
 */

package com.example.ListViewFoot.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 请在这里增加文件描述
 * <p>
 */
public class SharePrefrenceUtil
{

    public static final String APP_PREFERENCE 	= "app_preference";
    public static final String CACHE_MANAGER	= "cache_manager";

    public static void saveBoolean(String fileName,Context con, String key, boolean isfirst){
        SharedPreferences settings = con.getSharedPreferences(fileName, Context.MODE_PRIVATE); //首先获取一个 SharedPreferences 对象
        settings.edit()  
        .putBoolean(key, isfirst)
        .commit();
        Logger.info(isfirst+"");
    }
    
    public static boolean getBoolean(String fileName,Context con, String key, boolean defvalue){
        SharedPreferences settings = con.getSharedPreferences(fileName,  Context.MODE_PRIVATE); //获取一个 SharedPreferences 对象
        Logger.info( settings.getBoolean(key, defvalue)+"");
        return settings.getBoolean(key, defvalue);


    }
    public static void saveString(String fileName,Context con, String key,String values) {
        SharedPreferences settings = con.getSharedPreferences(fileName,

                Context.MODE_PRIVATE); // 首先获取一个 SharedPreferences 对象

        settings.edit()
                .putString(key,
                        values)
                .commit();
    }

    public static String getString(String fileName,Context con, String key) {
        SharedPreferences settings = con.getSharedPreferences(fileName,
                0); // 获取一个 SharedPreferences 对象
        return settings.getString(key, null);
    }
  public static void saveInt(String fileName,Context con, String key,int values) {
        SharedPreferences settings = con.getSharedPreferences(fileName,
                Context.MODE_PRIVATE); // 首先获取一个 SharedPreferences 对象
        settings.edit()
                .putInt(key,
                        values)
                .commit();
    }

    public static int getInt(String fileName,Context con, String key) {
        SharedPreferences settings = con.getSharedPreferences(fileName,
                0); // 获取一个 SharedPreferences 对象
        return settings.getInt(key, 0);
    }  public static void saveLong(String fileName,Context con, String key,long values) {
        SharedPreferences settings = con.getSharedPreferences(fileName,
                Context.MODE_PRIVATE); // 首先获取一个 SharedPreferences 对象
        settings.edit()
                .putLong(key,
                        values)
                .commit();
    }

    public static long getLong(String fileName,Context con, String key) {
        SharedPreferences settings = con.getSharedPreferences(fileName,
                0); // 获取一个 SharedPreferences 对象
        return settings.getLong(key, 0);
    }


    /**
     * 删除Shared_Preference文件
     */
    public static void deleteSharedPrfs(Context context, String shared_prfs_name) {
        SharedPreferences data = context.getSharedPreferences(shared_prfs_name,
                Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        data.edit().clear().commit();
    }

}
