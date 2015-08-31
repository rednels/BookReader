package com.vcokey.xs8reader.reader.widget;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BulletSpan;
import android.text.style.LineHeightSpan;

/**
 * 文本预渲染构建器
 *
 * Created by redn on 2015/8/30.
 */
public class LayoutBuilder {

    private SpannableString mText;
    private Layout.Alignment mAlign;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;
    private boolean mIncludePad = false;

    private int mWidth = 0;

    private TextPaint mPaint;
    private Editable editable;

    public LayoutBuilder(String text){
        this.mText = new SpannableString(text);
        this.mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(Color.BLACK);
    }

    public LayoutBuilder setAlignment(Layout.Alignment alignment){
        this.mAlign = alignment;
        return this;
    }

    public LayoutBuilder setSpacingMult(float spacingMult){
        this.mSpacingMult = spacingMult;
        return this;
    }

    public LayoutBuilder setSpacingAdd(float spacingAdd){
        this.mSpacingAdd = spacingAdd;
        return this;
    }

    public LayoutBuilder setWidth(int width){
        this.mWidth = width;
        return this;
    }

    public LayoutBuilder setIncludePad(boolean includePad){
        this.mIncludePad = includePad;
        return this;
    }

    public LayoutBuilder setPaint(TextPaint paint){
        this.mPaint.set(paint);
        return this;
    }

    public DynamicLayout build(){
        mAlign = mAlign == null ? mAlign =Layout.Alignment.ALIGN_NORMAL : mAlign;
//        mText.
//        editable.
        LineHeightSpan lhs = new LineHeightSpan() {
            @Override
            public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
                fm.ascent -= 5;
                fm.descent-= 10;
            }
        };
        mText.setSpan(lhs,0,100, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return new DynamicLayout(mText,
                this.mPaint, this.mWidth, this.mAlign,
                this.mSpacingMult, this.mSpacingAdd, this.mIncludePad);
    }

}
