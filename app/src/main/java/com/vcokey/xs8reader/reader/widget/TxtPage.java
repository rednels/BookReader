package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.vcokey.xs8reader.reader.util.PageTxtParser;

/**
 * Txt reader widget,not support rich text.
 * <p/>
 * Created by vcokey on 2015/8/27.
 *
 * @LICENCSE CC BY 4.0
 */
public class TxtPage extends View {

    private final static String TAG = "TxtPage";

    private boolean DEBUG = true;

    //    private Paint mTextPaint;
    private TextPaint mTextPaint;
    private int mTextColor;
    private int mBackgroudRes;
    private String mText = "";

    public TxtPage(Context context) {
        this(context, null);
    }

    public TxtPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TxtPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        Log.d(TAG, "TxtPage init");
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setSubpixelText(true);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        StaticLayout layout = new StaticLayout(mText,
                mTextPaint, getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL,
                1.0f, 4f, true);
        layout.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG, String.format("%s,fontspace %s, textwidth %s, width %s", mTextPaint.getFontMetricsInt().toString(),
                mTextPaint.getFontSpacing(),
                mTextPaint.measureText(mText),
                MeasureSpec.getSize(widthMeasureSpec)));
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mText = PageTxtParser.parsePager(mTextPaint, mText, width - getPaddingLeft() - getPaddingRight(),
                PageTxtParser.parseMaxLineCount(mTextPaint, height - getPaddingBottom() - getPaddingTop(),
                        1.0f, 4.0f));

    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    public void setTextColor(ColorStateList color) {
        mTextPaint.setColor(color.getColorForState(getDrawableState(), 0));
        invalidate();
    }

    public void setText(String text) {
        mText = text;
//        requestLayout();
//        invalidate();
    }

    public void setTextSize(int size) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        mTextPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics()));
    }

    public void setTypeface(Typeface tf) {
        if (mTextPaint.getTypeface() != tf) {
            mTextPaint.setTypeface(tf);

            requestLayout();
            invalidate();
        }
    }
}
