package com.vcokey.xs8reader.reader.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextPaint;

import com.vcokey.xs8reader.reader.model.Setting;
import com.vcokey.xs8reader.reader.util.SettingHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 负责文本正文排版
 * <p/>
 * Created by hongxiu on 2015/9/10.
 */
public class ReaderLayout implements Layout {

    private Canvas mCanvas;
    private TextPaint mTextPaint;
    private Bitmap mBitmap;

    private String mText;

    private Setting mSetting;

    private int mWidth;
    private int mHeight;

    private float mFontWidth;

    private ArrayList<TextLine> mChapterLines = new ArrayList<>();

    public ReaderLayout(String text, int width, int height, TextPaint paint) {
        this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(mBitmap);
        this.mTextPaint = paint;
        this.mText = text;
        this.mWidth = width;
        this.mHeight = height;
        this.mFontWidth = paint.measureText("永");
    }

    public void setSetting(Context context) {
        mSetting = SettingHelper.obtain(context);
    }

    int getMaxCountInLine(float width, float fontWidth) {
        return (int) (width / fontWidth);
    }

    void measureText(ArrayList<TextLine> chapter) {
        int lineWidth = mWidth - mSetting.pagePadding * 2;
        int maxCountInLine = getMaxCountInLine(lineWidth, mFontWidth);
        int position = 0;
        int lineChapterCount = 0;
        Pattern pattern = Pattern.compile("[\r\n]+");
        chapter.add(new TextLine(true, position));
        while (position < mText.length()) {
            int end;
            TextLine line;
            //计算每行最大字符位置
            if (position + maxCountInLine > mText.length()) {
                end = mText.length();
            } else {
                end = position + maxCountInLine;
            }
            //首行缩进后修正每行最大行宽，并计算每行最大字符数
            if (position == 0 || mText.substring(position - 1, end).startsWith("\n")) {
                lineChapterCount = mTextPaint.breakText(this.mText, position, end, true, lineWidth - 2 * mFontWidth, null);
            } else {
                lineChapterCount = mTextPaint.breakText(this.mText, position, end, true, lineWidth, null);
            }

            //换行时修正每行最大字符数

            String sub = mText.substring(position, Math.min(position + lineChapterCount + 1, mText.length()));

            Matcher matcher = pattern.matcher(sub);
            if (matcher.find(1)) {
                end = position + matcher.start() + 1;
                line = new TextLine(true, end);
                chapter.add(line);
            } else {
                end = position + lineChapterCount;
                line = new TextLine(false, end);
                chapter.add(line);
            }

            position = end;
        }
    }

    /**
     * 根据设置计算文本分行
     */
    public void measureLine() {
        measureText(mChapterLines);
    }

    public ArrayList<Integer> mPageIndex = new ArrayList<>();
    private int mCurrentPage = 1;

    public void measurePage(float height, float bodyHeader,float titleHeight, float bodyFooter){
        measureLine();
        mPageIndex.add(0);
        float bodyHeight =height - bodyHeader - bodyFooter;
        float fontHeight = getFontHeight();
        for (int i = 0; i < mChapterLines.size();){
            int lineCount;
            if (i == 0){
                 lineCount = (int) ((bodyHeight - titleHeight )/ fontHeight);
            }else{
                lineCount = (int) (bodyHeight / fontHeight);
            }
            i += lineCount;
            mPageIndex.add(lineCount);
        }
    }

    float getFontHeight(){
        return  (mTextPaint.getFontMetricsInt().bottom - mTextPaint.getFontMetricsInt().top)* mSetting.lineSpacing;
    }

    /**
     * 2 * {@link #mFontWidth}首行缩进2字符<br />
     *
     * @param canvas
     */
    public void drawCurPage(Canvas canvas, float textTop) {
        float fontHeight = getFontHeight();
        int pageLineStart = mPageIndex.get(mCurrentPage - 1);
        int pageLineIndex = mPageIndex.get(mCurrentPage);

        for (int i = pageLineStart; i < pageLineIndex; i++) {
            canvas.drawText(mText, mChapterLines.get(i).position, mChapterLines.get(i + 1).position,
                    mChapterLines.get(i).firstLine ? mSetting.pagePadding + 2 * mFontWidth : mSetting.pagePadding,
                    (i + 1) * (fontHeight) + textTop, mTextPaint);

        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void drawPreview(Canvas canvas) {

    }

    @Override
    public void drawCurrent(Canvas canvas, float textTop) {

    }

    @Override
    public void drawNext(Canvas canvas) {

    }


}
