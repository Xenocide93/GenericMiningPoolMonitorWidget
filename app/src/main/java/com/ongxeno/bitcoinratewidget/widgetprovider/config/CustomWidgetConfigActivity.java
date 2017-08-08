package com.ongxeno.bitcoinratewidget.widgetprovider.config;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.model.realm.WidgetData;
import com.ongxeno.bitcoinratewidget.widgetprovider.CustomWidgetProvider;

import io.realm.Realm;

public class CustomWidgetConfigActivity extends AppCompatActivity {

	private View rootView;
	private EditText baseUrlEditText;
	private EditText apiKeyEditText;
	private EditText hashRateUnitEditText;
	private EditText balanceUnitEditText;
	private EditText minHashRateEditText;
	private EditText hashRateFactorEditText;
	private Button baseUrlPasteButton;
	private Button apiKeyPasteButton;
	private Button minHashRateOffButton;
	private Button defaultHashRateFactorButton;
	private Button saveButton;

	private int widgetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_widget_config);

		extractExtra();
		bindView();

		saveButton.setOnClickListener(new OnSaveButtonClickListener());
	}

	private void extractExtra() {
		Intent intent = getIntent();
		widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		rootView = parent;
		return super.onCreateView(parent, name, context, attrs);
	}

	private void bindView() {
		baseUrlEditText = (EditText) findViewById(R.id.baseUrlEditText);
		apiKeyEditText = (EditText) findViewById(R.id.apiKeyEditText);
		hashRateUnitEditText = (EditText) findViewById(R.id.hashRateUnitEditText);
		balanceUnitEditText = (EditText) findViewById(R.id.balanceUnitEditText);
		minHashRateEditText = (EditText) findViewById(R.id.minHashRateEditText);
		hashRateFactorEditText = (EditText) findViewById(R.id.hashRateFactorEditText);
		baseUrlPasteButton = (Button) findViewById(R.id.baseUrlPasteButton);
		apiKeyPasteButton = (Button) findViewById(R.id.apiKeyPasteButton);
		minHashRateOffButton = (Button) findViewById(R.id.minHashRateOffButton);
		defaultHashRateFactorButton = (Button) findViewById(R.id.defaultHashRateFactorButton);
		saveButton = (Button) findViewById(R.id.saveButton);
	}

	private class OnSaveButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (saveWidgetDateToRealm()) {
				Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, CustomWidgetConfigActivity.this,
						CustomWidgetProvider.class);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId);
				sendBroadcast(intent);
				CustomWidgetConfigActivity.this.sendBroadcast(intent);
				setResult();
			} else {
				Snackbar.make(rootView, "Error: Exception was thrown in saveWidgetDateToRealm()", Snackbar.LENGTH_LONG).show();
			}
		}
	}

	private boolean saveWidgetDateToRealm() {
		try {
			String apiKey = apiKeyEditText.getText().toString();
			String baseUrl = baseUrlEditText.getText().toString();
			String hashRateUnit = hashRateUnitEditText.getText().toString();
			String balanceUnit = balanceUnitEditText.getText().toString();
			double minHashRate = Double.parseDouble(minHashRateEditText.getText().toString());
			double hashRateFactor = Double.parseDouble(hashRateFactorEditText.getText().toString());

			Realm realm = Realm.getDefaultInstance();
			realm.beginTransaction();

			WidgetData widgetData = new WidgetData();
			widgetData.setWidgetId(widgetId);
			widgetData.setApiKey(apiKey);
			widgetData.setBaseUrl(baseUrl);
			widgetData.setHashRateUnit(hashRateUnit);
			widgetData.setBalanceUnit(balanceUnit);
			widgetData.setMinHashRateThreshold(minHashRate);
			widgetData.setHashRateDisplayFactor(hashRateFactor);
			realm.copyToRealmOrUpdate(widgetData);

			realm.commitTransaction();
			realm.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void setResult() {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		setResult(RESULT_OK, resultValue);
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}
}
