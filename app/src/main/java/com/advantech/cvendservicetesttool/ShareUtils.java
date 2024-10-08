package com.advantech.cvendservicetesttool;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    private static final String NAME = "preferences_settings";

    /**
     * 存储String类型的值
     *
     * @param mContext this
     * @param key      key值
     * @param value    要存储的String值
     */
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 获取String类型的值
     *
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context mContext, String key, String defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }


    /**
     * 存储Int类型的值
     *
     * @param mContext this
     * @param key      key
     * @param value    要存储的Int值
     */
    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }


    /**
     * 获取Int类型的值
     *
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static int getInt(Context mContext, String key, int defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }


    /**
     * 存储Boolean类型的值
     *
     * @param mContext this
     * @param key      key
     * @param value    要存储Boolean值
     */
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取Boolean类型的值
     *
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context mContext, String key, Boolean defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    //删除 单个 key
    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }

    //删除全部 key
    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
