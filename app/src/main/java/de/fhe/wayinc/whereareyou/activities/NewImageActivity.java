package de.fhe.wayinc.whereareyou.activities;

import android.content.ClipData;
import android.content.Intent;
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

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.api.APIHandler;
import de.fhe.wayinc.whereareyou.models.WeatherResponse;
import de.fhe.wayinc.whereareyou.storage.ImageStoreHandler;
import de.fhe.wayinc.whereareyou.utils.APIHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static de.fhe.wayinc.whereareyou.activities.MainActivity.EXTRA_IMAGE;
import static de.fhe.wayinc.whereareyou.activities.MainActivity.EXTRA_MESSAGE;

public class NewImageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "de.fhe.wayinc.whereareyou.PLZ";

    // API related strings
    private static final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY_WEATHER = "5d92457b0e7ae402210a9590d29c395e";

    private static final String URL_NEWS = "https://newsapi.org/v2/";
    private static final String API_KEY_NEWS = "4727897250da453592cdc4952c410811";

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
            APIHandler weatherClient = APIHelper.createAPIHandler(URL_WEATHER);
            Call<WeatherResponse> weatherCall = weatherClient.getWeather(lat, lon, API_KEY_WEATHER);
            weatherCall.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> responseWeather) {
                    if (responseWeather.code() == 200) {
                        double temp = responseWeather.body().getMain().getTemp() - 273.15;
                        cityText.setText(responseWeather.body().getName());
                        tempText.setText(MessageFormat.format("{0}°C", temp));
                        Glide.with(getApplicationContext())
                                .load(MessageFormat.format("http://openweathermap.org/img/w/{0}.png", responseWeather.body().getWeather().get(0).getIcon()))
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
        }// End of location based defining of variables

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

                        // Handeling a choosen template
                        switch (choosenTemplate.getItemId()) {
                            case R.id.nav_oot:
                                latnLon.setVisibility(View.INVISIBLE);
                                cityText.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.nav_way:
                                latnLon.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.nav_sat:
                                latnLon.setVisibility(View.VISIBLE);
                                tempText.setVisibility(View.VISIBLE);
                                cityText.setVisibility(View.VISIBLE);
                                icon.setVisibility(View.VISIBLE);
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
                    imageStoreHandler = new ImageStoreHandler(null);
                    imageStoreHandler.loadImageListFromDisk(this);
                }

                if (!imageStoreHandler.isImageOnList(image)) {
                    imageStoreHandler.saveImageToImageList(image);
                    imageStoreHandler.writeOutImageList(this);
                }

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
