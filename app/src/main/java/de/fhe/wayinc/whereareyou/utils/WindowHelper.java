package de.fhe.wayinc.whereareyou.utils;

import android.os.Build;
import android.view.View;

import timber.log.Timber;

/*
        This class provides functions to manipulate the android window
 */
public class WindowHelper {

    //hides the UI
    public static void hideUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Timber.d("Hiding UI");

            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            Timber.w("Could not hide system UI: Android Version < Kit Kat");
        }
    }

    //shows the UI
    public static void showSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Timber.d("Showing UI");

            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            Timber.w("Could not show system UI: Android Version < Jelly Bean");
        }
    }
}
