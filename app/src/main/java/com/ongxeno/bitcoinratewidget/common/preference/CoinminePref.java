package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;

/**
 * @author Xenocide93 on 8/4/17.
 */

public class CoinminePref extends AbstractGenericPoolPreference {

	private static final String PREF_NAME = "coinmine-private-pref";

	private static CoinminePref instance;

	public static CoinminePref getInstance(Context context) {
		if (instance == null) {
			instance = new CoinminePref(context);
		}

		return instance;
	}

	protected CoinminePref(Context context) {
		super(context);
	}

	@Override
	protected String getPreferenceName() {
		return PREF_NAME;
	}
}
