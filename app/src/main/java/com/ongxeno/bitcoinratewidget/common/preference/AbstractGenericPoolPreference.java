package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Xenocide93 on 8/4/17.
 */

public abstract class AbstractGenericPoolPreference {

	private static final String THRESHOLD_HUSH = "threshold-hush";
	private static final String THRESHOLD_ZEC = "threshold-zec";
	private static final String THRESHOLD_LBRY = "threshold-lbry";

	private SharedPreferences pref;

	/**
	 * Gets unique share preference name.
	 *
	 * @return name
	 */
	protected abstract String getPreferenceName();

	public AbstractGenericPoolPreference(Context context) {
		pref = context.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
	}

	public double getThresholdHush() {
		return pref.getFloat(THRESHOLD_HUSH, 0f);
	}

	public void setThresholdHush(double threshold) {
		pref.edit().putFloat(THRESHOLD_HUSH, (float) threshold).apply();
	}

	public double getThresholdZec() {
		return pref.getFloat(THRESHOLD_ZEC, 0f);
	}

	public void setThresholdZec(double threshold) {
		pref.edit().putFloat(THRESHOLD_ZEC, (float) threshold).apply();
	}

	public double getThresholdLbry() {
		return pref.getFloat(THRESHOLD_LBRY, 0f);
	}

	public void setThresholdLbry(double threshold) {
		pref.edit().putFloat(THRESHOLD_LBRY, (float) threshold).apply();
	}
}
