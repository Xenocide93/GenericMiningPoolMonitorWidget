package com.ongxeno.bitcoinratewidget.widgetprovider.coinmine;

import com.ongxeno.bitcoinratewidget.common.preference.CoinminePref;

/**
 * @author Xenocide93 on 8/4/17.
 */

public class ZecCoinmineWidgetProvider extends AbstractCoinmineWidgetProvider {
	@Override
	protected String getCoinmineCoinType() {
		return "zec";
	}

	@Override
	protected String getHashRateSuffix() {
		return "Sol/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "Zec";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 1500d;
	}

	@Override
	protected double getHashRateDisplayFactor() {
		return 1d;
	}

	@Override
	protected void onSetThreshold(CoinminePref pref, double threshold) {
		pref.setThresholdZec(threshold);
	}
}
