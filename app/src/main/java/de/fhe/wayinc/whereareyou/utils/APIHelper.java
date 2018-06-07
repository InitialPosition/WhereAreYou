package de.fhe.wayinc.whereareyou.utils;

import de.fhe.wayinc.whereareyou.api.APIHandler;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {

    public static APIHandler createAPIHandler(String URL) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit.create(APIHandler.class);
    }
}
