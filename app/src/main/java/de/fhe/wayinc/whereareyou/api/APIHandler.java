package de.fhe.wayinc.whereareyou.api;

import de.fhe.wayinc.whereareyou.models.weather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIHandler {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String key);
}