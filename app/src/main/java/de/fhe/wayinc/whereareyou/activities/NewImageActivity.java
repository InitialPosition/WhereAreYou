package de.fhe.wayinc.whereareyou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.MessageFormat;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import timber.log.Timber;

import static de.fhe.wayinc.whereareyou.activities.MainActivity.EXTRA_IMAGE;

public class NewImageActivity extends AppCompatActivity {

    String imagePath;
    ImageStoreHandler imageStoreHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image);

        // get imageview handlers
        ImageView imgViewMain = findViewById(R.id.img_edit_main);
        ImageView imageViewWeather = findViewById(R.id.img_edit_weatherIcon);

        // load intent extras
        Intent startIntent = getIntent();
        Bundle intentExtras = startIntent.getExtras();
        if (intentExtras != null) {
            imagePath = (String) intentExtras.get(EXTRA_IMAGE);
        } else {
            Timber.e("Cannot load image: intentExtras was null");
        }

        Glide.with(this)
                .load(imagePath)
                .into(imgViewMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple_back_ok, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_edit_back:
                File image = new File(imagePath);
                if (image.exists()) {
                    image.delete();
                    Timber.i(MessageFormat.format("Image {0} deleted", imagePath));
                }

                finish();
                break;
            case R.id.btn_edit_done:
                // TODO process edited image
                if (imageStoreHandler == null) {
                    imageStoreHandler = new ImageStoreHandler(null);
                }
                //imageStoreHandler.saveImageToImageList(image);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
