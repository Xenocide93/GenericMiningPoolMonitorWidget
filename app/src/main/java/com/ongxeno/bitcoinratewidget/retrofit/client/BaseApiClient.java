package com.ongxeno.bitcoinratewidget.retrofit.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Xenocide93 on 7/24/17.
 */

public abstract class BaseApiClient {

    protected Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getHttpClient())
                .build();
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    protected static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gsonBuilder.enableComplexMapKeySerialization();
        gsonBuilder.serializeSpecialFloatingPointValues();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

    abstract String getBaseUrl();
}
