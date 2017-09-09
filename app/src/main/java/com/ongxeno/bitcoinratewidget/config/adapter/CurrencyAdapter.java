package com.ongxeno.bitcoinratewidget.config.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.config.OnClickDataListener;

import java.util.List;

/**
 * @author Xenocide93 on 9/9/17.
 */

public class CurrencyAdapter<T> extends RecyclerView.Adapter {

	private List<T> data;
	private OnBindCurrencyHandler<T> onBindCurrencyHandler;
	private OnClickDataListener<T> onClickDataListener;

	public CurrencyAdapter(List<T> data, OnBindCurrencyHandler<T> onBindCurrencyHandler) {
		this.onBindCurrencyHandler = onBindCurrencyHandler;
		this.data = data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setOnClickDataListener(OnClickDataListener<T> onClickDataListener) {
		this.onClickDataListener = onClickDataListener;
	}

	@Override
	public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new CurrencyViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_currency, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof Bindable) {
			((Bindable) holder).onBind(position, getData(position));
		}
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	private T getData(int position) {
		return data.get(position);
	}

	class CurrencyViewHolder extends RecyclerView.ViewHolder implements Bindable<T> {

		TextView textView;
		T data;

		CurrencyViewHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView;
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onClickDataListener != null && data != null) {
						onClickDataListener.onClick(v, data);
					}
				}
			});
		}

		@Override
		public void onBind(int position, T data) {
			if (onBindCurrencyHandler != null) {
				this.data = data;
				onBindCurrencyHandler.onBind(textView, data);
			}
		}
	}

	public interface OnBindCurrencyHandler<I> {
		void onBind(TextView textView, I data);
	}
}
