package com.jonathanduran.floatinglockbutton.ui.misc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.jonathanduran.floatinglockbutton.R;
import com.jonathanduran.floatinglockbutton.utils.FontCache;

/**
 * Created by jonathanduran on 7/26/15.
 */
public class CustomFontTextView extends AppCompatTextView {
    public CustomFontTextView(Context context) {
        this(context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (attrs != null) {
            TypedArray a = context.getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.CustomFontTextView, 0, 0);
            try {
                Typeface font =
                        FontCache.getFont(context, a.getString(R.styleable.CustomFontTextView_font));
                setTypeface(font);
            } finally {
                a.recycle();
            }
        }
    }
}
