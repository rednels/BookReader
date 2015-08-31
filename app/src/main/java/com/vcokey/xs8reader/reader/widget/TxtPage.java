package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.vcokey.xs8reader.R;
import com.vcokey.xs8reader.reader.util.PageTxtParser;
import com.vcokey.xs8reader.reader.util.RectUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    private boolean mInLongPress = false;

    private static final int LONG_PRESS = 0X00000001;
    private static final long LONGPRESS_TIMEOUT = 1000;

    private Handler mHandler;
    private int touchSlop;
    private int mTouchSlopSquare;

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

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Log.e("", "Longpress detected");
//            startDrag(null,null,null,0);
        }

    });

    private void init(Context context) {
        Log.d(TAG, "TxtPage init");
        mHandler = new GestureHandler(this);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/osp.ttf"));
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
        mTouchSlopSquare = touchSlop * touchSlop;
    }

    float dx = 0, dy = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setSubpixelText(true);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);

        if (mLayout != null) {
            mLayout.draw(canvas, path, mTextPaint, 0);
        }
        mTextPaint.setSubpixelText(true);
//        DynamicLayout layout = new DynamicLayout(ssb,
//                mTextPaint, getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL,
//                mLineSpacing, mSpacingExtra, false);
//        layout.draw(canvas);
//        Rect rect = new Rect();
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mText = PageTxtParser.parsePager(mTextPaint, mText, width - getPaddingLeft() - getPaddingRight(),
                PageTxtParser.parseMaxLineCount(mTextPaint, height - getPaddingBottom() - getPaddingTop(),
                        mLineSpacing, mSpacingExtra));
    }

    float X, Y;

    List<int[]> lines = new ArrayList<>();

    RectF rectF = new RectF();
    int[] currentLine;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInLongPress = false;
                mHandler.removeMessages(LONG_PRESS);
                mHandler.sendEmptyMessageAtTime(LONG_PRESS, event.getDownTime() + LONGPRESS_TIMEOUT);
                X = event.getX();
                ;
                Y = event.getY();
                System.out.println(String.format("down %s:%s", X, Y));
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = (int) (event.getX() - X);
                final int deltaY = (int) (event.getY() - Y);
                int distance = (deltaX * deltaX) + (deltaY * deltaY);
                System.out.println(String.format("move %s:%s", event.getX(), event.getY()));
//                System.out.println(String.format("%s,%s,%s", mInLongPress, distance, mTouchSlopSquare));

                if (!mInLongPress && distance > mTouchSlopSquare) {
//                    System.out.println("removeMessages");
                    mInLongPress = false;
                    mHandler.removeMessages(LONG_PRESS);
                }

                if (mInLongPress) {
                    dx = event.getX();
                    dy = event.getY();
                    rectF.set(RectUtils.setRectangle(X-getPaddingLeft(), Y - getPaddingTop(),
                            dx - getPaddingLeft(), dy - getPaddingTop(), false));
                    currentLine = RectUtils.layoutPosition(rectF, mLayout);
//                    System.out.println(String.format("%s,%s,%s,%s",currentLine[0],currentLine[1],currentLine[2],currentLine[3]));

                    BackgroundColorSpan span = new BackgroundColorSpan(Color.RED);
                    ((Spannable) mLayout.getText()).setSpan(span, currentLine[2], currentLine[3], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    int start = deltaY < 0 ? mLayout.getLineBottom(currentLine[0]) : mLayout.getLineTop(currentLine[0]);
                    int end = deltaY < 0 ? mLayout.getLineBottom(currentLine[1]) : mLayout.getLineTop(currentLine[1]);
                    int left = getLeft();
                    int right = getRight();
                    postInvalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mInLongPress) {
//                    System.out.println("ACTION_CANCEL");
                    mInLongPress = false;
                    mHandler.removeMessages(LONG_PRESS);
                    lines.add(currentLine);
                    currentLine = null;
                    lines = RectUtils.unionRectanges(lines);
                    rectF.set(0, 0, 0, 0);
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
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

    
    private class GestureHandler extends Handler {
        WeakReference<TxtPage> weakReference;

        public GestureHandler(TxtPage page) {
            weakReference = new WeakReference<>(page);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_PRESS:
                    mInLongPress = true;
                    weakReference.get().getParent().requestDisallowInterceptTouchEvent(true);
                    break;
            }
        }
    }
}
