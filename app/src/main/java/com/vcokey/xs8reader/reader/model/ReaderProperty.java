package com.vcokey.xs8reader.reader.model;

/**
 * the property of
 * <p/>
 * Created by vcokey on 2015/8/27.
 */
public class ReaderProperty {
    /**
     * text size in sp unit;
     */
    public int textSize;
    public int textColor;
    public int lineSpace;
    public int lineSpaceExtra;

    /**
     * night mode of the reader
     */
    public boolean nightMode;

    public ReadMode readMode;

    /**
     * 阅读模式<br/>
     * <p/>
     * MODE_FLIP,推压<br/>
     * MODE_SLIDE,平移<br/>
     * MODE_FADE;透明渐变<br/>
     */
    public enum ReadMode {
        MODE_FLIP,
        MODE_SLIDE,
        MODE_FADE;
    }
}
