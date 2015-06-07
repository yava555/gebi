package io.gebi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by yava on 15/5/6.
 */
public class APIBuilder {

    public static API api;

    public synchronized static API create() {
        if (api == null) {
            final Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.BASE_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();
            api = restAdapter.create(API.class);
        }
        return api;
    }

}
