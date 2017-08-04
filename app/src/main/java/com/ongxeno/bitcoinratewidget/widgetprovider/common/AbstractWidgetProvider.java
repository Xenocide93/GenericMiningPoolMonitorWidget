package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.widget.RemoteViews;

/**
 * @author Xenocide93 on 6/18/17.
 */

public abstract class AbstractWidgetProvider<T> extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getWidgetLayoutId());
            onUpdate(context, appWidgetManager, remoteViews, i);
            setOnClickUpdateListener(context, remoteViews, i);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    private void setOnClickUpdateListener(Context context, RemoteViews remoteViews, int widgetId) {
        Intent intent = new Intent(context, this.getClass());
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(getClickUpdateViewId(), pendingIntent);
    }

    public abstract @LayoutRes int getWidgetLayoutId();

    public abstract @IdRes int getClickUpdateViewId();

    public abstract void onUpdate(Context context, AppWidgetManager appWidgetManager, RemoteViews remoteViews, int widgetId);
}
