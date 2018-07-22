package de.fhe.wayinc.whereareyou.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private List<Bitmap> thumbnailList = new ArrayList<>();
    private Context ctx;

    private static final int GALLERY_SIZE = 250;
    private static final int GALLERY_PADDING = 16;

    public GalleryAdapter(Context ctx, @Nullable List<Bitmap> imageList) {
        this.ctx = ctx;

        if (imageList != null) {
            this.thumbnailList = imageList;
        }
    }

    public void setList(List<Bitmap> list) {
        this.thumbnailList = list;
    }

    @Override
    public int getCount() {
        return thumbnailList.size();
    }

    @Override
    public Object getItem(int position) {
        return thumbnailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void removeItem(int position) {
        thumbnailList.remove(position);
        notifyDataSetChanged();
    }

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
        currentImageView.setImageBitmap(thumbnailList.get(position));

        return currentImageView;
    }
}
