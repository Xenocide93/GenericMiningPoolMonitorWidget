package com.ongxeno.bitcoinratewidget;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ongxeno.bitcoinratewidget.common.preference.Preference;

public class MainActivity extends AppCompatActivity {

	private View rootView;
	private Button pasteSuprnovaTokenButton;
	private Button pasteCoinmineTokenButton;
	private Button pasteDwarfWalletAddressButton;
	private EditText pasteDwarfEmailEditText;
	private Button saveDwarfEmailButton;

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
		pasteDwarfWalletAddressButton = (Button) findViewById(R.id.pasteDwarfWalletAddressButton);
		pasteDwarfEmailEditText = (EditText) findViewById(R.id.pasteDwarfEmailEditText);
		saveDwarfEmailButton = (Button) findViewById(R.id.saveDwarfEmailButton);

		pasteSuprnovaTokenButton.setOnClickListener(new OnPasteTokenButtonClickListener());
		pasteCoinmineTokenButton.setOnClickListener(new OnPasteTokenButtonClickListener());
		pasteDwarfWalletAddressButton.setOnClickListener(new OnPasteTokenButtonClickListener());
		saveDwarfEmailButton.setOnClickListener(new OnSaveDwarfEmailClickListener());
	}

	private class OnPasteTokenButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			if (clipboardManager.getPrimaryClip().getItemCount() == 1) {
				String clipboardContent = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
				switch (v.getId()) {
				case R.id.pasteSuprnovaTokenButton:
					Preference.getInstance(getApplicationContext()).setSuprnovaApiToken(clipboardContent);
					break;
				case R.id.pasteCoinmineTokenButton:
					Preference.getInstance(getApplicationContext()).setCoinmineApiToken(clipboardContent);
					break;
				case R.id.pasteDwarfWalletAddressButton:
					Preference.getInstance(getApplicationContext()).setDwarfpoolWallet(clipboardContent);
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

	private class OnSaveDwarfEmailClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Preference.getInstance(getApplicationContext()).setDwarfpoolEmail(pasteDwarfEmailEditText.getText().toString());
		}
	}
}
