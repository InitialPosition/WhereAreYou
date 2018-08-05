package de.fhe.wayinc.whereareyou.api;

import de.fhe.wayinc.whereareyou.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIHandler {
    /**
     * Sets up a new call for the weather API
     * @param lat Latitude to get the weather for
     * @param lon Longitude to get the weather for
     * @param key The API key
     * @return Call for weather API
     */
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String key);

    /**
     * Sets up a new call for the number API
     * @param month Month to get a fact for
     * @param day Day to get a fact for
     * @return Call for number API
     */
    @GET("{month}/{day}/date")
    Call<String> getNumberFact(@Path("month") String month, @Path("day") String day);
}