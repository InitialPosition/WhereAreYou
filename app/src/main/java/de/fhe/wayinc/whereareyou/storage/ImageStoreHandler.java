package de.fhe.wayinc.whereareyou.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class ImageStoreHandler {
    private List<String> imageList = new ArrayList<>();
    private static final String LIST_SAVE_FILE = "images.txt";

    public ImageStoreHandler(@Nullable List<String> imageList) {
        if (imageList != null) {
            this.imageList = imageList;
        }
    }

    public int getImageListSize() {
        return imageList.size();
    }

    public String getImageFromImageList(int index) {
        Timber.d(MessageFormat.format("Requested image {0}", index));
        return imageList.get(index);
    }

    public void saveImageToImageList(File image) {
        String imagePath = image.getAbsolutePath();
        Timber.d(MessageFormat.format("Saving image path {0}", imagePath));
        imageList.add(imagePath);
    }

    public void clearImageList() {
        Timber.d("Image list cleared");
        imageList.clear();
    }

    public void writeOutImageList(Context ctx) {
        Timber.d("Saving image list to hard drive...");
        try {
            String data = imageListToString();

            if (imageList.size() == 0) {
                data = "";
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(LIST_SAVE_FILE, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Timber.e(MessageFormat.format("File write failed: {0}", e.toString()));
        }
    }

    public void loadImageListFromDisk(Context ctx) {
        Timber.d("Loading image list...");
        clearImageList();

        String ret = "";
        try {
            InputStream inputStream = ctx.openFileInput(LIST_SAVE_FILE);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Timber.e(MessageFormat.format("File not found: {0}", e.toString()));
        } catch (IOException e) {
            Timber.e(MessageFormat.format("Can not read file: {0}", e.toString()));
        }

        Timber.d(MessageFormat.format("Decoded imageList: {0}", ret));
        String[] items = ret.split(",");
        imageList.addAll(Arrays.asList(items));
        if (imageList.get(0).contentEquals("")) {
            imageList.remove(0);
        }

        Timber.d(MessageFormat.format("Image list loaded, contains {0} items", imageList.size()));
    }

    public void removeImageFromList(int position) {
        Timber.i(MessageFormat.format("Removing image {0}...", position));
        imageList.remove(position);
    }

    private String imageListToString() {
        StringBuilder csvList = new StringBuilder();
        for (String s : imageList) {
            csvList.append(s);
            csvList.append(",");
        }

        return csvList.toString();
    }

    public boolean isImageOnList(File image) {
        String imagePath = image.getAbsolutePath();

        for (String path : imageList) {
            if (path.equals(imagePath)) {
                Timber.d(MessageFormat.format("{0} found on image list", imagePath));
                return true;
            }
        }
        Timber.d(MessageFormat.format("{0} not found on image list", imagePath));
        return false;
    }

    public List<Bitmap> getImageListAsScaledBitmaps(int width, int height) {
        List<Bitmap> convertedList = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap, bitmapScaled;

        for (String s : imageList) {
            bitmap = BitmapFactory.decodeFile(s);
            bitmapScaled = Bitmap.createScaledBitmap(bitmap, width, height, false);

            convertedList.add(bitmapScaled);
        }

        return convertedList;
    }
}
