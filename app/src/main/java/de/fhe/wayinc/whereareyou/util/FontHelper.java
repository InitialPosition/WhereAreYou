package de.fhe.wayinc.whereareyou.util;

/**
 * This class provides functions for font manipulation.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.text.MessageFormat;

import timber.log.Timber;

public class FontHelper {

    /**
     * Set the font of a given TextView to an external font
     * @param ctx The application context
     * @param path The path to the font file
     * @param view The TextView to set the font of
     */
    public static void setExternalFont(Context ctx, String path, TextView view) {
        Timber.d(MessageFormat.format("Setting font of {0} to {1}", view, path));
        try {
            Typeface tf = Typeface.createFromAsset(ctx.getAssets(), path);
            view.setTypeface(tf);
        } catch (RuntimeException e) {
            Timber.e(MessageFormat.format("Error setting Font in ImageView: {0}", e));
        }
    }
}
