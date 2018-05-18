package de.fhe.wayinc.whereareyou.utils;

/*
        This class provides functions that help providing feedback to the user.
 */

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

public class FeedbackHelper {

    //vibrate the phone for a duration in ms
    public static void vibrate(Context ctx, int duration) {
        Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(duration);
        } else {
            Log.w("FeedbackHelper", "Could not vibrate phone: VIBRATOR_SERVICE returned null");
        }
    }
}