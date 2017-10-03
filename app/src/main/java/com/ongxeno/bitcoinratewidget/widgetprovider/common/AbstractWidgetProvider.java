package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.widget.RemoteViews;

import java.text.NumberFormat;

/**
 * @author Xenocide93 on 6/18/17.
 */

public abstract class AbstractWidgetProvider extends AppWidgetProvider {

	public static final String ACTION_REFRESH_WIDGET = "refresh-widget";

	private int widgetId;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int i = 0; i < appWidgetIds.length; i++) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
			onUpdateImpl(context, appWidgetManager, remoteViews, appWidgetIds[i]);
			setOnClickUpdateListener(context, remoteViews, appWidgetIds[i]);
			appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		if (id != AppWidgetManager.INVALID_APPWIDGET_ID) {
			widgetId = id;
		}
		if (!hasInit()) {
			updateUiNotInit(context);
		} else {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
			setOnClickUpdateListener(context, remoteViews, getWidgetId());

			if (intent.getAction().equals(ACTION_REFRESH_WIDGET)
					|| intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
					|| intent.getAction().equals(ACTION_REFRESH_WIDGET)) {
				onUpdate(context, widgetId);
			}
		}
	}

	private void setOnClickUpdateListener(Context context, RemoteViews remoteViews, int widgetId) {
		Intent intent = new Intent(context, this.getClass());
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(getClickUpdateViewId(), pendingIntent);
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	protected PendingIntent getSelfUpdateUiIntent(Context context) {
		return getPendingSelfIntent(context, ACTION_REFRESH_WIDGET);
	}

	public abstract @LayoutRes int getWidgetLayoutId();

	public abstract @IdRes int getClickUpdateViewId();

	public void onUpdateImpl(Context context, AppWidgetManager appWidgetManager, RemoteViews remoteViews, int widgetId) {
		this.widgetId = widgetId;
		onUpdate(context, widgetId);
	}

	public abstract void onUpdate(Context context, int widgetId);

	public int getWidgetId() {
		return widgetId;
	}

	public abstract boolean hasInit();

	public abstract void updateUiNotInit(Context context);

	protected String formatNumber(double number, int precision) {
		return NumberFormat.getIntegerInstance().format(((int) number * Math.pow(10, precision)) / Math.pow(10, precision));
	}
}
