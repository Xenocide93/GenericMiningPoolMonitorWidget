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
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.R;
import com.ongxeno.bitcoinratewidget.common.preference.Constant;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractBxPoolMonitorWidgetProvider;
import com.ongxeno.bitcoinratewidget.widgetprovider.common.AbstractGenericPoolWidgetProvider;

public class SetThresholdActivity extends AppCompatActivity {

	private EditText thresholdEditText;
	private Button saveButton;

	private View rootView;

	private int widgetId;
	private int poolId;
	private String coinType;

	public static Intent createIntent(Context context, int poolId, String coinType) {
		Intent intent = new Intent(context, SetThresholdActivity.class);
		intent.putExtra(Constant.EXTRA_POOL_ID, poolId);
		intent.putExtra(Constant.EXTRA_COIN_TYPE, coinType);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_threshold);

		extractExtra();

		bindView();

		saveButton.setOnClickListener(new OnSaveButtonClickListener());
	}

	private void extractExtra() {
		Intent intent = getIntent();
		poolId = getIntent().getIntExtra(Constant.EXTRA_POOL_ID, -1);
		coinType = getIntent().getStringExtra(Constant.EXTRA_COIN_TYPE);
		widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

		//		if (poolId == -1 || coinType == null) {
		//			throw new IllegalStateException("poolId or coinType is not init");
		//		}
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		rootView = parent;
		return super.onCreateView(parent, name, context, attrs);
	}

	private void bindView() {
		thresholdEditText = (EditText) findViewById(R.id.thresholdEditText);
		saveButton = (Button) findViewById(R.id.saveButton);
	}

	private class OnSaveButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				double threshold = Double.parseDouble(thresholdEditText.getText().toString());
				broadcastSetThreshold(threshold);
				broadcastUpdateWidget();

				if (threshold >= 0) {
					Toast.makeText(SetThresholdActivity.this, "Threshold saved", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(SetThresholdActivity.this, "Threshold disabled", Toast.LENGTH_SHORT).show();
				}

				setResult();
			} catch (NumberFormatException e) {
				Snackbar.make(rootView, "Error: Invalid number", Snackbar.LENGTH_LONG).show();
			}
		}
	}

	private void broadcastUpdateWidget() {
		Intent intent = new Intent(getApplicationContext(), AbstractGenericPoolWidgetProvider.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		getApplicationContext().sendBroadcast(intent);
	}

	private void broadcastSetThreshold(double threshold) {
		Intent intent = new Intent(getApplicationContext(), AbstractBxPoolMonitorWidgetProvider.class);
		intent.setAction(AbstractBxPoolMonitorWidgetProvider.ACTION_SET_THRESHOLD);
		intent.putExtra(Constant.EXTRA_THRESHOLD, threshold);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		intent.putExtra(Constant.EXTRA_WIDGET_ID, widgetId);
		getApplicationContext().sendBroadcast(intent);
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
