package com.ongxeno.bitcoinratewidget;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ongxeno.bitcoinratewidget.common.preference.Preference;

public class MainActivity extends AppCompatActivity {

	private View rootView;
	private Button pasteSuprnovaTokenButton;
	private Button pasteCoinmineTokenButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bindView();
	}

	private void bindView() {
		rootView = findViewById(R.id.rootView);
		pasteSuprnovaTokenButton = (Button) findViewById(R.id.pasteSuprnovaTokenButton);
		pasteCoinmineTokenButton = (Button) findViewById(R.id.pasteCoinmineTokenButton);

		pasteSuprnovaTokenButton.setOnClickListener(new OnPasteTokenButtonClickListener());
		pasteCoinmineTokenButton.setOnClickListener(new OnPasteTokenButtonClickListener());
	}

	private class OnPasteTokenButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			if (clipboardManager.getPrimaryClip().getItemCount() == 1) {
				String token = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
				switch (v.getId()) {
					case R.id.pasteSuprnovaTokenButton:
						Preference.getInstance(getApplicationContext()).setSuprnovaApiToken(token);
						break;
					case R.id.pasteCoinmineTokenButton:
						Preference.getInstance(getApplicationContext()).setCoinmineApiToken(token);
						break;
					default:
						Snackbar.make(rootView, "Error: Button ID miss match.", Snackbar.LENGTH_LONG).show();
						break;
				}

				Snackbar.make(rootView, "Token Saved. You can now place widget on Home Screen.", Snackbar.LENGTH_LONG).show();
			} else {
				Snackbar.make(rootView, "Error: Multiple text in clipboard", Snackbar.LENGTH_LONG).show();
			}
		}
	}
}
