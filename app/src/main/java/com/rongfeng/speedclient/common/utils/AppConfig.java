package com.rongfeng.speedclient.common.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * AUTHOR: Alex
 * DATE: 11/11/2015 10:22
 */
public class AppConfig {

    public static final String IS_UPDATE = "isUpdate"; //版本更新字段

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        AppConfig.mContext = context;
//        AppConfig.mAcache = ACache.get(context);
    }


    /**
     * Editor Object
     *
     * @return
     */
    public static void removeKeyToValue(String keyStr) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(keyStr);
        editor.commit();
    }

    /**
     * 设置String类型程序参数
     *
     * @param key
     * @param value
     */
    public static void setStringConfig(String key, String value) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 得到String类型程序参数
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getStringConfig(String key, String defValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        String site = preferences.getString(key, defValue);
        return site;
    }


    /**
     * 设置int类型程序参数
     *
     * @param key
     * @param value
     */
    public static void setIntConfig(String key, int value) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 得到String类型程序参数
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntConfig(String key, int defValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        int site = preferences.getInt(key, defValue);
        return site;
    }


    /**
     * 设置Boolean类型程序参数
     *
     * @param key
     * @param value
     */
    public static void setBooleanConfig(String key, boolean value) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 得到Boolean类型程序参数
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBooleanConfig(String key, boolean defValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(
                "xxb", Context.MODE_PRIVATE);
        boolean site = preferences.getBoolean(key, defValue);
        return site;
    }



    /**
     * 缓存对象
     *
     * @return
     */
//    public static ACache getAcache() {
//        return mAcache;
//    }
}
