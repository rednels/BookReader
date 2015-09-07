package com.vcokey.xs8reader.reader.model;

/**
 *阅读设置存储
 *
 * Created by hongxiu on 2015/9/7.
 */
public class Setting {

    public int nightMode;
    public int fontSize;
    public int lineSpacing;
    public int flipMode;
    public int simpleMode;
    public int fontColor;
    public int backgroundColor;
    public int backgroundImg;
    public int backgroundType;

    public Setting(int nightMode, int fontSize, int lineSpacing, int flipMode, int simpleMode, int fontColor, int backgroundColor, int backgroundImg, int backgroundType) {
        this.nightMode = nightMode;
        this.fontSize = fontSize;
        this.lineSpacing = lineSpacing;
        this.flipMode = flipMode;
        this.simpleMode = simpleMode;
        this.fontColor = fontColor;
        this.backgroundColor = backgroundColor;
        this.backgroundImg = backgroundImg;
        this.backgroundType = backgroundType;
    }

    public Setting() {
        backgroundType = TYPE_BACKGROUND_COLOR;
        flipMode = MODE_FLIP;
        simpleMode = MODE_SIMPLE;
        nightMode = MODE_DAY;
    }

    /**
     * 1 1 - 1 1 - 1 1 1 - 1 1
     */
    /**
     * 背景类型
     */
    public static final int TYPE_BACKGROUND_IMAGE = 1;
    /**
     * 背景类型
     */
    public static final int TYPE_BACKGROUND_COLOR = 1 << 1;
    /**
     * 翻页模式<br/>
     * <p/>
     * MODE_FLIP,推压<br/>
     */
    public static final int MODE_FLIP = 1 << 2;
    /**
     * 翻页模式<br/>
     * <p/>
     * MODE_SLIDE,平移<br/>
     */
    public static final int MODE_SLIDE = 1 << 3;
    /**
     * 翻页模式<br/>
     * <p/>
     * MODE_FADE;透明渐变<br/>
     */
    public static final int MODE_FADE = 1 << 4;
    /**
     * 简繁
     */
    public static final int MODE_SIMPLE = 1 << 5;
    /**
     * 简繁
     */
    public static final int MODE_TRADITION = 1 << 6;
    /**
     * 夜间模式
     */
    public static final int MODE_NIGHT = 1 << 7;
    /**
     * 白天模式
     */
    public static final int MODE_DAY = 1 << 8;

}
