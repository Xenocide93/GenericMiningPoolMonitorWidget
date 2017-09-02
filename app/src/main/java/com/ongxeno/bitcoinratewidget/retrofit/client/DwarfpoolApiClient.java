package com.ongxeno.bitcoinratewidget.retrofit.client;

import com.ongxeno.bitcoinratewidget.model.dwarfpool.DwarfpoolData;
import com.ongxeno.bitcoinratewidget.retrofit.api.DwarfpoolApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Xenocide93 on 9/2/17.
 */

public class DwarfpoolApiClient extends BaseApiClient {

	private static final String DWARFPOOL_BASE_URL = "http://dwarfpool.com/";

	@Override
	String getBaseUrl() {
		return DWARFPOOL_BASE_URL;
	}

	public static void getDwarfpoolData(String coinUrl, String walletAddress, String emailAddress, Callback<DwarfpoolData> externalCallback) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(DWARFPOOL_BASE_URL + coinUrl)
				.addConverterFactory(GsonConverterFactory.create(getGson()))
				.client(getHttpClient())
				.build();

		DwarfpoolApi dwarfpoolApi = retrofit.create(DwarfpoolApi.class);

		Call<DwarfpoolData> call = dwarfpoolApi.getDwarfpoolData(walletAddress, emailAddress);
		call.enqueue(externalCallback);
	}
}
