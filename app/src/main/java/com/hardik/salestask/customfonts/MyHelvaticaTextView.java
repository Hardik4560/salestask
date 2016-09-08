package com.hardik.salestask.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class MyHelvaticaTextView extends TextView {

    public MyHelvaticaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyHelvaticaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHelvaticaTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Helvetica.otf");
            setTypeface(tf);
        }
    }

}