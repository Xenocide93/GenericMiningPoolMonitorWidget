package com.ongxeno.bitcoinratewidget.retrofit.api;

import com.ongxeno.bitcoinratewidget.model.BxMarketData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Xenocide93 on 6/18/17.
 */

public interface BxApi {

    @GET("api/")
    Call<Map<Integer, BxMarketData>> getAllMarketData();

}
