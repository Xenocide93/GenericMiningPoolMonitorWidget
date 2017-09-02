package com.ongxeno.bitcoinratewidget.widgetprovider.dwarfpool;

import android.content.Context;

import com.ongxeno.bitcoinratewidget.model.dwarfpool.DwarfpoolData;
import com.ongxeno.bitcoinratewidget.retrofit.client.DwarfpoolApiClient;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractBxPoolMonitorWidgetProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xenocide93 on 9/2/17.
 */

public abstract class AbstractDwarfpoolWidgetProvider extends AbstractBxPoolMonitorWidgetProvider {

	public abstract String getWalletAddress();

	public abstract String getEmailAddress();

	public abstract String getCoinUrl();

	public abstract String getHashRateSuffix();

	public abstract String getBalanceSuffix();

	@Override
	public void updateHashRate(final Context context) {
		if (hasInit()) {
			DwarfpoolApiClient.getDwarfpoolData(getCoinUrl(), getWalletAddress(), getEmailAddress(), new Callback<DwarfpoolData>() {
				@Override
				public void onResponse(Call<DwarfpoolData> call, Response<DwarfpoolData> response) {
					if (response != null && response.body() != null) {
						setHashRateString(response.body().getTotalHashrate() + getHashRateSuffix());
						setBalanceString(response.body().getWalletBalance() + getBalanceSuffix());
						updateWidgetUi(context);
					} else {
						updateWidgetUi(context, "response = null", "", "");
					}
				}

				@Override
				public void onFailure(Call<DwarfpoolData> call, Throwable t) {
					updateWidgetUi(context, "Load failed", "", "");
				}
			});
		}
	}

	@Override
	public void updateBalance(Context context) {
		//TODO do nothing because already load balance with hash rate.
	}

	@Override
	public boolean hasInit() {
		return true;
	}
}
