package de.fhe.wayinc.whereareyou.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.model.SavedImage;
import timber.log.Timber;

public class ImageStoreHandler {

    private final static String SHARED_PREFERENCES_FILE = "images";
    private final static String SHARED_PREFERENCES_KEY = "images_key";

    private List<SavedImage> imageList = new ArrayList<>();

    public int getImageListSize() {
        return imageList.size();
    }

    public String getImagePathFromImageList(int index) {
        Timber.d(MessageFormat.format("Requested image {0}", index));
        return imageList.get(index).getPath();
    }

    public SavedImage getImageObjectFromImageList(int index) {
        Timber.d(MessageFormat.format("Requested image {0}", index));
        return imageList.get(index);
    }

    public void saveImageToImageList(File image, String icon, String latLon, String city, double temp, String fact, int color) {
        String imagePath = image.getAbsolutePath();
        Timber.d(MessageFormat.format("Saving image path {0}", imagePath));

        SavedImage newImage = new SavedImage(imagePath, icon, latLon, city, temp, fact, color);
        imageList.add(newImage);
    }

    public void clearImageList() {
        Timber.d("Image list cleared");
        imageList.clear();
    }

    public void writeOutImageList(Context ctx) {
        Timber.d("Saving image list to hard drive...");

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHARED_PREFERENCES_KEY, imageListToString()).apply();
    }

    public void loadImageListFromDisk(Context ctx) {
        Timber.d("Loading image list...");
        clearImageList();

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        String listJson = sharedPreferences.getString(SHARED_PREFERENCES_KEY, "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<List<SavedImage>>() {
        }.getType();
        imageList = gson.fromJson(listJson, type);
    }

    public void removeImageFromList(int position) {
        Timber.i(MessageFormat.format("Removing image {0}...", position));

        SavedImage image = imageList.get(position);
        File imageFile = new File(image.getPath());
        if (imageFile.exists()) {
            imageFile.delete();
            Timber.d("Image file deleted");
        }

        imageList.remove(position);
    }

    private String imageListToString() {
        Gson gson = new Gson();

        return gson.toJson(imageList);
    }

    public boolean isImageOnList(File image) {
        String imagePath = image.getAbsolutePath();

        if (imageList != null) {
            for (SavedImage path : imageList) {
                if (path.getPath().equals(imagePath)) {
                    Timber.d(MessageFormat.format("{0} found on image list", imagePath));
                    return true;
                }
            }
        } else {
            Timber.d("Image list null");
            return false;
        }
        Timber.d(MessageFormat.format("{0} not found on image list", imagePath));
        return false;
    }

    public List<Bitmap> getImageListAsScaledBitmaps(int width, int height) {
        List<Bitmap> convertedList = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap, bitmapScaled;

        if (imageList != null) {
            for (SavedImage s : imageList) {
                bitmap = BitmapFactory.decodeFile(s.getPath());
                bitmapScaled = Bitmap.createScaledBitmap(bitmap, width, height, false);

                convertedList.add(bitmapScaled);
            }
        }

        return convertedList;
    }
}
