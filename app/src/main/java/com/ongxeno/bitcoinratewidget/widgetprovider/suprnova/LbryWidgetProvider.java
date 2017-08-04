package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class LbryWidgetProvider extends AbstractSuprnovaWidgetProvider {
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
	protected void onSetThreshold(SuprnovaPref pref, double threshold) {
		pref.setThresholdLbry(threshold);
	}
}
