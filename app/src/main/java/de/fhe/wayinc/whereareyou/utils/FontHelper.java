package de.fhe.wayinc.whereareyou.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontHelper {
    public static void loadExternalTypefaceIntoTextView(String path, TextView view, Context ctx) {
        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), path);
        view.setTypeface(tf);
    }
}
