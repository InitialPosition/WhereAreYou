package de.fhe.wayinc.whereareyou.model;

import android.support.annotation.Nullable;

/**
 * This class acts as a model for the saved images.
 * It saves all the data connected to an image, such as the path and displayed information.
 */

public class SavedImage {
    private String path;
    private String icon;
    private double savedTemp;
    private String savedLocation;
    private String savedLatLon;
    private String savedFact;
    private int textColor;
    private String date;

    /**
     * Default constructor
     * @param path The image path
     * @param icon The weather icon for the image
     * @param latLon The latitude / longitude
     * @param city The city the image was taken in
     * @param temp The temperature the image was taken in
     * @param fact A random fact for the day
     * @param textColor The color to display the text in
     * @param date The date the image was taken on
     */
    public SavedImage(String path, @Nullable String icon, @Nullable String latLon, @Nullable String city, @Nullable double temp, @Nullable String fact, @Nullable int textColor, String date) {
        this.path = path;
        this.icon = icon;
        this.savedLatLon = latLon;
        this.savedLocation = city;
        this.savedTemp = temp;
        this.savedFact = fact;
        this.textColor = textColor;
        this.date = date;
    }

    /**
     * Get the Path for the image
     * @return The image path
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the weather icon for the image
     * @return The weather icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Get the saved temperature for the image
     * @return The saved temperature
     */
    public double getSavedTemp() {
        return savedTemp;
    }

    /**
     * Get the saved city for this image
     * @return The saved city
     */
    public String getSavedLocation() {
        return savedLocation;
    }

    /**
     * Get the saved latitude / longitude for the image
     * @return The saved latitude / longitude
     */
    public String getSavedLatLon() {
        return savedLatLon;
    }

    /**
     * Get the saved fact for the image
     * @return The saved fact
     */
    public String getSavedFact() {
        return savedFact;
    }

    /**
     * Get the color to display the text in
     * @return The text color
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Get the date the image was taken on
     * @return The image date
     */
    public String getDate() {
        return date;
    }
}
