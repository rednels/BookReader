package com.vcokey.xs8reader.reader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;

/**
 * 阅读器设置，所有方法均未做正确性验证，需自行验证
 * <p/>
 * Created by hongxiu on 2015/9/7.
 */
public class ReaderSettingUtils {
    private static final String PREF_NAME = "com.vcokey.reader.setting";

    private static SharedPreferences PREFERENCES;

    //setting keys begin
    public static final String KEY_NIGHT_MODE = "SETTING.NightMode";    //夜间模式
    public static final String KEY_FONT_SIZE = "SETTING.FontSize";      //字体大小
    public static final String KEY_FONT_COLOR = "SETTING.FontColor";    //字体颜色
    public static final String KEY_LINE_SPACING = "SETTING.LineSpacing";//行距
    public static final String KEY_FLIP_MODE = "SETTING.FlipMode";      //翻页方式
    public static final String KEY_SIMPLE_MODE = "SETTING.SimpleMode";  //简体、或者繁体
    public static final String KEY_BACKGROUND_COLOR = "SETTING.BackgroundColor";//背景颜色
    public static final String KEY_BACKGROUND_IMG = "SETTING.BackgroundImg";    //背景图片
    public static final String KEY_BACKGROUND_TYPE = "SETTING.BackgroundType";  //背景类型（颜色还是图片）
    //setting keys end

    //default setting value begin:
    private static final int DEFAULT_FONT_SIZE = 16;    //16SP
    private static final int DEFAULT_FONT_COLOR = Color.BLACK;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final float DEFAULT_LINE_SPACING = 1.2f;
    //default setting value end

    /**
     * 偏好设置初始化检查
     *
     * @param context
     */
    private static void checkPref(Context context) {
        if (PREFERENCES == null) {
            PREFERENCES = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * 设置是否是夜间模式
     *
     * @param night true，是夜间模式
     */
    public static void setNightMode(Context context, boolean night) {
        checkPref(context);
        PREFERENCES.edit().putBoolean(KEY_NIGHT_MODE, night).apply();
    }

    /**
     * 获取是否为夜间模式
     *
     * @param context
     * @return true，夜间模式，false，非夜间模式
     */
    public static boolean getNightMode(Context context) {
        checkPref(context);
        return PREFERENCES.getBoolean(KEY_NIGHT_MODE, false);
    }

    /**
     * 设置字体大小
     *
     * @param context
     * @param fontSize 字体大小，PX
     */
    public static void setFontSize(Context context, int fontSize) {
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_FONT_SIZE, fontSize).apply();
    }

    /**
     * 获取字体大小，默认16sp
     *
     * @param context
     * @return 字体大小，或默认16sp
     */
    public static int getFontSize(Context context) {
        checkPref(context);
        return PREFERENCES.getInt(KEY_FONT_SIZE, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, DEFAULT_FONT_SIZE,
                context.getResources().getDisplayMetrics()));
    }

    /**
     * 设置字体颜色
     * @param context
     * @param color
     */
    public static void setFontColor(Context context,int color){
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_FONT_COLOR,color).apply();
    }

    /**
     * 获取字体颜色，默认黑色
     * @param context
     * @return
     */
    public static int getFontColor(Context context){
        checkPref(context);
        return PREFERENCES.getInt(KEY_FONT_COLOR,DEFAULT_FONT_COLOR);
    }

    /**
     * 设置多倍行距
     * @param context
     * @param spacing
     */
    public static void setLineSpacing(Context context, float spacing){
        checkPref(context);
        PREFERENCES.edit().putFloat(KEY_LINE_SPACING,spacing).apply();
    }

    /**
     * 获取多倍行距设置
     * @param context
     * @return
     */
    public static float getLineSpacing(Context context){
        checkPref(context);
        return PREFERENCES.getFloat(KEY_LINE_SPACING,DEFAULT_LINE_SPACING);
    }

    /**
     * 设置翻页模式
     * @param context
     * @param mode
     */
    public static void setFlipMode(Context context,int mode){
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_FLIP_MODE,mode).apply();
    }

    /**
     * 获取翻页模式，未设置则返回-1
     * @param context
     * @return
     */
    public static int getFlipMode(Context context){
        checkPref(context);
        return PREFERENCES.getInt(KEY_FLIP_MODE,-1);
    }
    /**
     * 这是简繁模式
     * @param context
     * @param mode
     */
    public static void setSimpleMode(Context context,int mode){
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_SIMPLE_MODE,mode).apply();
    }

    /**
     * 获取简繁模式，未设置则返回-1
     * @param context
     * @return
     */
    public static int getSimpleMode(Context context){
        checkPref(context);
        return PREFERENCES.getInt(KEY_SIMPLE_MODE,-1);
    }

    /**
     * 设置背景颜色
     * @param context
     * @param color
     */
    public static void setBackgroundColor(Context context, int color){
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_BACKGROUND_COLOR,color).apply();
    }

    /**
     * 获取背景颜色，默认为白色
     * @param context
     * @return
     */
    public static int getBackgroundColor(Context context){
        checkPref(context);
        return PREFERENCES.getInt(KEY_BACKGROUND_COLOR,DEFAULT_BACKGROUND_COLOR);
    }

    /**
     * 设置背景图片路径
     * @param context
     * @param imagePath
     */
    public static void setBackgroundImg(Context context,String imagePath){
        checkPref(context);
        PREFERENCES.edit().putString(KEY_BACKGROUND_IMG, imagePath);
    }

    /**
     * 获取背景图片路径，默认为空
     * @param context
     * @return
     */
    public static String getBackgroundImg(Context context){
        checkPref(context);
        return PREFERENCES.getString(KEY_BACKGROUND_IMG,"");
    }

    /**
     * 设置背景类型
     * @param context
     * @param type
     */
    public static void setBackgroundType(Context context, int type){
        checkPref(context);
        PREFERENCES.edit().putInt(KEY_BACKGROUND_TYPE,type).apply();
    }

    /**
     * 获取背景类型，未设置则返回-1
     * @param context
     * @return
     */
    public static int getBackgroundType(Context context){
        checkPref(context);
        return PREFERENCES.getInt(KEY_BACKGROUND_TYPE,-1);
    }
}
