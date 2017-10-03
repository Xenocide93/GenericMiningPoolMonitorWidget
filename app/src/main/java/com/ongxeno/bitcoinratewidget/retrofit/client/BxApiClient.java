package com.ongxeno.bitcoinratewidget.retrofit.client;

import com.ongxeno.bitcoinratewidget.model.bx.BxMarketData;
import com.ongxeno.bitcoinratewidget.retrofit.api.BxApi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class BxApiClient extends BaseApiClient {

	private static final String BX_BASE_URL = "https://bx.in.th/";

	@Override
	String getBaseUrl() {
		return BX_BASE_URL;
	}

	public static void getAllMarketData(Callback<Map<Integer, BxMarketData>> externalCallback) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BX_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(getGson()))
				.client(getHttpClient())
				.build();

		BxApi bxApi = retrofit.create(BxApi.class);

		Call<Map<Integer, BxMarketData>> call = bxApi.getAllMarketData();
		call.enqueue(externalCallback);
	}
}
