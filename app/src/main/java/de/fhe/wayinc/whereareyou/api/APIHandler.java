package de.fhe.wayinc.whereareyou.api;

import de.fhe.wayinc.whereareyou.model.NewsResponse;
import de.fhe.wayinc.whereareyou.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIHandler {
    // WEATHER CALLS
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String key);

    // NEWS CALLS
    @GET("top-headlines")
    Call<NewsResponse> getNews(@Query("country") String countryCode, @Query("apiKey") String key);

    // NUMBER CALLS
    @GET("{month}/{day}/date")
    Call<String> getNumberFact(@Path("month") String month, @Path("day") String day);
}