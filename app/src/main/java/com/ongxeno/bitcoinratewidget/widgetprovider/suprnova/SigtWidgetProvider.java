package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class SigtWidgetProvider extends AbstractSuprnovaWidgetProvider {

	@Override
	protected String getSuprnovaCoinType() {
		return "SIGT";
	}

	@Override
	protected String getHashRateSuffix() {
		return "MH/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "SIGT";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 90d;
	}

	@Override
	protected void onSetThreshold(SuprnovaPref pref, double threshold) {
		pref.setThresholdSigt(threshold);
	}

	@Override
	protected double getHashRateDisplayFactor() {
		return 0.001d;
	}
}
