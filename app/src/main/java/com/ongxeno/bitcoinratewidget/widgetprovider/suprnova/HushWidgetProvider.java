package com.ongxeno.bitcoinratewidget.widgetprovider.suprnova;

import com.ongxeno.bitcoinratewidget.common.preference.SuprnovaPref;

/**
 * @author Xenocide93 on 6/18/17.
 */

public class HushWidgetProvider extends AbstractSuprnovaWidgetProvider {

	@Override
	protected String getCoinmineCoinType() {
		return "hush";
	}

	@Override
	protected String getHashRateSuffix() {
		return "Sol/s";
	}

	@Override
	protected String getBalanceSuffix() {
		return "Hush";
	}

	@Override
	protected double getMinHashRateThreshold() {
		return 790000d;
	}

	@Override
	protected void onSetThreshold(SuprnovaPref pref, double threshold) {
		pref.setThresholdHush(threshold);
	}
}
