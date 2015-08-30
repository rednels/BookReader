package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.vcokey.xs8reader.reader.util.PageTxtParser;
import com.vcokey.xs8reader.reader.util.RectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Txt reader widget,not support rich text.
 * <p/>
 * Created by vcokey on 2015/8/27.
 *
 * @LICENCSE Apache License Version 2.0
 */
public class TxtPage extends View {

    private final static String TAG = "TxtPage";

    private boolean DEBUG = true;

    //    private Paint mTextPaint;
    private TextPaint mTextPaint;
    private Layout mLayout;

    private float mLineSpacing = 1.5f;
    private float mSpacingExtra = 0f;

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
        mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/osp.ttf"));
    }

    float dx = 0, dy = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setSubpixelText(true);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        Path path = new Path();
        path.moveTo(getPaddingLeft(), getPaddingTop());
        path.lineTo(100, getPaddingTop());

        if (mLayout != null) {
            mLayout.draw(canvas, path, mTextPaint, 0);
        }
        mTextPaint.setSubpixelText(true);
        DynamicLayout layout = new DynamicLayout(mText,
                mTextPaint, getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL,
                mLineSpacing, mSpacingExtra, false);
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay().getMetrics(dm);
        mTextPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, dm));
//        layout.
        layout.draw(canvas, path, mTextPaint, 100);
        Rect rect = new Rect();
//        int bottom = layout.getLineBounds(0, rect); //获取第N行所在的矩形坐标，并返回该行baseline所在的坐标
//        int b = layout.getLineBottom(0);            //获取第N行，底部位置坐标，和rect.bottom相同
//        int left = layout.getLineDescent(0) / 2;    //获取baseline和bottom之间的距离
//        int right = layout.getOffsetForHorizontal(1, 20);    //获取第一行X坐标为20的点所在的字符在字符串中的位置
//        float a = layout.getPrimaryHorizontal(1);           //获取字符串中第N个字符的左边界坐标
//        float c = layout.getSecondaryHorizontal(10);        //获取字符串中第N个字符的右边界坐标

        for (int[] line : lines) {
            // TODO: 2015/8/30 依次循环画下划线，layout需提前渲染
        }


//        int line = layout.getLineForVertical((int) rectF.bottom);
//
//        int bottom = layout.getLineBaseline(line);
//        int decent = layout.getLineDescent(line);
//        int left = layout.getOffsetForHorizontal(line, rectF.left);
//        int right = layout.getOffsetForHorizontal(line, rectF.right);
//
//        float leftX = layout.getPrimaryHorizontal(left);
//        float rightX = layout.getPrimaryHorizontal(right + 1);
//        System.out.println(String.format("%s,%s,%s",line,left,right));
//        canvas.drawLine(leftX, bottom + decent / 2, rightX, bottom + decent / 2, mTextPaint);
        canvas.restore();
    }

    SpannableStringBuilder ssb;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mText = PageTxtParser.parsePager(mTextPaint, mText, width - getPaddingLeft() - getPaddingRight(),
                PageTxtParser.parseMaxLineCount(mTextPaint, height - getPaddingBottom() - getPaddingTop(),
                        mLineSpacing, mSpacingExtra));
//        ssb = new SpannableStringBuilder(mText);
//        BackgroundColorSpan span = new BackgroundColorSpan(0xffff0000);
//
//        UnderlineSpan underlineSpan = new UnderlineSpan();
//        TextAppearanceSpan tas = new TextAppearanceSpan(getContext(),android.R.style.TextAppearance_Small,0xff000000);
//        ForegroundColorSpan fcs = new ForegroundColorSpan(0xffff0000);
//        QuoteSpan sts = new QuoteSpan(0xff00ff00);
//
//        BulletSpan bulletSpan = new BulletSpan(40,0x0000ff);
//        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
//
//        ScaleXSpan scaleXSpan = new ScaleXSpan(1.5f);
//        ssb.setSpan(scaleXSpan, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        ssb.setSpan(fcs, 0, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        ssb.setSpan(underlineSpan, 0, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    }

    int presstime = 0;
    float X, Y;

    List<int[]> lines = new ArrayList<>();

    RectF rectF = new RectF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presstime = 0;
                X = event.getX();
                Y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(X - event.getX()) < 1.5 && Math.abs(Y - event.getY()) < 1.5)
                    presstime += 1;
                if (presstime > 30) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    dx = event.getX();
                    dy = event.getY();
                    rectF.set(RectUtils.setRectangle(X, Y, dx, dy, false));
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (presstime > 30) {
                    lines.add(RectUtils.layoutPosition(rectF, mLayout));
                    lines = RectUtils.unionRectanges(lines);
                }
                rectF.set(0, 0, 0, 0);
                presstime = 0;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return true;
    }

    /**
     * 设置文本布局方式
     *
     * @param layout
     */
    public void setLayout(Layout layout) {
        this.mLayout = layout;
        mLineSpacing = layout.getSpacingMultiplier();
        mSpacingExtra = layout.getSpacingAdd();
        requestLayout();
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        if (isShown())
            invalidate();
    }

    public void setTextColor(ColorStateList color) {
        mTextPaint.setColor(color.getColorForState(getDrawableState(), 0));
        if (isShown())
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
