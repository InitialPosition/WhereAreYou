package de.fhe.wayinc.whereareyou.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.utils.FontHelper;
import de.fhe.wayinc.whereareyou.utils.MathHelper;
import de.fhe.wayinc.whereareyou.utils.SystemHelper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "extraImage";
    private static final String FILEPROVIDER = "de.fhe.wayinc.whereareyou.fileprovider";

    private Location foundLocation;

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";
    private static final int PERMISSION_REQUEST_ANSWER = 1;

    private static final int TAKE_PICTURE = 1;
    private File imageFile;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ConstraintLayout layout = findViewById(R.id.layout_main_menu);
            Drawable bg = SystemHelper.getRandomBackgroundDrawable(this);
            layout.setBackground(bg);
        }

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

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            foundLocation = location;
                        } else {
                            Timber.e("Location was NULL");
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.e(MessageFormat.format("Call failed: {0}", e));
                    }
                });

        //set onClickListeners
        btn_newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if we have permission to take a picture
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takeNewImage();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CAMERA)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setCancelable(false)
                                .setTitle(getString(R.string.str_cam_permission_title))
                                .setMessage(getString(R.string.str_cam_permission_explanation))
                                .setNeutralButton("OK", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                // TODO gallery intent
                //Toast.makeText(getApplicationContext(), "Gallery Button clicked", Toast.LENGTH_SHORT).show();
                Intent callAPIsIntent = new Intent(MainActivity.this, APICallPrintActivity.class);

                Timber.d("LOCATION TEST FIRED");

                callAPIsIntent.putExtra(EXTRA_MESSAGE, foundLocation);
                startActivity(callAPIsIntent);
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

    private void takeNewImage() {
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
}