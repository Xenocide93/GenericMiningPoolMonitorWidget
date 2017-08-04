package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.model.BxMarketData;
import com.ongxeno.bitcoinratewidget.model.suprnova.Suprnova;
import com.ongxeno.bitcoinratewidget.model.suprnova.UserBalance;
import com.ongxeno.bitcoinratewidget.retrofit.client.BxApiClient;
import com.ongxeno.bitcoinratewidget.retrofit.client.GenericPoolClient;

import java.text.NumberFormat;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xenocide93 on 6/18/17.
 */

public abstract class AbstractGenericPoolWidgetProvider extends AbstractWidgetProvider implements ThresholdSetterHandler {

	private static final int NOTIFICATION_TAG = 2342;

	private static final String ACTION_REFRESH_WIDGET = "refresh-widget";

	private String bxString = "Not init";
	private String hashRateString = "Not init";
	private String balanceString = "Not init";
	private String token;

	/**
	 * Gets access token for the api.
	 * 
	 * @param context
	 *            context
	 * 
	 * @return token
	 */
	protected abstract String initToken(Context context);

	/**
	 * Gets coin type that's part of base url for the api.
	 *
	 * @return coin type.
	 */
	protected abstract String getCoinmineCoinType();

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

	@Override
	public int getWidgetLayoutId() {
		return R.layout.widget_bitcoin_rate;
	}

	@Override
	public int getClickUpdateViewId() {
		return R.id.rootView;
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		super.onReceive(context, intent);

		token = initToken(context);
		if (token == null || token.isEmpty()) {
			bxString = "No Token";
			hashRateString = "open app to setup";
			balanceString = "";
			updateWidget(context);
		} else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
				|| intent.getAction().equals(ACTION_REFRESH_WIDGET)) {
			updateWidget(context);
			updateBX(context);
			updateSuprnova(context);
			updateBalance(context);
		}
	}

	private void updateBalance(final Context context) {
		new GenericPoolClient(token, getBaseUrl()).getUserBalance(new Callback<UserBalance>() {
			@Override
			public void onResponse(Call<UserBalance> call, Response<UserBalance> response) {
				if (response.body() == null) {
					balanceString = "Error";
					updateWidget(context);
					return;
				}

				UserBalance balance = response.body();
				if (balance != null) {
					Double balanceDouble = balance.getUserBalanceData().getBalanceData().getConfirmed()
							+ balance.getUserBalanceData().getBalanceData().getUnconfirmed();
					balanceString = formatBalance(balanceDouble, 3) + " " + getBalanceSuffix();
					updateWidget(context);
				}
			}

			@Override
			public void onFailure(Call<UserBalance> call, Throwable t) {
				balanceString = "Error";
				updateWidget(context);
			}
		});
	}

	private void updateBX(final Context context) {
		BxApiClient.getAllMarketData(new Callback<Map<Integer, BxMarketData>>() {
			@Override
			public void onResponse(@NonNull Call<Map<Integer, BxMarketData>> call,
					@NonNull Response<Map<Integer, BxMarketData>> response) {
				if (response.body() == null) {
					return;
				}

				BxMarketData btcThbMarketData = response.body().get(1);
				if (btcThbMarketData != null) {
					bxString = formatNumber(btcThbMarketData.lastPrice, 0);
					Toast.makeText(context, bxString, Toast.LENGTH_SHORT).show();
				} else {
					bxString = "Error";
				}
				updateWidget(context);
			}

			@Override
			public void onFailure(@NonNull Call<Map<Integer, BxMarketData>> call, @NonNull Throwable t) {
				bxString = "Error";
				updateWidget(context);
			}
		});
	}

	private void updateSuprnova(final Context context) {
		GenericPoolClient client = new GenericPoolClient(token, getBaseUrl());
		client.getUserStatus(new Callback<Suprnova>() {
			@Override
			public void onResponse(@NonNull Call<Suprnova> call, @NonNull Response<Suprnova> response) {
				if (response.body() == null) {
					return;
				}

				Suprnova suprnova = response.body();
				if (suprnova != null) {
					Double hashRate = suprnova.getUserStatus().getShareRateData().getHashrate() * getHashRateDisplayFactor();
					hashRateString = formatNumber(hashRate, 0) + " " + getHashRateSuffix();
					Toast.makeText(context, hashRateString, Toast.LENGTH_SHORT).show();
					double threshold = getMinHashRateThreshold();
					if (threshold > 0 && hashRate < threshold) {
						notifyWorkerDown(context, hashRate);
					}
				} else {
					hashRateString = "Error";
				}
				updateWidget(context);
			}

			@Override
			public void onFailure(@NonNull Call<Suprnova> call, @NonNull Throwable t) {
				hashRateString = "Error";
				updateWidget(context);
			}
		});
	}

	private void updateWidget(Context context) {
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
		final ComponentName widget = new ComponentName(context, getClass());

		remoteViews.setTextViewText(R.id.lastPriceTextView, bxString);
		remoteViews.setTextViewText(R.id.suprnovaTextView, hashRateString);
		remoteViews.setTextViewText(R.id.balanceTextView, balanceString);
		(AppWidgetManager.getInstance(context)).updateAppWidget(widget, remoteViews);

		remoteViews.setOnClickPendingIntent(R.id.rootView, getPendingSelfIntent(context, ACTION_REFRESH_WIDGET));
	}

	private void notifyWorkerDown(Context context, Double hashRate) {
		String title = "Worker Down!";
		String content = String.format("Hash rate is down to %s. Check it now.", formatNumber(hashRate, 0));

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle(title);
		builder.setContentText(content);
		builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
		builder.setSmallIcon(R.drawable.ic_warning_white_24dp);
		NotificationManagerCompat.from(context).notify(NOTIFICATION_TAG, builder.build());
	}

	private String formatNumber(int number) {
		return NumberFormat.getIntegerInstance().format(number);
	}

	private String formatNumber(double number, int precision) {
		return NumberFormat.getIntegerInstance().format(((int) number * Math.pow(10, precision)) / Math.pow(10, precision));
	}

	private String formatBalance(double d, int precision) {
		return String.valueOf(((int) (d * Math.pow(10, precision))) / Math.pow(10, precision));
	}

	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final RemoteViews remoteViews,
			final int widgetId) {
		Log.d(getClass().getSimpleName(), "onUpdate: " + widgetId);
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	protected double getHashRateDisplayFactor() {
		return 1d;
	}
}
