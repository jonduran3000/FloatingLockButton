package com.jonathanduran.floatinglockbutton;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by jonathanduran on 10/25/14.
 */
public class Util {
    private static Typeface bold;
    private static Typeface boldItalic;
    private static Typeface italic;
    private static Typeface light;
    private static Typeface lightItalic;
    private static Typeface regular;

    private static final String BOLD = "RobotoCondensed-Bold.ttf";
    private static final String BOLD_ITALIC = "RobotoCondensed-BoldItalic.ttf";
    private static final String ITALIC = "RobotoCondensed-Italic.ttf";
    private static final String LIGHT = "RobotoCondensed-Light.ttf";
    private static final String LIGHT_ITALIC = "RobotoCondensed-LightItalic.ttf";
    private static final String REGULAR = "RobotoCondensed-Regular.ttf";

    public static void loadTypefaces(Context context) {
        bold = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", BOLD));
        boldItalic = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", BOLD_ITALIC));
        italic = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", ITALIC));
        light = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", LIGHT));
        lightItalic = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", LIGHT_ITALIC));
        regular = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                String.format("fonts/%s", REGULAR));
    }

    public static Typeface getBold() {
        return bold;
    }

    public static Typeface getBoldItalic() {
        return boldItalic;
    }

    public static Typeface getItalic() {
        return italic;
    }

    public static Typeface getLight() {
        return light;
    }

    public static Typeface getLightItalic() {
        return lightItalic;
    }

    public static Typeface getRegular() {
        return regular;
    }
}
