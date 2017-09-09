package com.ongxeno.bitcoinratewidget.widgetprovider;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import com.ongxeno.bitcoinratewidget.model.realm.WidgetData;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractGenericPoolWidgetProvider;

import io.realm.Realm;

/**
 * @author Xenocide93 on 8/9/17.
 */

public class CustomWidgetProvider extends AbstractGenericPoolWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)) {
			super.onReceive(context, intent);
		}
	}

	@Override
	public boolean hasSetupToken(Context context) {
		String token = getToken(context);
		return token != null && token.isEmpty();
	}

	@Override
	public boolean hasInit() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		boolean hasInit = widgetData != null;
		realm.commitTransaction();
		realm.close();
		return hasInit;
	}

	@Override
	public void onSetThreshold(Context context, double threshold) {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		if (widgetData == null) {
			widgetData = new WidgetData();
		}
		widgetData.setMinHashRateThreshold(threshold);
		realm.copyToRealmOrUpdate(widgetData);
		realm.commitTransaction();
		realm.close();
	}

	@Override
	protected String getToken(Context context) {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String apiKey = null;
		if (widgetData != null) {
			apiKey = widgetData.getApiKey();
		}
		realm.commitTransaction();
		realm.close();
		return apiKey;
	}

	@Override
	protected String getBaseUrl() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String baseUrl = null;
		if (widgetData != null) {
			baseUrl = widgetData.getBaseUrl();
		}
		realm.commitTransaction();
		realm.close();
		return baseUrl;
	}

	@Override
	protected String getHashRateSuffix() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String unit = null;
		if (widgetData != null) {
			unit = widgetData.getHashRateUnit();
		}
		realm.commitTransaction();
		realm.close();
		return unit;
	}

	@Override
	protected String getBalanceSuffix() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String unit = null;
		if (widgetData != null) {
			unit = widgetData.getBalanceUnit();
		}
		realm.commitTransaction();
		realm.close();
		return unit;
	}

	@Override
	protected double getMinHashRateThreshold() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		double hashRateThreshold = 0d;
		if (widgetData != null) {
			hashRateThreshold = widgetData.getMinHashRateThreshold();
		}
		realm.commitTransaction();
		realm.close();
		return hashRateThreshold;
	}

	@Override
	protected int getPoolId() {
		return 999;
	}


}
