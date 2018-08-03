package de.fhe.wayinc.whereareyou.activity.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.model.SavedImage;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.util.FontHelper;
import timber.log.Timber;

import static de.fhe.wayinc.whereareyou.activity.gallery.GalleryActivity.EXTRA_IMAGE_FULLSCREEN;

public class GalleryFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_fullscreen);

        // TODO load correct layout

        Intent imageIntent = getIntent();
        Bundle data = imageIntent.getExtras();
        int imagePos = (int) data.get(EXTRA_IMAGE_FULLSCREEN);

        ImageStoreHandler imageStoreHandler = new ImageStoreHandler();
        imageStoreHandler.loadImageListFromDisk(this);

        SavedImage image = imageStoreHandler.getImageObjectFromImageList(imagePos);

        ImageView fullscreenImageView = findViewById(R.id.img_fullscreen);
        ImageView icon = findViewById(R.id.img_edit_icon_f);
        TextView latLonText = findViewById(R.id.img_edit_LatnLon_f);
        TextView cityText = findViewById(R.id.img_edit_city_f);
        TextView tempText = findViewById(R.id.img_edit_tempText_f);
        TextView factText = findViewById(R.id.img_edit_fact_f);
        TextView dateText = findViewById(R.id.img_edit_date_f);

        // make all extras invisible
        icon.setVisibility(View.GONE);
        latLonText.setVisibility(View.GONE);
        cityText.setVisibility(View.GONE);
        tempText.setVisibility(View.GONE);
        factText.setVisibility(View.GONE);
        dateText.setVisibility(View.GONE);

        // turn extras with a value visible
        if (image.getIcon() != null) {
            icon.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(MessageFormat.format("https://openweathermap.org/img/w/{0}.png", image.getIcon()))
                    .into(icon);
        }
        if (image.getSavedLatLon() != null) {
            cityText.setVisibility(View.VISIBLE);
            cityText.setText(image.getSavedLocation());

            cityText.setTextSize(50);

            if (icon.getVisibility() == View.GONE) {
                cityText.setTextSize(90);
            }

            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", cityText);
        }
        if (image.getSavedLatLon() != null) {
            latLonText.setVisibility(View.VISIBLE);
            latLonText.setText(image.getSavedLatLon());

            latLonText.setTextSize(22);

            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", latLonText);
        }
        if (image.getSavedTemp() != -999) {
            tempText.setVisibility(View.VISIBLE);
            tempText.setText(MessageFormat.format("{0}°C", String.valueOf(image.getSavedTemp())));

            tempText.setTextSize(50);

            if (icon.getVisibility() == View.GONE) {
                tempText.setTextSize(90);
            }

            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", tempText);
        }
        if (image.getSavedFact() != null) {
            factText.setVisibility(View.VISIBLE);
            factText.setText(image.getSavedFact());

            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", factText);
        }
        if (image.getDate() != null) {
            dateText.setVisibility(View.VISIBLE);
            dateText.setText(image.getDate());

            dateText.setTextSize(22);

            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", dateText);
        }

        // set text color
        tempText.setTextColor(image.getTextColor());
        cityText.setTextColor(image.getTextColor());
        latLonText.setTextColor(image.getTextColor());
        factText.setTextColor(image.getTextColor());
        dateText.setTextColor(image.getTextColor());

        Glide.with(this)
                .load(image.getPath())
                .into(fullscreenImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back_export, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_edit_back_backExport:
                finish();
                return super.onOptionsItemSelected(item);

            case R.id.btn_edit_export_backExport:
                // magic numbers because I could not find how to get the views size
                int xPos1 = 0;
                int yPos1 = 282;

                int xPos2 = 1080;
                int yPos2 = 1440;

                Bitmap screenshot = renderImage();
                Bitmap screenshotCropped = Bitmap.createBitmap(screenshot, xPos1, yPos1, xPos2, yPos2);

                shareImage(screenshotCropped);

                return super.onOptionsItemSelected(item);

            default:
                Timber.e("Could not perform action: Unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    private Bitmap renderImage() {
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        return bitmap;
    }

    private void shareImage(Bitmap mBitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(android.os.Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg"));
        startActivity(Intent.createChooser(share, getString(R.string.str_share_image)));
    }
}
