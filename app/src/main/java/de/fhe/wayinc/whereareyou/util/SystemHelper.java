package de.fhe.wayinc.whereareyou.util;

/*
        This class provides functions from system calls
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;

public class SystemHelper {

    // get the current time stamp
    private static String getCurrentTimeStamp() {
        Long timestamp = System.currentTimeMillis() / 1000;
        return timestamp.toString();
    }

    // gets a random background for the main menu
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getRandomBackgroundDrawable(Context ctx) {
        List<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.mm_bg_0);
        drawables.add(R.drawable.mm_bg_1);
        drawables.add(R.drawable.mm_bg_2);
        drawables.add(R.drawable.mm_bg_3);
        drawables.add(R.drawable.mm_bg_4);
        drawables.add(R.drawable.mm_bg_6);
        drawables.add(R.drawable.mm_bg_7);
        drawables.add(R.drawable.mm_bg_8);
        drawables.add(R.drawable.mm_bg_9);
        drawables.add(R.drawable.mm_bg_10);

        int randomDrawable = MathHelper.randomInt(0, drawables.size() - 1);
        if (MathHelper.randomInt(0, 1000) < 1) {
            return ctx.getDrawable(R.drawable.mm_bg_11);
        } else {
            return ctx.getDrawable(drawables.get(randomDrawable));
        }
    }
}
