package com.vcokey.xs8reader.reader.util;

import android.graphics.RectF;

/**
 * 用来处理坐标运算，集合运算等工具类
 *
 * Created by vcokey on 2015/8/29.
 */
public class RectUtils {

    /**
     * 检测指定的两个矩形是否相交
     *
     * @param origin
     * @param dest
     * @return true,相交;false,不相交
     */
    public static boolean isIntersect(RectF origin, RectF dest){
        return RectF.intersects(origin,dest);
    }


}
