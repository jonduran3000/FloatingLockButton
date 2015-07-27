package com.jonathanduran.floatinglockbutton.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

/**
 * Created by jonathanduran on 7/26/15.
 */
public final class FontCache {
    private static final SimpleArrayMap<String, Typeface> fontsByName = new SimpleArrayMap<>();
    private static final String DIR = "fonts/";

    private FontCache() {
        throw new AssertionError("No instances.");
    }

    public static Typeface getFont(@NonNull Context context, @NonNull String fontName) {
        if (fontsByName.containsKey(fontName)) {
            return fontsByName.get(fontName);
        } else {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), DIR + fontName);
            fontsByName.put(fontName, typeface);
            return typeface;
        }
    }
}
