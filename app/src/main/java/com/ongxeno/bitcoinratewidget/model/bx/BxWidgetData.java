package com.ongxeno.bitcoinratewidget.model.bx;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Xenocide93 on 9/9/17.
 */

public class BxWidgetData extends RealmObject {
	@PrimaryKey
	private int widgetId;
	private int paringId;
	private int threshold;

	public BxWidgetData() {
	}

	public BxWidgetData(int widgetId, int paringId, int threshold) {
		this.paringId = paringId;
		this.widgetId = widgetId;
		this.threshold = threshold;
	}

	public int getParingId() {
		return paringId;
	}

	public void setParingId(int paringId) {
		this.paringId = paringId;
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
