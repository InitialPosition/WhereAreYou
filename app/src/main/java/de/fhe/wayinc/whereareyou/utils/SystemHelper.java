package de.fhe.wayinc.whereareyou.utils;

/*
        This class provides functions from system calls
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

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

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    // adds a file to the gallery
    public static void galleryAddPic(Context ctx, File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }
}
