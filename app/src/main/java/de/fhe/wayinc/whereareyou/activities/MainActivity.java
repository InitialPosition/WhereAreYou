package de.fhe.wayinc.whereareyou.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.utils.FontHelper;
import de.fhe.wayinc.whereareyou.utils.WindowHelper;
import retrofit2.Retrofit;
import timber.log.Timber;

import static android.content.Context.LOCATION_SERVICE;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location foundLocation;

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";
    private static final int PERMISSION_REQUEST_ANSWER = 1;

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //plant timber debug log tree
        Timber.plant(new Timber.DebugTree());

        //find elements
        TextView view = findViewById(R.id.txt_mainTitle);
        Button btn_newImage = findViewById(R.id.btn_takePicture);
        Button btn_gallery = findViewById(R.id.btn_gallery);

        //load the correct font into the title
        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", view);



        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
                //start a new image intent
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, TAKE_PICTURE);
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
                //TODO send new image to edit screen
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), MessageFormat.format("File received: {0}", imageUri), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Timber.e("OnActivityResult: No request code given");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            WindowHelper.hideUI(getWindow().getDecorView());
        }
    }
}