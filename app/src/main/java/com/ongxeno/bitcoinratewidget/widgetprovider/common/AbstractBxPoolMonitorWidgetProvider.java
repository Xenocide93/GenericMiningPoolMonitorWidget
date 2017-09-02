package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.common.preference.Constant;
import com.ongxeno.bitcoinratewidget.model.bx.BxMarketData;
import com.ongxeno.bitcoinratewidget.retrofit.client.BxApiClient;

import java.text.NumberFormat;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xenocide93 on 9/2/17.
 */

public abstract class AbstractBxPoolMonitorWidgetProvider extends AbstractWidgetProvider implements ThresholdSetterHandler  {

	private static final int NOTIFICATION_TAG = 2342;
	private static final String ACTION_REFRESH_WIDGET = "refresh-widget";
	private static final String ACTION_SET_THRESHOLD = "set-threshold";

	private String bxString;
	private String balanceString;
	private String hashRateString;

	@Override
	public int getWidgetLayoutId() {
		return R.layout.widget_bitcoin_rate;
	}

	@Override
	public int getClickUpdateViewId() {
		return R.id.rootView;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, RemoteViews remoteViews, int widgetId) {
		if (hasInit()) {
			handleRefreshWidget(context);
		}
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		super.onReceive(context, intent);
		if (!hasInit()) {
			updateWidgetUi(context, "Not Init", "Not Init", "Not Init");
		} else {
			if (!hasSetupToken(context)) {
				updateWidgetUi(context, "No Token", "open app to setup", "");
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
		if (targetWidgetId == getWidgetId()) {
			onSetThreshold(context, threshold);
		}
	}

	protected void updateWidgetUi(Context context) {
		updateWidgetUi(context, bxString, hashRateString, balanceString);
	}

	protected void updateWidgetUi(Context context, String bxString, String hashRateString, String balanceString) {
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
		final ComponentName widget = new ComponentName(context, getClass());

		remoteViews.setTextViewText(R.id.lastPriceTextView, bxString);
		remoteViews.setTextViewText(R.id.suprnovaTextView, hashRateString);
		remoteViews.setTextViewText(R.id.balanceTextView, balanceString);
		(AppWidgetManager.getInstance(context)).updateAppWidget(widget, remoteViews);

		remoteViews.setOnClickPendingIntent(R.id.rootView, getPendingSelfIntent(context, ACTION_REFRESH_WIDGET));
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	protected String formatNumber(double number, int precision) {
		return NumberFormat.getIntegerInstance().format(((int) number * Math.pow(10, precision)) / Math.pow(10, precision));
	}

	protected void notifyWorkerDown(Context context, Double hashRate) {
		String title = "Worker Down!";
		String content = String.format("Hash rate is down to %s. Check it now.", formatNumber(hashRate, 0));

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle(title);
		builder.setContentText(content);
		builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
		builder.setSmallIcon(R.drawable.ic_warning_white_24dp);
		NotificationManagerCompat.from(context).notify(NOTIFICATION_TAG, builder.build());
	}

	public void handleRefreshWidget(Context context) {
		updateWidgetUi(context, "Loading ...", "", "");
		updateBX(context);
		updateHashRate(context);
		updateBalance(context);
	}

	protected void updateBX(final Context context) {
		BxApiClient.getAllMarketData(new Callback<Map<Integer, BxMarketData>>() {
			@Override
			public void onResponse(@NonNull Call<Map<Integer, BxMarketData>> call,
								   @NonNull Response<Map<Integer, BxMarketData>> response) {
				if (response.body() == null) {
					return;
				}

				BxMarketData btcThbMarketData = response.body().get(1);
				if (btcThbMarketData != null) {
					String bxString = formatNumber(btcThbMarketData.lastPrice, 0);
					setBxString(bxString);
					Toast.makeText(context, bxString, Toast.LENGTH_SHORT).show();
				} else {
					setBxString("Error");
				}
				updateWidgetUi(context);
			}

			@Override
			public void onFailure(@NonNull Call<Map<Integer, BxMarketData>> call, @NonNull Throwable t) {
				setBxString("Error");
				updateWidgetUi(context);
			}
		});
	}

	public abstract void updateHashRate(Context context);

	public abstract void updateBalance(Context context);

	public abstract boolean hasSetupToken(Context context);

	public abstract boolean hasInit();

	public void setBxString(String bxString) {
		this.bxString = bxString;
	}

	public void setBalanceString(String balanceString) {
		this.balanceString = balanceString;
	}

	public void setHashRateString(String hashRateString) {
		this.hashRateString = hashRateString;
	}
}