package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;

/**
 * @author Xenocide93 on 8/4/17.
 */

public abstract class AbstractGenericPoolPreference extends AbstractPreference {

	private static final String THRESHOLD_HUSH = "threshold-hush";
	private static final String THRESHOLD_ZEC = "threshold-zec";
	private static final String THRESHOLD_LBRY = "threshold-lbry";
	private static final String THRESHOLD_SIGT = "threshold-sigt";

	public AbstractGenericPoolPreference(Context context) {
		super(context);
	}

	public double getThresholdHush() {
		return getPref().getFloat(THRESHOLD_HUSH, 0f);
	}

	public void setThresholdHush(double threshold) {
		getPref().edit().putFloat(THRESHOLD_HUSH, (float) threshold).apply();
	}

	public double getThresholdZec() {
		return getPref().getFloat(THRESHOLD_ZEC, 0f);
	}

	public void setThresholdZec(double threshold) {
		getPref().edit().putFloat(THRESHOLD_ZEC, (float) threshold).apply();
	}

	public double getThresholdLbry() {
		return getPref().getFloat(THRESHOLD_LBRY, 0f);
	}

	public void setThresholdLbry(double threshold) {
		getPref().edit().putFloat(THRESHOLD_LBRY, (float) threshold).apply();
	}

	public double getThresholdSigt() {
		return getPref().getFloat(THRESHOLD_SIGT, 0f);
	}

	public void setThresholdSigt(double threshold) {
		getPref().edit().putFloat(THRESHOLD_SIGT, (float) threshold).apply();
	}
}
