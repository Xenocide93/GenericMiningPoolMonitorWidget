package com.ongxeno.bitcoinratewidget.retrofit.client;

import com.ongxeno.bitcoinratewidget.model.suprnova.Suprnova;
import com.ongxeno.bitcoinratewidget.model.suprnova.UserBalance;
import com.ongxeno.bitcoinratewidget.retrofit.api.SuprnovaApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Xenocide93 on 7/13/17.
 */

public class GenericPoolClient extends BaseApiClient {

	public static final String ZEC = "zec";
	public static final String LBRY = "lbry";
	public static final String HUSH = "hush";

	private static final String API_GET_USER_STATUS = "getuserstatus";
	private static final String API_GET_USER_BALANCE = "getuserbalance";

	private String token;
	private String baseUrl;

	public GenericPoolClient(String suprnovaToken, String baseUrl) {
		super();
		this.token = suprnovaToken;
		this.baseUrl = baseUrl;
	}

	public void getUserStatus(Callback<Suprnova> externalCallback) {
		Map<String, String> params = new HashMap<>();
		params.put("page", "api");
		params.put("action", API_GET_USER_STATUS);
		params.put("api_key", token);

		Call<Suprnova> call = getRetrofit().create(SuprnovaApi.class).getSuprnovaData(params);
		call.enqueue(externalCallback);
	}

	public void getUserBalance(Callback<UserBalance> externalCallback) {
		Map<String, String> params = new HashMap<>();
		params.put("page", "api");
		params.put("action", API_GET_USER_BALANCE);
		params.put("api_key", token);

		Call<UserBalance> call = getRetrofit().create(SuprnovaApi.class).getBalanceData(params);
		call.enqueue(externalCallback);
	}

	@Override
	String getBaseUrl() {
		return baseUrl;
	}
}
