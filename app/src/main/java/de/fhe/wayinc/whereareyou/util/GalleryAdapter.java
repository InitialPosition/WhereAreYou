package de.fhe.wayinc.whereareyou.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an extended BaseAdapter for the gallery view.
 */

public class GalleryAdapter extends BaseAdapter {
    private List<Bitmap> thumbnailList = new ArrayList<>();
    private Context ctx;

    private static final int GALLERY_SIZE = 250;
    private static final int GALLERY_PADDING = 16;

    /**
     * Create a new gallery adapter
     * @param ctx The application context
     * @param imageList An image list to load into the adapter
     */
    public GalleryAdapter(Context ctx, @Nullable List<Bitmap> imageList) {
        this.ctx = ctx;

        if (imageList != null) {
            this.thumbnailList = imageList;
        }
    }

    /**
     * Set the image list of the adapter
     * @param list The image list to use
     */
    public void setList(List<Bitmap> list) {
        this.thumbnailList = list;
    }

    /**
     * Get how many images are in the list
     * @return The number of images
     */
    @Override
    public int getCount() {
        return thumbnailList.size();
    }

    /**
     * Get a specific image item
     * @param position The position of the image
     * @return A reference to the image
     */
    @Override
    public Object getItem(int position) {
        return thumbnailList.get(position);
    }

    /**
     * Get the ID for a given position
     * @param position The position of the image
     * @return The image ID
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Remove an item from the list
     * @param position The item to remove
     */
    public void removeItem(int position) {
        thumbnailList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * Get a new view for an image or recycle if available
     * @param position The position of the new image
     * @param convertView The convertview to use
     * @param parent The parent object
     * @return The new / recycled view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView currentImageView;

        if (convertView == null) {
            currentImageView = new ImageView(ctx);
            currentImageView.setLayoutParams(new ViewGroup.LayoutParams(GALLERY_SIZE, GALLERY_SIZE));
            currentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            currentImageView.setPadding(GALLERY_PADDING, GALLERY_PADDING, GALLERY_PADDING, GALLERY_PADDING);
        } else {
            currentImageView = (ImageView) convertView;
        }
        Glide.with(parent)
                .load(thumbnailList.get(position))
                .into(currentImageView);

        return currentImageView;
    }
}
