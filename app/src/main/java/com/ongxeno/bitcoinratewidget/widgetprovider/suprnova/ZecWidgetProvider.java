package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class ZecWidgetProvider extends AbstractSuprnovaWidgetProvider {

	@Override
	protected String getSuprnovaCoinType() {
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
		return 1400d;
	}

	@Override
	protected void onSetThreshold(SuprnovaPref pref, double threshold) {
		pref.setThresholdZec(threshold);
	}
}
