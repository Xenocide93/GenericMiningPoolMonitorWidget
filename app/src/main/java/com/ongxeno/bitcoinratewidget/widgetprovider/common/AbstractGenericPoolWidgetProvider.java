package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.common.preference.Constant;
import com.ongxeno.bitcoinratewidget.model.bx.BxMarketData;
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
	private static final String ACTION_SET_THRESHOLD = "set-threshold";

	private String bxString = "Not init";
	private String hashRateString = "Not init";
	private String balanceString = "Not init";
	private String token;
	private int widgetId = -1;

	public static Intent getThresholdSetterIntent(Context context, double threshold, int poolId, String coinType, int widgetId) {
		Intent intent = new Intent(context, AbstractGenericPoolWidgetProvider.class);
		intent.setAction(ACTION_SET_THRESHOLD);
		intent.putExtra(Constant.EXTRA_THRESHOLD, threshold);
		intent.putExtra(Constant.EXTRA_POOL_ID, poolId);
		intent.putExtra(Constant.EXTRA_COIN_TYPE, coinType);
		intent.putExtra(Constant.EXTRA_WIDGET_ID, widgetId);
		return intent;
	}

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
		if (!hasInit()) {
			updateWidgetUi(context);
		} else {
			token = initToken(context);
			if (token == null || token.isEmpty()) {
				bxString = "No Token";
				hashRateString = "open app to setup";
				balanceString = "";
				updateWidgetUi(context);
			} else if (intent.getAction().equals(ACTION_SET_THRESHOLD)) {
				handleSetThreshold(context, intent);
			} else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
					|| intent.getAction().equals(ACTION_REFRESH_WIDGET)) {
				handleRefreshWidget(context);
			}
		}
	}

	private void handleSetThreshold(Context context, Intent intent) {
		double threshold = intent.getDoubleExtra(Constant.EXTRA_THRESHOLD, 0);
		int poolId = intent.getIntExtra(Constant.EXTRA_POOL_ID, 0);
		String coinType = intent.getStringExtra(Constant.EXTRA_COIN_TYPE);
		int targetWidgetId = intent.getIntExtra(Constant.EXTRA_WIDGET_ID, -2);
		if (targetWidgetId == widgetId) {
			onSetThreshold(context, threshold);
		}
	}

	private void handleRefreshWidget(Context context) {
		updateWidgetUi(context);
		updateBX(context);
		updateSuprnova(context);
		updateBalance(context);
	}

	private void updateBalance(final Context context) {
		new GenericPoolClient(token, getBaseUrl()).getUserBalance(new Callback<UserBalance>() {
			@Override
			public void onResponse(Call<UserBalance> call, Response<UserBalance> response) {
				if (response.body() == null) {
					balanceString = "Error";
					updateWidgetUi(context);
					return;
				}

				UserBalance balance = response.body();
				if (balance != null) {
					Double balanceDouble = balance.getUserBalanceData().getBalanceData().getConfirmed()
							+ balance.getUserBalanceData().getBalanceData().getUnconfirmed();
					balanceString = formatBalance(balanceDouble, 3) + " " + getBalanceSuffix();
					updateWidgetUi(context);
				}
			}

			@Override
			public void onFailure(Call<UserBalance> call, Throwable t) {
				balanceString = "Error";
				updateWidgetUi(context);
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
				updateWidgetUi(context);
			}

			@Override
			public void onFailure(@NonNull Call<Map<Integer, BxMarketData>> call, @NonNull Throwable t) {
				bxString = "Error";
				updateWidgetUi(context);
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
				updateWidgetUi(context);
			}

			@Override
			public void onFailure(@NonNull Call<Suprnova> call, @NonNull Throwable t) {
				hashRateString = "Error";
				updateWidgetUi(context);
			}
		});
	}

	private void updateWidgetUi(Context context) {
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
		this.widgetId = widgetId;
		if (hasInit()) {
			handleRefreshWidget(context);
		}
	}

	protected abstract boolean hasInit();

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

	protected int getWidgetId() {
		return widgetId;
	}

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
