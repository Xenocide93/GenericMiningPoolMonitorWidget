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
		if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)) {

		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onSetThreshold(Context context, double threshold) {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		widgetData.setMinHashRateThreshold(threshold);
		realm.commitTransaction();
		realm.close();
	}

	@Override
	protected boolean hasInit() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		boolean widgetDataExist = widgetData != null;
		realm.commitTransaction();
		realm.close();
		return getWidgetId() != -1 && widgetDataExist;
	}

	@Override
	protected String initToken(Context context) {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String apiKey = widgetData.getApiKey();
		realm.commitTransaction();
		realm.close();
		return apiKey;
	}

	@Override
	protected String getBaseUrl() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String baseUrl = widgetData.getBaseUrl();
		realm.commitTransaction();
		realm.close();
		return baseUrl;
	}

	@Override
	protected String getHashRateSuffix() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String unit = widgetData.getHashRateUnit();
		realm.commitTransaction();
		realm.close();
		return unit;
	}

	@Override
	protected String getBalanceSuffix() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		String unit = widgetData.getBalanceUnit();
		realm.commitTransaction();
		realm.close();
		return unit;
	}

	@Override
	protected double getMinHashRateThreshold() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		WidgetData widgetData = realm.where(WidgetData.class).equalTo(WidgetData.PRIMARY_KEY, getWidgetId()).findFirst();
		Double hashRateThreshold = widgetData.getMinHashRateThreshold();
		realm.commitTransaction();
		realm.close();
		return hashRateThreshold;
	}

	@Override
	protected int getPoolId() {
		return 999;
	}


}
