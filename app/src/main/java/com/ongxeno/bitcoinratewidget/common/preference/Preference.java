package com.ongxeno.bitcoinratewidget.common.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Xenocide93 on 8/3/17.
 */

public class Preference {

	private static final String SUPRNOVA_API_TOKEN = "suprnova-api-token";

	private static final String COINMINE_API_TOKEN = "coinmine-api-token";

	private static final String DWARFPOOL_WALLET = "dwarf-wallet";

	private static final String DWARFPOOL_EMAIL = "dwarf-email";

	private static Preference instance;

	public static Preference getInstance(Context context) {
		if (instance == null) {
			instance = new Preference(context);
		}

		return instance;
	}

	private SharedPreferences pref;

	public Preference(Context context) {
		pref = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String getSuprnovaApiToken() {
		return pref.getString(SUPRNOVA_API_TOKEN, "");
	}

	public void setSuprnovaApiToken(String token) {
		pref.edit().putString(SUPRNOVA_API_TOKEN, token).apply();
	}

	public String getCoinmineApiToken() {
		return pref.getString(COINMINE_API_TOKEN, "");
	}

	public void setCoinmineApiToken(String token) {
		pref.edit().putString(COINMINE_API_TOKEN, token).apply();
	}

	public String getDwarfpoolWallet() {
		return pref.getString(DWARFPOOL_WALLET, "");
	}

	public void setDwarfpoolWallet(String walletAddress) {
		pref.edit().putString(DWARFPOOL_WALLET, walletAddress).apply();
	}

	public String getDwarfpoolEmail() {
		return pref.getString(DWARFPOOL_EMAIL, "");
	}

	public void setDwarfpoolEmail(String email) {
		pref.edit().putString(DWARFPOOL_EMAIL, email).apply();
	}


}
