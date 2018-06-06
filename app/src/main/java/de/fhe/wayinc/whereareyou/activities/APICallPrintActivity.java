package de.fhe.wayinc.whereareyou.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.location.Location;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.api.APIHandler;
import de.fhe.wayinc.whereareyou.models.weather.WeatherResponse;
import de.fhe.wayinc.whereareyou.utils.FontHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class APICallPrintActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";
    private static final String API_KEY_WEATHER = "326312ef6a3790999a35722bc8e3eb16";
    private static final String API_KEY_NEWS = "584a0a528faa4be89f7ed9225d87dac0";


    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_print);

        Intent returnAPIsCallIntent = getIntent();
        String PLZ = "12345";
        TextView plzText = findViewById(R.id.textView_API_PLZ);
        final TextView temperaturText = findViewById(R.id.textView_API_Temperatur);
        final TextView skyText = findViewById(R.id.textView_API_Sky);
        Bundle bundle = returnAPIsCallIntent.getExtras();
        Location location = (Location) bundle.get(EXTRA_MESSAGE);
        Geocoder geocoder = new Geocoder(this);
        List<Address> adrList = new ArrayList<>();
        try {
            lat = location.getLatitude();
            lon = location.getLongitude();
            adrList = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String plz = adrList.get(0).getPostalCode();

        plzText.setText("Postleitzahl: " + plz);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        APIHandler client = retrofit.create(APIHandler.class);

        Call<WeatherResponse> call = client.getWeather(lat, lon, API_KEY_WEATHER);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                double temp = response.body().getMain().getTemp() - 273.15;
                String himmel = response.body().getWeather().get(0).getDescription();
                temperaturText.setText("Temperatur: " + temp + "°C");
                skyText.setText("Himmel: " + himmel);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Timber.e(MessageFormat.format("Failed to retreive Weather Info: {0}", t));
            }
        });
    }

    /*protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        geocoder.getFromLocation()
    }*/

}
