package com.ongxeno.bitcoinratewidget.widgetprovider.coinmine;

import android.content.Context;

import com.ongxeno.bitcoinratewidget.common.preference.CoinminePref;
import com.ongxeno.bitcoinratewidget.common.preference.Preference;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractGenericPoolWidgetProvider;

/**
 * @author Xenocide93 on 8/4/17.
 */
public abstract class AbstractCoinmineWidgetProvider extends AbstractGenericPoolWidgetProvider {

	private static final String BASE_COINMINE_URL = "https://www2.coinmine.pl/";

	public static final int POOL_ID = 2;

	@Override
	protected String getBaseUrl() {
		return BASE_COINMINE_URL + getCoinmineCoinType() + "/";
	}

	@Override
	protected String initToken(Context context) {
		return Preference.getInstance(context).getCoinmineApiToken();
	}

	@Override
	public void onSetThreshold(Context context, double threshold) {
		onSetThreshold(CoinminePref.getInstance(context), threshold);
	}

	protected abstract void onSetThreshold(CoinminePref pref, double threshold);

	@Override
	protected int getPoolId() {
		return POOL_ID;
	}

	/**
	 * Gets coin type that's part of base url for the api.
	 *
	 * @return coin type.
	 */
	protected abstract String getCoinmineCoinType();

	@Override
	protected boolean hasInit() {
		return true;
	}
}
