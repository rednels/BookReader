package com.vcokey.xs8reader.reader.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Html reader ,support html text;
 * <p/>
 * Created by vcokey on 2015/8/27.
 *
 * @LICENCSE CC BY 4.0
 */
public class HtmlPage extends View{

    public HtmlPage(Context context) {
        super(context);
    }

    public HtmlPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HtmlPage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
