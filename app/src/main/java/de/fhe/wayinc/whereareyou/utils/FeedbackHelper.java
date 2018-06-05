package de.fhe.wayinc.whereareyou.utils;

/*
        This class provides functions that help providing feedback to the user.
 */

import android.content.Context;
import android.os.Vibrator;

import java.text.MessageFormat;

import timber.log.Timber;

public class FeedbackHelper {

    // vibrate the phone for a duration in ms
    public static void vibrate(Context ctx, int duration) {
        Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            Timber.d(MessageFormat.format("Vibrating phone for {0}ms", duration));
            v.vibrate(duration);
        } else {
            Timber.w("VIBRATOR_SERVICE returned null");
        }
    }
}