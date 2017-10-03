package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Xenocide93 on 9/9/17.
 */

public abstract class AbstractPreference {

	private SharedPreferences pref;

	/**
	 * Gets unique share preference name.
	 *
	 * @return name
	 */
	protected abstract String getPreferenceName();

	public AbstractPreference(Context context) {
		pref = context.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
	}

	protected SharedPreferences getPref() {
		return pref;
	}

}
