package com.ongxeno.bitcoinratewidget.retrofit.api;

import com.ongxeno.bitcoinratewidget.model.dwarfpool.DwarfpoolData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Xenocide93 on 9/2/17.
 */

public interface DwarfpoolApi {

	@GET("/api")
	Call<DwarfpoolData> getDwarfpoolData(String walletAddress, String emailAddress);
}
