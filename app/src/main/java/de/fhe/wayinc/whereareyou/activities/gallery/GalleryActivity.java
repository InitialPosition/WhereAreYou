package de.fhe.wayinc.whereareyou.activities.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.utils.GalleryAdapter;

public class GalleryActivity extends AppCompatActivity {

    protected final static String EXTRA_IMAGE_FULLSCREEN = "image_fullscreen";
    private AlertDialog.Builder builder;
    boolean canClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        builder = new android.support.v7.app.AlertDialog.Builder(this);

        final ImageStoreHandler imageStoreHandler = new ImageStoreHandler(null);
        imageStoreHandler.loadImageListFromDisk(this);
        List<Bitmap> bitmaps = imageStoreHandler.getImageListAsBitmaps();

        GridView mainGridView = findViewById(R.id.grid_view_gallery);
        final GalleryAdapter galleryAdapter = new GalleryAdapter(this, bitmaps);
        mainGridView.setAdapter(galleryAdapter);

        canClick = true;

        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (canClick) {
                    Intent fullScreenIntent = new Intent(GalleryActivity.this, GalleryFullscreenActivity.class);
                    fullScreenIntent.putExtra(EXTRA_IMAGE_FULLSCREEN, imageStoreHandler.getImageFromImageList(position));
                    startActivity(fullScreenIntent);
                }
            }
        });

        mainGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                canClick = false;

                builder.setMessage("Do you really want to delete this image?");
                builder.setCancelable(false);
                builder.setPositiveButtonIcon(getDrawable(R.drawable.ic_check_black_24dp));
                builder.setNegativeButtonIcon(getDrawable(R.drawable.ic_cancel_black_24dp));
                builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canClick = true;

                        imageStoreHandler.removeImageFromList(position);
                        imageStoreHandler.writeOutImageList(getBaseContext());

                        galleryAdapter.removeItem(position);
                    }
                });
                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canClick = true;

                        dialog.cancel();
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple_back, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_edit_back_backOnly:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
