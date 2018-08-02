package de.fhe.wayinc.whereareyou.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.api.APIHandler;
import de.fhe.wayinc.whereareyou.model.SavedImage;
import de.fhe.wayinc.whereareyou.model.WeatherResponse;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.util.APIHelper;
import de.fhe.wayinc.whereareyou.util.StatsHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static de.fhe.wayinc.whereareyou.activity.MainActivity.EXTRA_IMAGE;

public class NewImageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";

    // API related strings
    private static final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY_WEATHER = "5d92457b0e7ae402210a9590d29c395e";

    private static final String URL_NUMBERS = "http://numbersapi.com/";

    // actual variables
    private String currentCity;
    private double temp;
    private String weatherIcon;
    private String latLon;
    private String fact;

    // output variables
    private String currentCity_out;
    private double temp_out;
    private String weatherIcon_out;
    private String latLon_out;
    private String fact_out;

    private String imagePath;
    private ImageStoreHandler imageStoreHandler;

    private DrawerLayout mDrawerLayout;
    private MenuItem choosenTemplate;

    // Location related variables
    private Location currentLocation;
    private Double lat, lon;
    private String countryCode;

    // Template related objects
    private ImageView imgViewMain;
    private TextView tempText;
    private ImageView icon;
    private TextView latnLon;
    private TextView cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // get imageview handlers
        imgViewMain = findViewById(R.id.img_edit_main);
        icon = findViewById(R.id.img_edit_icon);
        tempText = findViewById(R.id.img_edit_tempText);
        latnLon = findViewById(R.id.img_edit_LatnLon);
        cityText = findViewById(R.id.img_edit_city);

        // load intent extras
        Intent startIntent = getIntent();
        Bundle intentExtras = startIntent.getExtras();
        if (intentExtras != null) {
            imagePath = (String) intentExtras.get(EXTRA_IMAGE);
            currentLocation = (Location) intentExtras.get(EXTRA_MESSAGE);
        } else {
            Timber.e("Cannot load image: intentExtras was null");
        }

        // Set every infromation that depends on apis -----------------
        if (currentLocation != null) {
            lat = currentLocation.getLatitude();
            lon = currentLocation.getLongitude();
            latnLon.setText(MessageFormat.format("Lat: {0} | Lon: {1}", lat.toString(), lon.toString()));
            APIHandler weatherClient = APIHelper.createAPIHandler(URL_WEATHER, true, false);
            Call<WeatherResponse> weatherCall = weatherClient.getWeather(lat, lon, API_KEY_WEATHER);
            weatherCall.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> responseWeather) {
                    if (responseWeather.code() == 200) {
                        Timber.d("WEATHER API -> 200");

                        // write response into accessible variables
                        currentCity = responseWeather.body().getName();
                        temp = responseWeather.body().getMain().getTemp() - 273.15;
                        weatherIcon = responseWeather.body().getWeather().get(0).getIcon();
                        latLon = MessageFormat.format("{0}, {1}", lat.toString(), lon.toString());

                        currentCity_out = currentCity;
                        temp_out = temp;
                        weatherIcon_out = weatherIcon;
                        latLon_out = latLon;

                        cityText.setText(currentCity);
                        tempText.setText(MessageFormat.format("{0}°C", temp));
                        Glide.with(getApplicationContext())
                                .load(MessageFormat.format("https://openweathermap.org/img/w/{0}.png", weatherIcon))
                                .into(icon);
                    } else {
                        Timber.e(MessageFormat.format("Weather API returned code {0}", responseWeather.code()));
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Timber.e(MessageFormat.format("Failed to retreive Weather Info: {0}", t));
                }
            });
        } // End of location based defining of variables

        // call number fact api
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        Date currentDate = new Date();

        String month = monthFormat.format(currentDate);
        String day = dayFormat.format(currentDate);

        APIHandler numberHandler = APIHelper.createAPIHandler(URL_NUMBERS, false, true);
        Call<String> numberFactCall = numberHandler.getNumberFact(month, day);
        numberFactCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // TODO handle number api response
                fact = response.body();
                fact_out = fact;

                Timber.d("NUMBER API -> 200");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // TODO handle number api failure
                Timber.e(MessageFormat.format("Number Fact API failed: {0}", t));
            }
        });

        // load taken picture into main image view
        Glide.with(this)
                .load(imagePath)
                .into(imgViewMain);

        // Handeling the cklick on a template
        NavigationView navigationView = findViewById(R.id.img_edit_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // set item as selected to persist highlight
                        item.setChecked(true);
                        choosenTemplate = item;
                        mDrawerLayout.closeDrawers();

                        // Handling a chosen template
                        switch (choosenTemplate.getItemId()) {
                            case R.id.nav_oot:
                                tempText.setVisibility(View.VISIBLE);
                                icon.setVisibility(View.VISIBLE);

                                latnLon.setVisibility(View.GONE);
                                cityText.setVisibility(View.GONE);

                                temp_out = temp;
                                weatherIcon_out = weatherIcon;

                                latLon_out = null;
                                currentCity_out = null;
                                break;
                            case R.id.nav_way:
                                latnLon.setVisibility(View.VISIBLE);
                                cityText.setVisibility(View.VISIBLE);

                                tempText.setVisibility(View.GONE);
                                icon.setVisibility(View.GONE);

                                latLon_out = latLon;
                                currentCity_out = currentCity;

                                temp_out = -999;
                                weatherIcon_out = null;
                                break;
                            case R.id.nav_sat:
                                latnLon.setVisibility(View.VISIBLE);
                                tempText.setVisibility(View.VISIBLE);
                                cityText.setVisibility(View.VISIBLE);
                                icon.setVisibility(View.VISIBLE);

                                temp_out = temp;
                                weatherIcon_out = weatherIcon;
                                latLon_out = latLon;
                                currentCity_out = currentCity;
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple_back_ok, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        File image = new File(imagePath);

        switch (item.getItemId()) {
            case R.id.btn_edit_back:
                if (image.exists()) {
                    image.delete();
                    Timber.i(MessageFormat.format("Image {0} deleted", imagePath));
                }

                finish();
                break;
            case R.id.btn_edit_done:
                // TODO process edited image
                if (imageStoreHandler == null) {
                    imageStoreHandler = new ImageStoreHandler();
                    imageStoreHandler.loadImageListFromDisk(this);
                }

                if (!imageStoreHandler.isImageOnList(image)) {
                    imageStoreHandler.saveImageToImageList(image, weatherIcon_out, latLon_out, currentCity_out, temp_out, fact_out, null);
                    imageStoreHandler.writeOutImageList(this);
                }

                StatsHelper.incrementImageStat(this);
                if (StatsHelper.cityIsNew(this, currentCity)) {
                    StatsHelper.addCityToStat(this, currentCity);
                }

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
