package de.fhe.wayinc.whereareyou.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

/**
 * This class keeps track of stats and achievements
 */

public class StatsHelper {
    private final static String SHARED_PREFERENCES_FILE = "statFile";

    private final static String PREF_TAKEN_IMAGES = "images_taken";
    private final static String PREF_VISITED_CITIES = "cities_visited";
    private final static String PREF_SHARED_IMAGES = "images_shared";
    private final static String PREF_MYSTERY = "mystery_achievement";

    /**
     * Reset all the stats
     * @param ctx The application context
     */
    public static void resetStats(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear().apply();

        Timber.d("Stats reset");
    }

    /**
     * Add one to the image counter
     * @param ctx The application context
     */
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

    /**
     * Check whether a city is already counted as visited
     * @param ctx The application context
     * @param newCity The city to check
     * @return Whether the city is already counted
     */
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

    /**
     * Add a new city to the city list
     * @param ctx The application context
     * @param newCity The city to add
     */
    public static void addCityToStat(Context ctx, String newCity) {
        List<String> citiesVisited = getCitiesAsList(ctx);
        citiesVisited.add(newCity);
        saveListToSharedPreferences(ctx, citiesVisited);

        Timber.d(MessageFormat.format("Added ''{0}'' to city list", newCity));
    }

    /**
     * Get all the saved cities as a list
     * @param ctx The application context
     * @return The city list
     */
    private static List<String> getCitiesAsList(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        String csvList = sharedPreferences.getString(PREF_VISITED_CITIES, "");
        String[] items = csvList.split(",");

        return new ArrayList<>(Arrays.asList(items));
    }

    /**
     * Unlocks the mystery achievement
     * @param ctx The application context
     */
    public static void unlockMysteryAchievement(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(PREF_MYSTERY, true).apply();
    }

    /**
     * Checks whether the mystery achievement is unlocked
     * @param ctx The application context
     * @return Whether the achievement is unlocked
     */
    public static boolean mysteryAchievementUnlocked(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_MYSTERY)) {
            return sharedPreferences.getBoolean(PREF_MYSTERY, false);
        } else {
            return false;
        }
    }

    /**
     * Write out the stats to hard drive
     * @param ctx The application context
     * @param list The list to write out
     */
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

    /**
     * Get the current image count
     * @param ctx The application context
     * @return The number of images taken
     */
    public static int getImageCount(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_TAKEN_IMAGES, 0);
    }

    /**
     * Get the current city count
     * @param ctx The application context
     * @return The number of cities visited
     */
    public static int getCityCount(Context ctx) {
        List<String> cities = getCitiesAsList(ctx);
        return cities.size();
    }
}
