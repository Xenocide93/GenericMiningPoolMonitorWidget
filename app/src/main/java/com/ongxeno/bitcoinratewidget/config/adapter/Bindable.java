package com.ongxeno.bitcoinratewidget.config.adapter;

/**
 * @author Xenocide93 on 9/9/17.
 */

public interface Bindable<T> {
	void onBind(int position, T data);
}
