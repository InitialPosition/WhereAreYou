package de.fhe.wayinc.whereareyou.models;

public class SavedImage {
    private String path;
    private boolean iconEnabled, latLonEnabled, cityEnabled, tempTextEnabled;

    public SavedImage(String path, boolean iconEnabled, boolean latLonEnabled, boolean cityEnabled, boolean tempTextEnabled) {
        this.path = path;
        this.iconEnabled = iconEnabled;
        this.latLonEnabled = latLonEnabled;
        this.cityEnabled = cityEnabled;
        this.tempTextEnabled = tempTextEnabled;
    }

    public String getPath() {
        return path;
    }

    public boolean isIconEnabled() {
        return iconEnabled;
    }

    public boolean isLatLonEnabled() {
        return latLonEnabled;
    }

    public boolean isCityEnabled() {
        return cityEnabled;
    }

    public boolean isTempTextEnabled() {
        return tempTextEnabled;
    }
}
