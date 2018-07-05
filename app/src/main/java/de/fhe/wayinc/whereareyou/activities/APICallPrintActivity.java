package de.fhe.wayinc.whereareyou.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.api.APIHandler;
import de.fhe.wayinc.whereareyou.models.NewsResponse;
import de.fhe.wayinc.whereareyou.models.WeatherResponse;
import de.fhe.wayinc.whereareyou.utils.APIHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class APICallPrintActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";

    static final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY_WEATHER = "5d92457b0e7ae402210a9590d29c395e";

    private String countryCode;
    private static final String URL_NEWS = "https://newsapi.org/v2/";
    private static final String API_KEY_NEWS = "4727897250da453592cdc4952c410811";


    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_print);

        Intent returnAPIsCallIntent = getIntent();
        String PLZ = "No zip code found";

        final TextView latlongText = findViewById(R.id.textView_API_LatLong);
        final TextView plzText = findViewById(R.id.textView_API_PLZ);
        final TextView temperaturText = findViewById(R.id.textView_API_Temperatur);
        final TextView skyText = findViewById(R.id.textView_API_Sky);
        final TextView locationText = findViewById(R.id.textView_API_Location);
        final TextView newsTitleText = findViewById(R.id.textView_API_NewsTitle);
        final TextView newsText = findViewById(R.id.textView_API_NewsText);
        Bundle bundle = returnAPIsCallIntent.getExtras();

        Location location = (Location) bundle.get(EXTRA_MESSAGE);
        Geocoder geocoder = new Geocoder(this);
        List<Address> adrList = new ArrayList<>();
        try {
            lat = location.getLatitude();
            lon = location.getLongitude();
            latlongText.setText("Latitude: " + lat + " | Longitude: " + lon);
            adrList = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String plz = adrList.get(0).getPostalCode();

        countryCode = adrList.get(0).getCountryCode();
        plzText.setText("Zip Code: " + plz);
        locationText.setText("Country: " + countryCode);

        APIHandler weatherClient = APIHelper.createAPIHandler(URL_WEATHER);
        APIHandler newsClient = APIHelper.createAPIHandler(URL_NEWS);

        Call<WeatherResponse> weatherCall = weatherClient.getWeather(lat, lon, API_KEY_WEATHER);
        weatherCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> responseWeather) {
                if (responseWeather.code() == 200) {
                    double temp = responseWeather.body().getMain().getTemp() - 273.15;
                    String himmel = responseWeather.body().getWeather().get(0).getDescription();
                    temperaturText.setText("Temperature: " + temp + "°C");
                    skyText.setText("Sky: " + himmel);
                } else {
                    Timber.e(MessageFormat.format("Weather API returned code {0}", responseWeather.code()));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Timber.e(MessageFormat.format("Failed to retreive Weather Info: {0}", t));
            }
        });

        Call<NewsResponse> newsCall = newsClient.getNews(countryCode, API_KEY_NEWS);
        newsCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> responseNews) {
                if (responseNews.code() == 200) {
                    newsTitleText.setText(responseNews.body().getArticles().get(0).getTitle());
                    newsTitleText.setPaintFlags(newsTitleText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    newsText.setText(responseNews.body().getArticles().get(0).getSource().getName());
                } else {
                    Timber.e(MessageFormat.format("News API returned code {0}", responseNews.code()));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Timber.e(MessageFormat.format("Failed to retreive News Info: {0}", t));
            }
        });
    }

    /*protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        geocoder.getFromLocation()
    }*/

}
