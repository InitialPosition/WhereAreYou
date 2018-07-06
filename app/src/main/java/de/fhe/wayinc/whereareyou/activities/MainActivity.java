package de.fhe.wayinc.whereareyou.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private static final int PERMISSION_REQUEST_CAMERA_STORAGE_LOCATION = 1;

    String mCurrentPhotoPath;
    private static final int TAKE_PICTURE = 2;

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

        // easter egg corner
        if (MathHelper.randomInt(0, 1000) < 1) {
            mainTitle.setText(getString(R.string.str_appTitle_easteregg));
        }

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
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    takeNewImage();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_CAMERA_STORAGE_LOCATION);
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO gallery intent
                //Toast.makeText(getApplicationContext(), "Gallery Button clicked", Toast.LENGTH_SHORT).show();
                Intent callAPIsIntent = new Intent(MainActivity.this, APICallPrintActivity.class);

                callAPIsIntent.putExtra(EXTRA_MESSAGE, foundLocation);
                startActivity(callAPIsIntent);
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA_STORAGE_LOCATION:
                if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    takeNewImage();
                } else {
                    Timber.w("Permissions were denied");
                }
                break;
            default:
                Timber.d(MessageFormat.format("Unknown callback for permission request: Code {0}", requestCode));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Intent imageProcessIntent = new Intent(MainActivity.this, NewImageActivity.class);
                    Bundle imageData = data.getExtras();
                    imageProcessIntent.putExtra(EXTRA_IMAGE, mCurrentPhotoPath);
                    imageProcessIntent.putExtra(EXTRA_MESSAGE, foundLocation);
                    startActivity(imageProcessIntent);
                }
                break;
            default:
                Timber.e("OnActivityResult: No request code given");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takeNewImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Timber.e(ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, FILEPROVIDER, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }

    }
}