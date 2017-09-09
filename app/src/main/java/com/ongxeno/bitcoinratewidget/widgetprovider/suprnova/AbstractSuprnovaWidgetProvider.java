package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import android.content.Context;

import com.ongxeno.bitcoinratewidget.common.preference.Preference;
import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractGenericPoolWidgetProvider;

/**
 * @author Xenocide93 on 8/4/17.
 */

public abstract class AbstractSuprnovaWidgetProvider extends AbstractGenericPoolWidgetProvider {

	private static final String HTTP = "https://";

	private static final String SUPRNOVA_BASE_URL = ".suprnova.cc/";

	public static final int POOL_ID = 1;

	@Override
	protected String getBaseUrl() {
		return HTTP + getSuprnovaCoinType() + SUPRNOVA_BASE_URL;
	}

	@Override
	protected String getToken(Context context) {
		return Preference.getInstance(context).getSuprnovaApiToken();
	}

	@Override
	public void onSetThreshold(Context context, double threshold) {
		onSetThreshold(SuprnovaPref.getInstance(context), threshold);
	}

	protected abstract void onSetThreshold(SuprnovaPref pref, double threshold);

	@Override
	protected int getPoolId() {
		return POOL_ID;
	}

	abstract String getSuprnovaCoinType();

	@Override
	public boolean hasInit() {
		return true;
	}

	@Override
	public boolean hasSetupToken(Context context) {
		String token = Preference.getInstance(context).getSuprnovaApiToken();
		return  token != null && !token.isEmpty();
	}
}
