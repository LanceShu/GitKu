package com.example.lance.playertest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lance on 16-10-2.
 */
public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isFocused(){
        return true;
    }
}
