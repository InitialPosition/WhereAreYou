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

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;

import static de.fhe.wayinc.whereareyou.activities.MainActivity.EXTRA_IMAGE;

public class NewImageActivity extends AppCompatActivity {

    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image);

        // load intent extras
        Intent startIntent= getIntent();
        Bundle intentExtras = startIntent.getExtras();
        imageFile = (File) intentExtras.get(EXTRA_IMAGE);

        ImageView imgViewMain = findViewById(R.id.img_edit_main);

        Glide.with(this)
                .load(imageFile)
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
                finish();
                break;
            case R.id.btn_edit_done:
                // TODO process edited image
                ImageStoreHandler imageStoreHandler = new ImageStoreHandler(null);
                imageStoreHandler.saveImageToImageList(imageFile);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
