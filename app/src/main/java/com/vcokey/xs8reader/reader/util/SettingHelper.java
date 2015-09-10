package com.vcokey.xs8reader.reader.util;

import android.content.Context;
import android.util.TypedValue;

import com.vcokey.xs8reader.reader.model.Setting;

/**
 * Created by hongxiu on 2015/9/9.
 */
public class SettingHelper {
    private static final Setting SETTING = new Setting();

    /**
     * 获取设置单例，为初始化时则获取默认设置
     * @param context
     * @return
     */
    public static Setting obtain(Context context){
        if (SETTING.initial){
            return obtainDefault(context);
        }
        return SETTING;
    }

    /**
     * 获取默认设置
     * @param context
     * @return
     */
    public static Setting obtainDefault(Context context){
        SETTING.initial = false;
        SETTING.pagePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,
                context.getResources().getDisplayMetrics());
        SETTING.backgroundColor = ReaderSettingUtils.getBackgroundColor(context);
        SETTING.backgroundImg = ReaderSettingUtils.getBackgroundImg(context);
        SETTING.fontColor = ReaderSettingUtils.getFontColor(context);
        SETTING.fontSize = ReaderSettingUtils.getFontSize(context);
        SETTING.lineSpacing = ReaderSettingUtils.getLineSpacing(context);
        return SETTING;
    }

    /**
     * 保存设置
     * @param context
     */
    public void save(Context context){
        ReaderSettingUtils.setBackgroundColor(context, SETTING.backgroundColor);
        ReaderSettingUtils.setBackgroundImg(context, SETTING.backgroundImg);
        ReaderSettingUtils.setBackgroundType(context, SETTING.backgroundType);
        ReaderSettingUtils.setFlipMode(context, SETTING.flipMode);
        ReaderSettingUtils.setFontColor(context, SETTING.backgroundColor);
        ReaderSettingUtils.setFontSize(context, SETTING.fontSize);
        ReaderSettingUtils.setLineSpacing(context, SETTING.lineSpacing);
        ReaderSettingUtils.setNightMode(context, SETTING.nightMode == Setting.MODE_NIGHT);
        ReaderSettingUtils.setSimpleMode(context, SETTING.simpleMode);
    }

    public void setNightMode(int nightMode) {
        SETTING.nightMode = nightMode;
    }

    public void setFontSize(int fontSize) {
        SETTING.fontSize = fontSize;
    }

    public void setLineSpacing(float lineSpacing) {
        SETTING.lineSpacing = lineSpacing;
    }

    public void setFlipMode(int flipMode) {
        SETTING.flipMode = flipMode;
    }

    public void setSimpleMode(int simpleMode) {
        SETTING.simpleMode = simpleMode;
    }

    public void setFontColor(int fontColor) {
        SETTING.fontColor = fontColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        SETTING.backgroundColor = backgroundColor;
    }

    public void setBackgroundImg(String backgroundImg) {
        SETTING.backgroundImg = backgroundImg;
    }

    public void setBackgroundType(int backgroundType) {
        SETTING.backgroundType = backgroundType;
    }
}
