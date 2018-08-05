package de.fhe.wayinc.whereareyou.util;

import de.fhe.wayinc.whereareyou.api.APIHandler;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * This class creates API handlers for the different API endpoints.
 */

public class APIHelper {

    /**
     * Create a new API handler
     * @param URL The base URL of the API to call
     * @param hasJSONFactory Whether the new handler needs a GSON converter factory
     * @param hasScalarsFactory Whether the new handler needs a Scalars factory
     * @return The new handler
     */
    public static APIHandler createAPIHandler(String URL, boolean hasJSONFactory, boolean hasScalarsFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL);

        // add factories
        if (hasJSONFactory) {
            builder.addConverterFactory(GsonConverterFactory.create());
        }
        if (hasScalarsFactory) {
            builder.addConverterFactory(ScalarsConverterFactory.create());
        }

        Retrofit retrofit = builder.build();

        return retrofit.create(APIHandler.class);
    }
}
