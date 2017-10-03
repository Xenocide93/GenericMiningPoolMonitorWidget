package com.ongxeno.bitcoinratewidget.config;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.config.adapter.CurrencyAdapter;
import com.ongxeno.bitcoinratewidget.model.bx.BxMarketData;
import com.ongxeno.bitcoinratewidget.model.bx.BxWidgetData;
import com.ongxeno.bitcoinratewidget.retrofit.client.BxApiClient;
import com.ongxeno.bitcoinratewidget.widgetprovider.bx.BxWidgetProvider;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BxRateConfigActivity extends AppCompatActivity {

	private static final int MAX_RETRY = 3;

	private RecyclerView recyclerView;
	private CurrencyAdapter<BxMarketData> adapter;

	private int widgetId;
	private ArrayList<BxMarketData> data = new ArrayList<>();
	private int retry = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bx_rate_config);

		extractData();
		if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			exitOnError("INVALID WIDGET ID");
		}

		setTitle("Select BX Exchange");

		bindView();
		setupRecyclerView();
		loadBxMarketData();
	}

	private void extractData() {
		Intent intent = getIntent();
		widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	private void loadBxMarketData() {
		BxApiClient.getAllMarketData(new Callback<Map<Integer, BxMarketData>>() {
			@Override
			public void onResponse(Call<Map<Integer, BxMarketData>> call, Response<Map<Integer, BxMarketData>> response) {
				try {
					for (int key : response.body().keySet()) {
						data.add(response.body().get(key));
					}
					adapter.setData(data);
					adapter.notifyDataSetChanged();
				} catch (NullPointerException e) {
					onFailure();
				}
			}

			@Override
			public void onFailure(Call<Map<Integer, BxMarketData>> call, Throwable t) {
				onFailure();
			}

			private void onFailure() {
				retry++;
				if (retry >= MAX_RETRY) {
					exitOnError("Couldn't load BxMarketData");
				} else {
					loadBxMarketData();
				}
			}
		});
	}

	private void bindView() {
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
	}

	private void setupRecyclerView() {
		adapter = new CurrencyAdapter<>(data, new OnBindBxData());
		adapter.setOnClickDataListener(new OnCurrencyClickListener());
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
	}

	private class OnBindBxData implements CurrencyAdapter.OnBindCurrencyHandler<BxMarketData> {
		@Override
		public void onBind(TextView textView, BxMarketData data) {
			textView.setText(data.primaryCurrency + " / " + data.secondaryCurrency);
		}
	}

	private class OnCurrencyClickListener implements OnClickDataListener<BxMarketData> {
		@Override
		public void onClick(View v, final BxMarketData data) {
			Realm realm = Realm.getDefaultInstance();
			realm.beginTransaction();
			realm.copyToRealmOrUpdate(new BxWidgetData(widgetId, data.pairingId, 0));
			realm.commitTransaction();
			realm.close();
			setResult();
		}
	}

	private void broadcastUpdateWidget() {
		Intent intent = new Intent(getApplicationContext(), BxWidgetProvider.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		getApplicationContext().sendBroadcast(intent);
	}

	private void setResult() {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		setResult(RESULT_OK, resultValue);
		finish();
		broadcastUpdateWidget();
	}

	private void exitOnError(String cause) {
		Toast.makeText(getApplicationContext(), cause, Toast.LENGTH_SHORT).show();
		finish();
	}
}
