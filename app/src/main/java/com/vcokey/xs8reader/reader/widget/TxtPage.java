package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.vcokey.xs8reader.reader.util.PageTxtParser;
import com.vcokey.xs8reader.reader.util.RectUtils;

import java.lang.ref.WeakReference;
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

    private boolean mInLongPress = false;

    private static final int LONG_PRESS = 0X00000001;
    private static final long LONGPRESS_TIMEOUT = 1000;

    private Handler mHandler;
    private int touchSlop;
    private int mTouchSlopSquare;

    private int mWidth;
    private int mHeight;

    private Bitmap mMagnifierBitmap;
    private Canvas mMagnifierCanvas;

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
    PopupWindow popupWindow;
    private void init(Context context) {
        Log.d(TAG, "TxtPage init");
        mHandler = new GestureHandler(this);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/osp.ttf"));
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
        mTouchSlopSquare = touchSlop * touchSlop;

        post(new Runnable() {
            @Override
            public void run() {
                mMagnifierBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                mMagnifierCanvas = new Canvas(mMagnifierBitmap);
            }
        });

//        mMagnifierBitmap = Bitmap.createBitmap(200,100, Bitmap.Config.ARGB_8888);
//        mMagnifierCanvas = new Canvas(mMagnifierBitmap);

        popupWindow = new PopupWindow(200, 100);
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(0xffffff);
        popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_switch_thumb_material));
        popupWindow.setContentView(imageView);
    }

    float dx = 0, dy = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setSubpixelText(true);

        canvas.drawColor(0xffffff);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);

        if (mLayout != null) {
            mLayout.draw(canvas, path, mTextPaint, 0);
        }
        mTextPaint.setSubpixelText(true);

        for (int[] line : lines) {
            // TODO: 2015/8/30 依次循环画下划线，layout需提前渲染
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();
        mText = PageTxtParser.parsePager(mTextPaint, mText, mWidth,
                PageTxtParser.parseMaxLineCount(mTextPaint, mHeight,
                        mLineSpacing, mSpacingExtra));

//        mLayout.increaseWidthTo(mWidth);
    }

    float X, Y;

    List<int[]> lines = new ArrayList<>();

    RectF rectF = new RectF();
    int[] currentLine;
    UnderlineSpan span = new UnderlineSpan();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInLongPress = false;
                mHandler.removeMessages(LONG_PRESS);
                mHandler.sendEmptyMessageAtTime(LONG_PRESS, event.getDownTime() + LONGPRESS_TIMEOUT);
                X = event.getX();
                Y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = (int) (event.getX() - X);
                final int deltaY = (int) (event.getY() - Y);
                int distance = (deltaX * deltaX) + (deltaY * deltaY);

                if (!mInLongPress && distance > mTouchSlopSquare) {
                    mInLongPress = false;
                    mHandler.removeMessages(LONG_PRESS);
                }

                if (mInLongPress) {
                    dx = event.getX();
                    dy = event.getY();

                    rectF.set(X - getPaddingLeft(), Y - getPaddingTop(),
                            dx - getPaddingLeft(), dy - getPaddingTop());
                    currentLine = RectUtils.layoutPosition(rectF, mLayout);
                    rectF.set(RectUtils.setRectangle(X - getPaddingLeft(), Y - getPaddingTop(),
                            dx - getPaddingLeft(), dy - getPaddingTop(), false));
//                    if (distance > mTouchSlopSquare) {


                        ((ImageView) popupWindow.getContentView()).setImageBitmap(getBitmap((int) dx - getPaddingLeft(), calculateY((int) dy - getPaddingTop())));
                        popupWindow.update((int) dx - getPaddingLeft(), calculateY((int) dy - getPaddingTop()), 200, 100);
//                    }
                    ((Spannable) mLayout.getText()).setSpan(span, currentLine[2], currentLine[3], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    int start = Math.min(mLayout.getLineTop(currentLine[0]),mLayout.getLineTop(currentLine[1]));
                    int end = Math.max(mLayout.getLineBottom(currentLine[0]), mLayout.getLineBottom(currentLine[1]));
                    int left = getLeft();
                    int right = getRight();
//                    postInvalidate(left, start + getPaddingTop(), right, end + getPaddingTop());
                    postInvalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mInLongPress) {
                    mInLongPress = false;
                    popupWindow.dismiss();
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

    private Bitmap getBitmap(int x,int y){
        setDrawingCacheEnabled(true);
        Bitmap bitmap = this.getDrawingCache();
        bitmap = Bitmap.createBitmap(bitmap, x-100, y+50, 200, 100);
        setDrawingCacheEnabled(false);
        return bitmap;
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


    private int calculateY(int y){
        if (y <= popupWindow.getHeight() + getPaddingTop()){
//            popupWindow.dismiss();
            return y + popupWindow.getHeight() + 100;
        }
        return y - 100;
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
                    popupWindow.showAtLocation((View) weakReference.get().getParent(), Gravity.NO_GRAVITY,
                            (int)X - getPaddingLeft(), calculateY((int) Y - getPaddingTop()));
                    break;
            }
        }
    }
}
