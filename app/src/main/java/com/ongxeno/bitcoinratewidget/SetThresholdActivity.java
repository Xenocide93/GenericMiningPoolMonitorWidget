package com.ongxeno.bitcoinratewidget;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ongxeno.bitcoinratewidget.widgetprovider.common.ThresholdSetterHandler;

public class SetThresholdActivity extends AppCompatActivity {

	private EditText thresholdEditText;
	private Button saveButton;

	private ThresholdSetterHandler thresholdSetterHandler;
	private View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_threshold);

		bindView();
		saveButton.setOnClickListener(new OnSaveButtonClickListener());
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

	public void setThresholdSetterHandler(ThresholdSetterHandler handler) {
		this.thresholdSetterHandler = handler;
	}

	private class OnSaveButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (thresholdSetterHandler != null) {
				try {
					double threshold = Double.parseDouble(thresholdEditText.getText().toString());
					thresholdSetterHandler.onSetThreshold(getApplicationContext(), threshold);
					if (threshold >= 0) {
						Toast.makeText(SetThresholdActivity.this, "Threshold saved", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SetThresholdActivity.this, "Threshold disabled", Toast.LENGTH_SHORT).show();
					}
					SetThresholdActivity.this.finish();
				} catch (NumberFormatException e) {
					Snackbar.make(rootView, "Error: Invalid number", Snackbar.LENGTH_LONG).show();
				}
			} else {
				Snackbar.make(rootView, "Error: Handler is not set.", Snackbar.LENGTH_LONG).show();
			}
		}
	}
}
