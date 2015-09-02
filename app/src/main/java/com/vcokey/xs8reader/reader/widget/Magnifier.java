package com.vcokey.xs8reader.reader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.hardware.display.DisplayManager;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.vcokey.xs8reader.R;

/**
 * Created by vcokey on 2015/9/2.
 */
public class Magnifier extends View {

    private Canvas mCanvas;
    private Paint mPaint;
    private Bitmap mBitmap;

    private DisplayMetrics dm;

    private Path mBoardPath;
    private Path mClipPath;

    private int mHeight;
    private int mWidth;

    private float mArcWidth = 5.f;

    public Magnifier(Context context) {
        this(context, 200, 100);
    }

    public Magnifier(Context context, int width, int height) {
        super(context);
        dm = getResources().getDisplayMetrics();
        mWidth = (int) getSize(width);
        mHeight = (int) getSize(height);

        mArcWidth = getSize(5);

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);
        createBoardPath();
    }

    private void createBoardPath() {
        mBoardPath = new Path();
        mBoardPath.moveTo(mArcWidth + 2, mArcWidth + 2);
//        RectF leftTopF = new RectF();
//        leftTopF.set(0+ 2,0+ 2,mArcWidth * 2+ 2,mArcWidth * 2+ 2);

        mBoardPath.addCircle(getSize(10) + 2,getSize(10) +2 , getSize(10), Path.Direction.CW);

//        mBoardPath.moveTo(mArcWidth+ 2,0+ 2);
//        mBoardPath.lineTo(mWidth - mArcWidth+ 2, 0+ 2);
//        leftTopF.set(mWidth - mArcWidth * 2+ 2, 0+ 2, mWidth+ 2, mArcWidth+ 2);
//        mBoardPath.addArc(leftTopF, 270, 90);
//        mBoardPath.moveTo(mWidth+ 2, mArcWidth+ 2);
//        mBoardPath.lineTo(mWidth+ 2, mHeight - mArcWidth - getSize(4)+ 2);
//        leftTopF.set(mWidth - mArcWidth * 2+ 2, mHeight - mArcWidth * 2 - + 2, mWidth+ 2, mHeight - getSize(4)+ 2);
//        mBoardPath.addArc(leftTopF, 0, 90);
//        mBoardPath.moveTo(mWidth - mArcWidth+ 2, mHeight - getSize(4)+ 2);
//        mBoardPath.lineTo(mWidth / 2 + getSize(3)+ 2, mHeight - getSize(4)+ 2);
//        mBoardPath.lineTo(mWidth / 2+ 2,mHeight+ 2);
//        mBoardPath.lineTo(mWidth / 2 - getSize(3)+ 2, mHeight - getSize(4)+ 2);
//        mBoardPath.lineTo(mArcWidth+ 2, mHeight - 4+ 2);
//        leftTopF.set(0+ 2, mHeight - mArcWidth * 2 - getSize(4)+ 2, mArcWidth * 2+ 2, mHeight+ 2 - getSize(4)+ 2);
//        mBoardPath.addArc(leftTopF, 90, 90);
//        mBoardPath.moveTo(0+ 2,mHeight - getSize(4) - mArcWidth+ 2);
//        mBoardPath.lineTo(0+ 2, mArcWidth+ 2);
        RectF rectF = new RectF(2,2,mWidth - 2,mHeight - 2);
        mBoardPath.addRoundRect(rectF, getSize(10), getSize(10), Path.Direction.CW);

        mClipPath = new Path();
        mClipPath.addRect(0, 0, mWidth, mHeight, Path.Direction.CW);
//        mClipPath.op(mClipPath, mBoardPath, Path.Op.XOR);
        BitmapShader shader = new BitmapShader(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_mylocation), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
//        mBoardPath.
    }

    private float getSize(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.drawColor(Color.GREEN);
////        canvas.clipPath(mBoardPath, Region.Op.INTERSECT);
////        canvas.drawBitmap(mBitmap, 0, 0, null);
//        canvas.drawPath(mBoardPath, mPaint);
//        mPaint.setColor(Color.TRANSPARENT);
        RectF rectF = new RectF(2,2,mWidth - 2,mHeight - 2);
        canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mWidth / 2,mHeight / 2), mPaint);
//        canvas.drawPath(mClipPath,mPaint);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),2,2,mPaint);
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth+ 4, mHeight + 4);
    }
}
