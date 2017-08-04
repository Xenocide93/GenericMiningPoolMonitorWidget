package com.ongxeno.bitcoinratewidget.retrofit.api;

import com.ongxeno.bitcoinratewidget.model.suprnova.Suprnova;
import com.ongxeno.bitcoinratewidget.model.suprnova.UserBalance;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author Xenocide93 on 7/13/17.
 */

public interface SuprnovaApi {

    @GET("index.php")
    Call<Suprnova> getSuprnovaData(@QueryMap Map<String, String> params);

    @GET("index.php")
    Call<UserBalance> getBalanceData(@QueryMap Map<String, String> params);

}
