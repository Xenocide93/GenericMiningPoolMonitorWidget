package com.ongxeno.bitcoinratewidget.config;

import android.view.View;

/**
 * @author Xenocide93 on 9/9/17.
 */

public interface OnClickDataListener<T> {
	void onClick(View v, T data);
}
