package de.fhe.wayinc.whereareyou.activities;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.location.Location;
import android.location.Geocoder;

import java.util.Locale;

import de.fhe.wayinc.whereareyou.R;

public class APICallPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_print);

        Intent returnAPIsCallIntent = getIntent();
        String PLZ = "12345";
        TextView plzText = findViewById(R.id.textView);
        plzText.setText(returnAPIsCallIntent.getStringExtra(MainActivity.EXTRA_MESSAGE));
    }

    /*protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        geocoder.getFromLocation()
    }*/

}
