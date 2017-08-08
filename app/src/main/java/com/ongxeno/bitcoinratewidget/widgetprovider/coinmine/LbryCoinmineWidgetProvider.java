package com.ongxeno.bitcoinratewidget.widgetprovider.coinmine;

import com.ongxeno.bitcoinratewidget.common.preference.CoinminePref;

/**
 * @author Xenocide93 on 8/4/17.
 */

public class LbryCoinmineWidgetProvider extends AbstractCoinmineWidgetProvider {
	@Override
	protected String getCoinmineCoinType() {
		return "lbc";
	}

	@Override
	protected String getHashRateSuffix() {
		return "MH/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "lbc";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 800d;
	}

	@Override
	protected double getHashRateDisplayFactor() {
		return 0.001d;
	}

	@Override
	protected void onSetThreshold(CoinminePref pref, double threshold) {
		pref.setThresholdLbry(threshold);
	}
}
