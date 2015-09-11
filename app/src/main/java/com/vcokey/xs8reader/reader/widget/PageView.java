package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.vcokey.xs8reader.reader.graphics.ReaderLayout;
import com.vcokey.xs8reader.reader.util.SettingHelper;

/**
 * 阅读页面
 * <p/>
 * Created by hongxiu on 2015/9/7.
 */
public class PageView extends View {

    public static final String TAG = "PageView";
    private Boolean DEBUG = true;

    private TextPaint mTextPaint;//正文画笔
    private Paint mTitlePaint;  //标题画笔
    private Paint mHeaderPaint; //页眉画笔（用来绘制书名或章节名）

    private DisplayMetrics dm;
//    private Layout mLayout;     //正文布局（其余文字使用绘图绘制）

    private int mWidth;
    private int mHeight;

    private boolean mChapter = false;
    private String mBookName;
    private String mChapterName;

    private Paint.FontMetrics mHeaderMetrics;
    private Paint.FontMetrics mTitleMetrics;
    private Paint.FontMetrics mTextMetrics;

    private float mFontWidth;
    private Bitmap batteryBitmap;

    private Bitmap mBitmap;

    private Canvas mCanvas;
    //TODO
    ReaderLayout mLayout;
    String sb;
    public PageView(Context context) {
        super(context);
        sb = "正则表达式的鼻祖或许可一直追溯到科学家对人类神经系统工作原理的早期研究。\n美国新泽西州的和出生在美国底特律的这两位神经生理方面的科学家，研究出了一种用数学方式来描述神经网络的新方法，他们创造性地将神经系统中的神经元描述成了小而简单的自动控制元，从而作出了一项伟大的工作革新。\n" +
                "在1956年,出生在被马克·吐温称为美国最美丽的城市之一的哈特福德市的一位名叫的数学科学家，他在和早期工作的基础之上，发表了一篇题目是《神经网事件的表示法》的论文，利用称之为正则集合的数学符号来描述此模型，引入了正则表达式的概念。正则表达式被作为用来描述其称之为正则集的代数”的一种表达式，因而采用了正则表达式这个术语。\n" +
                "之后一段时间，人们发现可以将这一工作成果应用于其他方面。就把这一成果应用于计算搜索算法的一些早期研究，是 Unix的主要发明人，也就是大名鼎鼎的Unix之父。Unix之父将此符号系统引入编辑器QED，然后是Unix上的编辑器ed，并最终引入grep。在其著作（中文版译作：精通正则表达式，已出到第三版）中对此作了进一步阐述讲解，如果你希望更多了解正则表达式理论和历史，推荐你看看这本书。";

        dm = getResources().getDisplayMetrics();
        initialize();
        mBitmap = Bitmap.createBitmap(dm.widthPixels,dm.heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        batteryBitmap = BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_lock_idle_charging);

        mLayout = new ReaderLayout(sb,dm.widthPixels,dm.heightPixels,mTextPaint);
        mLayout.setSetting(getContext());
    }

    public void setChapterInfo(String bookName, String chapterName, boolean isChapterStart) {
        mBookName = bookName;
        mChapterName = chapterName;
        mChapter = isChapterStart;
    }

    /**
     * 工具初始化
     * TODO - 笔刷还未设置，读取设置之类的
     */
    private void initialize() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(SettingHelper.obtain(getContext()).fontSize);
        mTextPaint.setColor(SettingHelper.obtain(getContext()).fontColor);
        mTitlePaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, dm));
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17, dm));
        mHeaderPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));

        mTextPaint.setTypeface(Typeface.MONOSPACE);

        mHeaderPaint.setColor(Color.GRAY);

        mHeaderPaint.setLinearText(true);

        mHeaderMetrics = mHeaderPaint.getFontMetrics();
        mTitleMetrics = mTitlePaint.getFontMetrics();
        mTextMetrics = mTextPaint.getFontMetrics();
        mFontWidth = mTextPaint.measureText("永");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //TODO - 绘制背景
        int padding = SettingHelper.obtain(getContext()).pagePadding;
        mCanvas.drawText(mChapter ? mBookName : mChapterName, padding, padding - mHeaderMetrics.ascent, mHeaderPaint);
        float titleHeight = mTitleMetrics.bottom - mTitleMetrics.top;
        float headerHeight = mHeaderMetrics.bottom - mHeaderMetrics.top;
        float bodyAdd = padding + padding;
        if (mChapter){
            bodyAdd += padding + titleHeight + headerHeight;
            mCanvas.drawText(mChapterName,padding,padding + titleHeight + headerHeight + padding,mTitlePaint );
        }
//
//        float bodyTop = bodyAdd + mTextMetrics.bottom - mTextMetrics.top;
//
//        mCanvas.drawText("efg哈哈哈哈阿斯蒂芬阿萨德浪费空间阿斯蒂芬阿斯蒂芬"
//        ,padding + 2*mFontWidth,bodyAdd + mTextMetrics.bottom - mTextMetrics.top,mTextPaint);
//        mCanvas.drawText("哈哈哈哈阿斯蒂芬阿萨德浪费空间阿斯蒂芬阿斯蒂芬"
//                , padding, bodyAdd + (mTextMetrics.bottom - mTextMetrics.top) * 2 + 5, mTextPaint);
        mCanvas.drawBitmap(batteryBitmap,padding,mHeight - padding * 2,mTextPaint);
        canvas.drawBitmap(mBitmap,0,0,null);
        mLayout.measurePage(mHeight,padding * 2 + headerHeight,padding + titleHeight,padding * 2);
        mLayout.drawCurPage(mCanvas,bodyAdd);
        canvas.drawBitmap(mBitmap,0,0,null);
    }

}
