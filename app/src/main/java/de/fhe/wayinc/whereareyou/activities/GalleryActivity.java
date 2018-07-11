package de.fhe.wayinc.whereareyou.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.utils.GalleryAdapter;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageStoreHandler imageStoreHandler = new ImageStoreHandler(null);
        imageStoreHandler.loadImageListFromDisk(this);
        List<Bitmap> bitmaps;
        bitmaps = imageStoreHandler.getImageListAsBitmaps();

        GridView mainGridView = findViewById(R.id.grid_view_gallery);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, bitmaps);
        mainGridView.setAdapter(galleryAdapter);
    }
}
