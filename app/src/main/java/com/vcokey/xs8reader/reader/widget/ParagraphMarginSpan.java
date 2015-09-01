package com.vcokey.xs8reader.reader.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by hongxiutianxiang on 2015/9/1.
 */
public class ParagraphMarginSpan implements LeadingMarginSpan.LeadingMarginSpan2 {

    private int mMargin;

    public ParagraphMarginSpan(int margin) {
        this.mMargin = margin;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return 1;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return first ? mMargin : 0;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

    }
}
