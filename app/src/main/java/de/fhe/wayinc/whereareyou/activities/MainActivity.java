package de.fhe.wayinc.whereareyou.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.utils.FontHelper;
import de.fhe.wayinc.whereareyou.utils.SystemHelper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "extraImage";

    private static final String FILEPROVIDER = "de.fhe.wayinc.whereareyou.fileprovider";

    private static final int TAKE_PICTURE = 1;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // plant timber debug log tree
        Timber.plant(new Timber.DebugTree());

        // find elements
        TextView mainTitle = findViewById(R.id.txt_mainTitle);
        Button btn_newImage = findViewById(R.id.btn_takePicture);
        Button btn_gallery = findViewById(R.id.btn_gallery);

        // load the correct font into the title
        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", mainTitle);

        // set onClickListeners
        btn_newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an image file
                imageFile = null;
                try {
                    imageFile = SystemHelper.createImageFile(getApplicationContext());
                } catch (IOException e) {
                    Timber.e(MessageFormat.format("Error creating the image file: {0}", e));
                }

                if (imageFile != null) {
                    // get image URI
                    Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), FILEPROVIDER, imageFile);

                    // start new image intent
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            .putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, TAKE_PICTURE);
                    } else {
                        Timber.e("Could not find compatible camera app");
                    }
                } else {
                    Timber.e("Could not start image capture intent: imageFile was NULL");
                }
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO gallery intent
                Toast.makeText(getApplicationContext(), "Gallery Button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Intent imageProcessIntent = new Intent(MainActivity.this, NewImageActivity.class);
                    imageProcessIntent.putExtra(EXTRA_IMAGE, imageFile);
                    startActivity(imageProcessIntent);
                }
                break;
            default:
                Timber.e("OnActivityResult: No request code given");
        }
    }
}