package com.vcokey.xs8reader.reader.graphics;

import android.graphics.Canvas;

/**
 * Created by hongxiu on 2015/9/10.
 */
public interface Layout {

    void draw(Canvas canvas);

    void drawPreview(Canvas canvas);

    void drawCurrent(Canvas canvas,float textTop);

    void drawNext(Canvas canvas);

    class TextLine{
        boolean firstLine;
        int position;

        public TextLine(boolean firstLine, int position) {
            this.firstLine = firstLine;
            this.position = position;
        }
    }
}
