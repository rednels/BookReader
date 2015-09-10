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
 *
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

    private ArrayList<TextLine> preChapter = new ArrayList<>();
    private ArrayList<TextLine> curChapter = new ArrayList<>();
    private ArrayList<TextLine> nextChapter = new ArrayList<>();

    public ReaderLayout(String text, int width, int height, TextPaint paint) {
        this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(mBitmap);
        this.mTextPaint = paint;
        this.mText = text;
        this.mWidth = width;
        this.mHeight = height;
        this.mFontWidth = paint.measureText(" ");
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
        chapter.add(new TextLine(true,position));
        while (position < mText.length()) {
            int end ;
            //计算每行最大字符位置
            if (position + maxCountInLine > mText.length()) {
                end = mText.length();
            }else{
                end = position + maxCountInLine;
            }
            //首行缩进后修正每行最大行宽，并计算每行最大字符数
            if (position == 0 || mText.substring(position - 1, end).startsWith("\n")) {
                lineChapterCount = mTextPaint.breakText(this.mText, position, end, true, lineWidth - 8 * mFontWidth, null);
            }else {
                lineChapterCount = mTextPaint.breakText(this.mText, position, end, true, lineWidth, null);
            }

            //换行时修正每行最大字符数
            String sub = mText.substring(position, position + lineChapterCount);
            TextLine line;
            Matcher matcher = pattern.matcher(sub);
            if (matcher.find()){
                end = position + matcher.start() + 1;
                line = new TextLine(true,end);
            }else{
                end = position + lineChapterCount;
                line = new TextLine(false,end);
            }
            chapter.add(line);
            position  = end;
        }
    }

    public void measurePage(){
        measureText(curChapter);
    }

    public void drawCurPage(Canvas canvas){
        for (int i = 0; i< 10;i++) {
            canvas.drawText(mText, curChapter.get(i).position, curChapter.get(i + 1).position, curChapter.get(i).firstLine ? 36 + 64 : 36, i * 48 + 36, mTextPaint);
            // 0 - 12 true
            // 12 - 26 false
            // 26 - 37 false
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void drawPreview(Canvas canvas) {

    }

    @Override
    public void drawCurrent(Canvas canvas) {

    }

    @Override
    public void drawNext(Canvas canvas) {

    }


}
