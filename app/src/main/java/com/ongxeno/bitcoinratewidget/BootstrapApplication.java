package com.ongxeno.bitcoinratewidget;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Xenocide93 on 8/8/17.
 */

public class BootstrapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.init(this);
		RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
		Realm.setDefaultConfiguration(config);
	}
}
