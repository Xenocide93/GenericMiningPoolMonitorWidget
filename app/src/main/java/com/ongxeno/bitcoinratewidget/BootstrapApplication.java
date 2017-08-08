package com.ongxeno.bitcoinratewidget;

import android.app.Application;

import io.realm.Realm;

/**
 * @author Xenocide93 on 8/8/17.
 */

public class BootstrapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.init(this);
	}
}
