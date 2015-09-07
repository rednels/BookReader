package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View;

/**
 * 阅读页面
 *
 * Created by hongxiu on 2015/9/7.
 */
public class PageView extends View {

    public static final String TAG = "PageView";
    private Boolean DEBUG = true;

    private TextPaint mTextPaint;//正文画笔
    private Paint mTitlePaint;  //标题画笔
    private Paint mHeaderPaint; //页眉画笔（用来绘制书名或章节名）

//    private Layout mLayout;     //正文布局（其余文字使用绘图绘制）

    private int mWidth;
    private int mHeight;

    public PageView(Context context) {
        super(context);
        initialize();
    }

    /**
     * 工具初始化
     */
    private void initialize() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}
