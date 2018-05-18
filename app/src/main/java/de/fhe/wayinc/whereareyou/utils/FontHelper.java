package de.fhe.wayinc.whereareyou.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontHelper {
    public static void loadExternalTypefaceIntoTextView(Context ctx, String path, TextView view) {
        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), path);
        view.setTypeface(tf);
    }
}
