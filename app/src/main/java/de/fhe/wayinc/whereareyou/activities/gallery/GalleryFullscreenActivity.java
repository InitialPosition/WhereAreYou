package de.fhe.wayinc.whereareyou.activities.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.fhe.wayinc.whereareyou.R;

import static de.fhe.wayinc.whereareyou.activities.gallery.GalleryActivity.EXTRA_IMAGE_FULLSCREEN;

public class GalleryFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_fullscreen);

        Intent imageIntent = getIntent();
        Bundle data = imageIntent.getExtras();
        String imagePath = (String) data.get(EXTRA_IMAGE_FULLSCREEN);

        ImageView fullscreenImageView = findViewById(R.id.img_fullscreen);

        Glide.with(this)
                .load(imagePath)
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
