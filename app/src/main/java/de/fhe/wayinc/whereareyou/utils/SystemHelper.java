package de.fhe.wayinc.whereareyou.utils;

/*
        This class provides functions from system calls
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;

public class SystemHelper {

    // get the current time stamp
    private static String getCurrentTimeStamp() {
        Long timestamp = System.currentTimeMillis() / 1000;
        return timestamp.toString();
    }

    // creates a new image file handle and returns it
    public static File createImageFile(Context ctx) throws IOException {
        String timeStamp = getCurrentTimeStamp();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return new File(storageDir, MessageFormat.format("{0}.jpg", imageFileName));
    }

    // adds a file to the gallery
    public static void galleryAddPic(Context ctx, File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
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
        drawables.add(R.drawable.mm_bg_5);
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
