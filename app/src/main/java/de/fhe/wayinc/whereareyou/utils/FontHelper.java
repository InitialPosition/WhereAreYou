package de.fhe.wayinc.whereareyou.utils;

/*
        This class provides functions for font manipulation.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontHelper {

    //set the font of a given TextView to an external font
    public static void setExternalFont(Context ctx, String path, TextView view) {
        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), path);
        view.setTypeface(tf);
    }
}
