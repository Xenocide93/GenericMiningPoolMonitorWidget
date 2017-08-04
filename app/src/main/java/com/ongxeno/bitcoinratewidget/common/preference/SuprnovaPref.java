package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;

/**
 * @author Xenocide93 on 8/4/17.
 */

public class SuprnovaPref extends AbstractGenericPoolPreference {

	private static final String PREF_NAME = "suprnova-private-pref";

	private static SuprnovaPref instance;

	public static SuprnovaPref getInstance(Context context) {
		if (instance == null) {
			instance = new SuprnovaPref(context);
		}

		return instance;
	}

	protected SuprnovaPref(Context context) {
		super(context);
	}

	@Override
	protected String getPreferenceName() {
		return PREF_NAME;
	}
}
