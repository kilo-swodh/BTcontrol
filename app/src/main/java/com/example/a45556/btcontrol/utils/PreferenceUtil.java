package com.example.a45556.btcontrol.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wty
 * ShareP 工具类  请在Application oncreate初始化
 **/
public class PreferenceUtil {

    private static volatile PreferenceUtil sInstance = null;

    private static String PREFERENCES_NAME = "Preferences";//preference名字
    public static String RATE1_CODE = "rate1";//等级
    public static String RATE2_CODE = "rate2";//
    public static String RATE3_CODE = "rate3";//
    public static String RATE4_CODE = "rate4";//
    public static String RATE5_CODE = "rate5";//

    private SharedPreferences mSharedPreferences;

    /**
     * 单例模式，获取instance实例
     * @return
     */
    public synchronized static PreferenceUtil getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("please init first!");
        }
        return sInstance;
    }

    /**
     * context用AppContext
     **/
    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (PreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new PreferenceUtil(context);
                }
            }
        }
    }

    private PreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public String getLeftCode() {
        return mSharedPreferences.getString(RATE1_CODE,"2");
    }

    public String getRightCode() {
        return mSharedPreferences.getString(RATE2_CODE,"2");
    }

    public String getUpCode() {
        return mSharedPreferences.getString(RATE3_CODE,"2");
    }

    public String getDownCode() {
        return mSharedPreferences.getString(RATE4_CODE,"2");
    }

    public String getStopCode() {return mSharedPreferences.getString(RATE5_CODE,"2");}

    /**
     *	功能描述:保存到sharedPreferences
     */
    public void writePreferences(String key,String value){
        mSharedPreferences.edit().putString(key, value).commit();// 提交修改;
    }

    public void writePreferences(String key,Boolean value){
        mSharedPreferences.edit().putBoolean(key, value).commit();// 提交修改;
    }

    public void writePreferences(String key,int value){
        mSharedPreferences.edit().putInt(key, value).commit();// 提交修改;
    }

}
