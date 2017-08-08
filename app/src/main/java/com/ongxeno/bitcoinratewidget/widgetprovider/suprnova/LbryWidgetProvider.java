package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.CoinminePref;
import com.ongxeno.bitcoinratewidget.widgetprovider.coinmine.AbstractCoinmineWidgetProvider;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class LbryWidgetProvider extends AbstractCoinmineWidgetProvider {

	@Override
	protected String getCoinmineCoinType() {
		return "lbry";
	}

	@Override
	protected String getHashRateSuffix() {
		return "MH/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "LBRY";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 600d;
	}

	@Override
	protected double getHashRateDisplayFactor() {
		return 0.001;
	}

	@Override
	protected void onSetThreshold(CoinminePref pref, double threshold) {
		pref.setThresholdLbry(threshold);
	}
}
