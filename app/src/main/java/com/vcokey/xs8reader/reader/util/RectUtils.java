package com.vcokey.xs8reader.reader.util;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来处理坐标运算，集合运算等工具类
 * <p/>
 * // TODO: 2015/8/30  如果下划线在页末，当调大字体或间距后，一部分被挤压到下一页，这时需重新计算当页下划线集合以及下页下划线集合
 * // TODO: 2015/8/30 还有从上页挤压过来的下划线也要考虑
 * Created by vcokey on 2015/8/29.
 */
public class RectUtils {

    /**
     * 检测指定的两个矩形是否相交,区别于{@link android.graphics.RectF#intersects(RectF, RectF)}<br/>
     * 即使边缘相切也算作相交
     *
     * @param a
     * @param b
     * @return true, 相交;false,不相交
     */
    public static boolean isIntersect(RectF a, RectF b) {
        return a.left <= b.right && b.left <= a.right
                && a.top <= b.bottom && b.top <= a.bottom;
    }

    /**
     * 根据触摸点起止坐标，设置矩形实际坐标
     *
     * @param sx          起点X坐标
     * @param sy          起点Y坐标
     * @param dx          终点X坐标
     * @param dy          重点Y坐标
     * @param distinguish 是否主动避免产生空矩形
     * @return 手指绘制的矩形的坐标
     */
    public static RectF setRectangle(float sx, float sy, float dx, float dy, boolean distinguish) {
        RectF rectF = new RectF();

        /**
         * 因为如果坐标出现相同的，就被认为是空矩形，所以+0.1区分
         */
        if (distinguish && sx == sy) {
            sx += 0.1;
        }

        if (distinguish && sy == dy) {
            dy += 0.1;
        }

        rectF.set(
                Math.min(sx, dx),
                Math.min(sy, dy),
                Math.max(sx, dx),
                Math.max(sy, dy)
        );

        return rectF;
    }

    /**
     * 判断是否不存在
     *
     * @param rectF
     * @return
     */
    public static boolean isNone(RectF rectF) {
        return rectF.left == rectF.right && rectF.top == rectF.bottom;
    }

    /**
     * 两下划线标记是否相交
     *
     * @param a
     * @param b
     * @return
     */
    static boolean isLineIntersect(int[] a, int[] b) {
        return a[4] <= b[3];
    }

    /**
     * 将列表中的相交的下划线，进行合并
     *
     * @param underlines
     * @return 合并后下划线集合
     */
    public static List<int[]> unionRectanges(@NonNull List<int[]> underlines) {
        int count = underlines.size();

        if (count == 0) {
            return underlines;
        }

        List<int[]> newLines = new ArrayList<>();
        int[] temp = underlines.get(0);
        for (int i = 1; i < count; i++) {
            int[] next = underlines.get(i);
            if (isLineIntersect(temp, next)) {
                temp[4] = Math.max(temp[4], next[4]);
            } else {
                newLines.add(temp);
                temp = next;
            }
        }
        return newLines;
    }

    /**
     * 计算划线经过的矩形所划选的文本
     *
     * @param rectF  划线形成的矩形
     * @param layout 文本布局
     * @return 下划线数组 int[]{lineStart, lineEnd, characterStart, characterEnd}
     * <p/>
     * 即，从N行到第M行中，整段文本的第I个字符到第J个字符
     */
    public static int[] layoutPosition(RectF rectF, Layout layout) {
        int lineStart = layout.getLineForVertical((int) rectF.top);
        int lineEnd = layout.getLineForVertical((int) rectF.bottom);

        int characterStart = layout.getOffsetForHorizontal(lineStart, rectF.left);
        int characterEnd = layout.getOffsetForHorizontal(lineStart, rectF.right);

        return new int[]{lineStart, lineEnd, characterStart, characterEnd};

    }
}
