package de.fhe.wayinc.whereareyou.model;

import android.graphics.Color;
import android.support.annotation.Nullable;

public class SavedImage {
    private String path;
    private String icon;
    private double savedTemp;
    private String savedLocation;
    private String savedLatLon;
    private String savedFact;
    private int textColor;

    public SavedImage(String path, @Nullable String icon, @Nullable String latLon, @Nullable String city, @Nullable double temp, @Nullable String fact, @Nullable int textColor) {
        this.path = path;
        this.icon = icon;
        this.savedLatLon = latLon;
        this.savedLocation = city;
        this.savedTemp = temp;
        this.savedFact = fact;
        this.textColor = textColor;
    }

    public String getPath() {
        return path;
    }

    public String getIcon() {
        return icon;
    }

    public double getSavedTemp() {
        return savedTemp;
    }

    public String getSavedLocation() {
        return savedLocation;
    }

    public String getSavedLatLon() {
        return savedLatLon;
    }

    public String getSavedFact() {
        return savedFact;
    }

    public int getTextColor() {
        return textColor;
    }
}
