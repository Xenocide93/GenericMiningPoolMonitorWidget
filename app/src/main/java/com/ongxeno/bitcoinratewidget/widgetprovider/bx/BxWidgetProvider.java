package com.ongxeno.bitcoinratewidget.widgetprovider.bx;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.model.bx.BxMarketData;
import com.ongxeno.bitcoinratewidget.model.bx.BxWidgetData;
import com.ongxeno.bitcoinratewidget.retrofit.client.BxApiClient;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractWidgetProvider;

import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xenocide93 on 9/9/17.
 */

public class BxWidgetProvider extends AbstractWidgetProvider {

	private static final int MAX_RETRY = 3;
	private int retry = 0;

	private String bxString = "not init";
	private String paringString = "not init";

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public int getWidgetLayoutId() {
		return R.layout.widget_bx_rate;
	}

	protected void updateWidgetUi(Context context) {
		updateWidgetUi(context, bxString, paringString);
	}

	protected void updateWidgetUi(Context context, String rateString, String paringString) {
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
		final ComponentName widget = new ComponentName(context, getClass());

		remoteViews.setTextViewText(R.id.rateTextView, rateString);
		remoteViews.setTextViewText(R.id.paringTextView, paringString);
		(AppWidgetManager.getInstance(context)).updateAppWidget(widget, remoteViews);

		remoteViews.setOnClickPendingIntent(R.id.rootView, getSelfUpdateUiIntent(context));
	}

	@Override
	public boolean hasInit() {
		return getWidgetId() != 0 && getParingId() != -1;
	}

	@Override
	public void updateUiNotInit(Context context) {
		//Do noting
	}

	@Override
	public int getClickUpdateViewId() {
		return R.id.rootView;
	}

	@Override
	public void onUpdate(Context context, int widgetId) {
		if (hasInit()) {
			updateWidgetUi(context, "updating ...", "");
			updateBX(context);
		}
	}

	protected void updateBX(final Context context) {
		BxApiClient.getAllMarketData(new Callback<Map<Integer, BxMarketData>>() {
			@Override
			public void onResponse(@NonNull Call<Map<Integer, BxMarketData>> call,
					@NonNull Response<Map<Integer, BxMarketData>> response) {
				if (response.body() == null) {
					return;
				}

				BxMarketData marketData = response.body().get(getParingId());
				if (marketData != null) {
					bxString = formatNumber(marketData.lastPrice, 0);
					paringString = marketData.primaryCurrency + "/" + marketData.secondaryCurrency;
					Toast.makeText(context, bxString + " " + paringString, Toast.LENGTH_SHORT).show();
					updateWidgetUi(context);
				} else {
					onFailure();
				}
			}

			@Override
			public void onFailure(@NonNull Call<Map<Integer, BxMarketData>> call, @NonNull Throwable t) {
				onFailure();
			}

			private void onFailure() {
				retry++;
				if (retry >= MAX_RETRY) {
					bxString = "Error";
					paringString = "";
					updateWidgetUi(context);
				} else {
					updateBX(context);
				}
			}
		});
	}

	private int getParingId() {
		int id = -1;
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		BxWidgetData bxWidgetData = realm.where(BxWidgetData.class).equalTo("widgetId", getWidgetId()).findFirst();
		if (bxWidgetData != null) {
			id = bxWidgetData.getParingId();
		}
		realm.commitTransaction();
		realm.close();
		return id;
	}
}
