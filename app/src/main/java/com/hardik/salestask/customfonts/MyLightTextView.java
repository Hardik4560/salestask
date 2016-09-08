package com.hardik.salestask.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class MyLightTextView extends TextView {

    public MyLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLightTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Thin.ttf");
            setTypeface(tf);
        }
    }

}