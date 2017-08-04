package com.ongxeno.bitcoinratewidget.widgetprovider.common;

import android.content.Context;

/**
 * @author Xenocide93 on 8/4/17.
 */

public interface ThresholdSetterHandler {

	/**
	 * Handle how the threshold is set to Preference.
	 *
	 * @param context
	 *            context
	 * @param threshold
	 *            threshold
	 */
	void onSetThreshold(Context context, double threshold);
}
