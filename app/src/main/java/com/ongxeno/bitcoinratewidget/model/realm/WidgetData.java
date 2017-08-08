package com.ongxeno.bitcoinratewidget.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Xenocide93 on 8/8/17.
 */

public class WidgetData extends RealmObject {

	public static final String PRIMARY_KEY = "widgetId";
	public static final String KEY_API_KEY = "apiKey";
	public static final String KEY_BASE_URL = "baseUrl";
	public static final String KEY_HASH_RATE_UNIT = "hashRateUnit";
	public static final String KEY_BALANCE_UNIT = "balanceUnit";
	public static final String KEY_MIN_HASH_RATE = "minHashRateThreshold";
	public static final String KEY_HASH_RATE_FACTOR = "hashRateDisplayFactor";


	@PrimaryKey
	private int widgetId;
	private String apiKey;
	private String baseUrl;
	private String hashRateUnit;
	private String balanceUnit;
	private double minHashRateThreshold;
	private double hashRateDisplayFactor;

	public WidgetData() {
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getHashRateUnit() {
		return hashRateUnit;
	}

	public void setHashRateUnit(String hashRateUnit) {
		this.hashRateUnit = hashRateUnit;
	}

	public String getBalanceUnit() {
		return balanceUnit;
	}

	public void setBalanceUnit(String balanceUnit) {
		this.balanceUnit = balanceUnit;
	}

	public double getMinHashRateThreshold() {
		return minHashRateThreshold;
	}

	public void setMinHashRateThreshold(double minHashRateThreshold) {
		this.minHashRateThreshold = minHashRateThreshold;
	}

	public double getHashRateDisplayFactor() {
		return hashRateDisplayFactor;
	}

	public void setHashRateDisplayFactor(double hashRateDisplayFactor) {
		this.hashRateDisplayFactor = hashRateDisplayFactor;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
