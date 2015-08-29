package com.vcokey.xs8reader.reader.util;

import android.graphics.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本排版解析器
 *
 * Created by vcokey on 2015/8/28.
 */
public class PageTxtParser {

    /**
     * 解析每页字符串最大行数
     *
     * @param paint       画笔
     * @param height      页面高度
     * @param spacingMult 行倍距
     * @param spacingAdd  附加行距
     * @return 页面最大行数，不计算半行
     */
    public static int parseMaxLineCount(Paint paint, int height, float spacingMult, float spacingAdd) {
        int textHeight;
        float lineHeight;
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        textHeight = Math.abs(fm.ascent) + Math.abs(fm.descent) + Math.abs(fm.leading);

        lineHeight = textHeight * spacingMult + spacingAdd;

        return (int) ((height + lineHeight - textHeight) / lineHeight);
    }

    /**
     * 解析获取每页可显示的字符串，处理回车/换行算一行<br/>
     * TODO - 未处理长单词，中间加连词符 <br/>
     *
     * ps:长单词的几种情况--<br/>
     * <li>1.|-------123456|    长数字</li>
     * <li>2.|-------abcdef|    长英文单词</li>
     * <li>3.|-------123abc|    先数字 后字母</li>
     * <li>4.|-------abc123|    先字母 后数字</li>
     * <li>5.|------------a|    长单词只有一个字符在上一行末尾</li>
     *
     * @param paint 画笔
     * @param text  文本
     * @param width 行宽
     * @param lineCount 行数
     *
     * @return 每页的字符串
     */
    public static String parsePager(Paint paint, String text, int width, int lineCount) {
        StringBuffer sb = new StringBuffer();
        int lastCharacterCount;
        int position = 0;
        Pattern pattern = Pattern.compile("[\r\n]+");
        for (int i = 0; i < lineCount; i++) {
            lastCharacterCount = paint.breakText(text, position,
                    text.length(), true, width, null);

            String sub = text.substring(position, position + lastCharacterCount);

            Matcher matcher = pattern.matcher(sub);
            if (matcher.find()){
                lastCharacterCount = matcher.start() + 1;
            }

            position += lastCharacterCount;
        }

        return text.substring(0, position);
    }

    /**
     * 计算最小值
     *
     * @param numbers 待比较的数
     * @return 最小值
     */
    public static float min(float[] numbers) {
        float min = 0;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers.length == 1) {
                min = numbers[0];
            } else {
                min = Math.min(numbers[i], numbers[i + 1]);
            }
        }
        return min;
    }

    /**
     * 获取已知宽度下能容纳的最大字符个数
     *
     * @param width   行宽
     * @param testStr 待测试填充的字符序列
     * @return 在该宽度下，字符的最大个数
     */
    public static int maxCountInLine(Paint paint, int width, String testStr) {
        float[] characterWidths = new float[testStr.length()];
        paint.getTextWidths(testStr, characterWidths);
        return (int) (width / min(characterWidths) + 1);
    }
}
