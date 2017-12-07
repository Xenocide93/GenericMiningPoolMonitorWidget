package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class MonaWidgetProvider extends AbstractSuprnovaWidgetProvider {

	@Override
	protected String getSuprnovaCoinType() {
		return "mona";
	}

	@Override
	protected String getHashRateSuffix() {
		return "MH/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "MONA";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 100d;
	}

	@Override
	protected void onSetThreshold(SuprnovaPref pref, double threshold) {
		pref.setThresholdMona(threshold);
	}
}
