package com.ongxeno.bitcoinratewidget.retrofit.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ongxeno.bitcoinratewidget.model.BxMarketData;
import com.ongxeno.bitcoinratewidget.retrofit.api.BxApi;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class BxApiClient {

	private static final String BX_BASE_URL = "https://bx.in.th/";

	public static void getAllMarketData(Callback<Map<Integer, BxMarketData>> externalCallback) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setLenient();
		gsonBuilder.enableComplexMapKeySerialization();
		gsonBuilder.serializeSpecialFloatingPointValues();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
	    // set your desired log level
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		// add your other interceptors …

	    // add logging as last interceptor
		httpClient.addInterceptor(logging);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BX_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(httpClient.build())
				.build();

		BxApi bxApi = retrofit.create(BxApi.class);

		Call<Map<Integer, BxMarketData>> call = bxApi.getAllMarketData();
		call.enqueue(externalCallback);
	}
}
