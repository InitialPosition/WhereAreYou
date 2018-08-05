package de.fhe.wayinc.whereareyou.activity.gallery;

import android.content.Context;
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
import android.widget.TextView;

import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.util.FontHelper;
import de.fhe.wayinc.whereareyou.util.GalleryAdapter;

public class GalleryActivity extends AppCompatActivity {

    protected final static String EXTRA_IMAGE_FULLSCREEN = "image_fullscreen";
    private AlertDialog.Builder builder;
    private boolean canClick;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ctx = this;

        // get layout elements
        TextView title = findViewById(R.id.gallery_title);

        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", title);

        // create a dialog builder
        builder = new android.support.v7.app.AlertDialog.Builder(this);

        // create image handler and gridview adapter
        final ImageStoreHandler imageStoreHandler = new ImageStoreHandler();
        imageStoreHandler.loadImageListFromDisk(ctx);

        final GridView mainGridView = findViewById(R.id.grid_view_gallery);
        final GalleryAdapter galleryAdapter = new GalleryAdapter(this, null);

        // load the images in a new thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Bitmap> bitmaps = imageStoreHandler.getImageListAsScaledBitmaps(100, 100);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        galleryAdapter.setList(bitmaps);
                        mainGridView.setAdapter(galleryAdapter);
                    }
                });
            }
        };
        thread.start();

        canClick = true;

        // handle clicking an image
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (canClick) {
                    Intent fullScreenIntent = new Intent(GalleryActivity.this, GalleryFullscreenActivity.class);
                    fullScreenIntent.putExtra(EXTRA_IMAGE_FULLSCREEN, position);
                    startActivity(fullScreenIntent);
                }
            }
        });

        // handle deleting an image
        mainGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                canClick = false;

                // show a confirmation dialog
                builder.setMessage("Do you really want to delete this image?");
                builder.setCancelable(false);
                builder.setPositiveButtonIcon(getDrawable(R.drawable.ic_check_black_24dp));
                builder.setNegativeButtonIcon(getDrawable(R.drawable.ic_cancel_black_24dp));
                builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canClick = true;

                        // remove the image from the list
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

    /**
     * Set the correct menu
     * @param menu The menu to set
     * @return unused
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple_back, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle menu item selection
     * @param item The selected menu item
     * @return unused
     */
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
