package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.model.suprnova.Suprnova;
import com.ongxeno.bitcoinratewidget.model.suprnova.UserBalance;
import com.ongxeno.bitcoinratewidget.retrofit.client.GenericPoolClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xenocide93 on 6/18/17.
 */

public abstract class AbstractGenericPoolWidgetProvider extends AbstractBxPoolMonitorWidgetProvider implements ThresholdSetterHandler {

	@Override
	public void updateBalance(final Context context) {
		new GenericPoolClient(getToken(context), getBaseUrl()).getUserBalance(new Callback<UserBalance>() {
			@Override
			public void onResponse(Call<UserBalance> call, Response<UserBalance> response) {
				if (response.body() == null) {
					setBalanceString("Error");
					updateWidgetUi(context);
					return;
				}

				UserBalance balance = response.body();
				if (balance != null) {
					Double balanceDouble = balance.getUserBalanceData().getBalanceData().getConfirmed()
							+ balance.getUserBalanceData().getBalanceData().getUnconfirmed();
					setBalanceString(formatBalance(balanceDouble, 3) + " " + getBalanceSuffix());
					updateWidgetUi(context);
				}
			}

			@Override
			public void onFailure(Call<UserBalance> call, Throwable t) {
				setBalanceString("Error");
				updateWidgetUi(context);
			}
		});
	}

	@Override
	public void updateHashRate(final Context context) {
		GenericPoolClient client = new GenericPoolClient(getToken(context), getBaseUrl());
		client.getUserStatus(new Callback<Suprnova>() {
			@Override
			public void onResponse(@NonNull Call<Suprnova> call, @NonNull Response<Suprnova> response) {
				if (response.body() == null) {
					return;
				}

				Suprnova suprnova = response.body();
				if (suprnova != null) {
					Double hashRate = suprnova.getUserStatus().getShareRateData().getHashrate() * getHashRateDisplayFactor();
					String hashRateString = formatNumber(hashRate, 0) + " " + getHashRateSuffix();
					setHashRateString(hashRateString);
					Toast.makeText(context, hashRateString, Toast.LENGTH_SHORT).show();
					double threshold = getMinHashRateThreshold();
					if (threshold > 0 && hashRate < threshold) {
						notifyWorkerDown(context, hashRate);
					}
				} else {
					setHashRateString("Error");
				}
				updateWidgetUi(context);
			}

			@Override
			public void onFailure(@NonNull Call<Suprnova> call, @NonNull Throwable t) {
				setHashRateString("Error");
				updateWidgetUi(context);
			}
		});
	}

	private String formatBalance(double d, int precision) {
		return String.valueOf(((int) (d * Math.pow(10, precision))) / Math.pow(10, precision));
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	protected double getHashRateDisplayFactor() {
		return 1d;
	}

	/**
	 * Gets access token for the api.
	 *
	 * @param context
	 *            context
	 *
	 * @return token
	 */
	protected abstract String getToken(Context context);

	/**
	 * Gets base url for GenericPoolClient.
	 *
	 * @return base url.
	 */
	protected abstract String getBaseUrl();

	/**
	 * Gets hash rate suffix.
	 *
	 * @return hash rate suffix
	 */
	protected abstract String getHashRateSuffix();

	/**
	 * Gets balance suffix.
	 *
	 * @return balance suffix
	 */
	protected abstract String getBalanceSuffix();

	/**
	 * Get min hash rate threshold to notify.
	 *
	 * @return hash rate
	 */
	protected abstract double getMinHashRateThreshold();

	/**
	 * Get unique pool ID.
	 *
	 * @return pool ID
	 */
	protected abstract int getPoolId();

}
