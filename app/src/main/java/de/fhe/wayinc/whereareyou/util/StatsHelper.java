package de.fhe.wayinc.whereareyou.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class StatsHelper {
    private final static String SHARED_PREFERENCES_FILE = "statFile";

    private final static String PREF_TAKEN_IMAGES = "images_taken";
    private final static String PREF_VISITED_CITIES = "cities_visited";
    private final static String PREF_SHARED_IMAGES = "images_shared";
    private final static String PREF_MYSTERY = "mystery_achievement";


    public static void resetStats(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear().apply();

        Timber.d("Stats reset");
    }

    public static void incrementImageStat(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        int newImageCount;
        if (sharedPreferences.contains(PREF_TAKEN_IMAGES)) {
            newImageCount = sharedPreferences.getInt(PREF_TAKEN_IMAGES, 0) + 1;
        } else {
            newImageCount = 1;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_TAKEN_IMAGES, newImageCount);
        editor.apply();

        Timber.d(MessageFormat.format("Incremented image counter to {0}", newImageCount));
    }

    public static boolean cityIsNew(Context ctx, String newCity) {
        List<String> citiesVisited = getCitiesAsList(ctx);
        if (citiesVisited.contains(newCity)) {
            Timber.d("City new: false (already in list)");
            return false;
        } else {
            Timber.d("City new: true (not in list)");
            return true;
        }
    }

    public static void addCityToStat(Context ctx, String newCity) {
        List<String> citiesVisited = getCitiesAsList(ctx);
        citiesVisited.add(newCity);
        saveListToSharedPreferences(ctx, citiesVisited);

        Timber.d(MessageFormat.format("Added ''{0}'' to city list", newCity));
    }

    private static List<String> getCitiesAsList(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        String csvList = sharedPreferences.getString(PREF_VISITED_CITIES, "");
        String[] items = csvList.split(",");

        return new ArrayList<>(Arrays.asList(items));
    }

    public static void unlockMysteryAchievement(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(PREF_MYSTERY, true).apply();
    }

    public static boolean mysteryAchievementUnlocked(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_MYSTERY)) {
            return sharedPreferences.getBoolean(PREF_MYSTERY, false);
        } else {
            return false;
        }
    }

    private static void saveListToSharedPreferences(Context ctx, List<String> list) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        StringBuilder csvList = new StringBuilder();
        for (String s : list) {
            csvList.append(s);
            csvList.append(",");
        }

        editor.putString(PREF_VISITED_CITIES, csvList.toString()).apply();
    }

    public static int getImageCount(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_TAKEN_IMAGES, 0);
    }

    public static int getCityCount(Context ctx) {
        List<String> cities = getCitiesAsList(ctx);
        return cities.size();
    }
}
