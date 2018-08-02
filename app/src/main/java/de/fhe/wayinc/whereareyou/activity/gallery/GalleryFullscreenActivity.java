package de.fhe.wayinc.whereareyou.activity.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.model.SavedImage;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;

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

        // make all extras invisible
        icon.setVisibility(View.GONE);
        latLonText.setVisibility(View.GONE);
        cityText.setVisibility(View.GONE);
        tempText.setVisibility(View.GONE);

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
        }
        if (image.getSavedLatLon() != null) {
            latLonText.setVisibility(View.VISIBLE);
            latLonText.setText(image.getSavedLatLon());
        }
        if (image.getSavedTemp() != -999) {
            tempText.setVisibility(View.VISIBLE);
            tempText.setText(String.valueOf(image.getSavedTemp()));
        }

        // set text color
        if (image.getTextColor() != null) {
            // TODO change color of text views
        }

        Glide.with(this)
                .load(image.getPath())
                .into(fullscreenImageView);
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
