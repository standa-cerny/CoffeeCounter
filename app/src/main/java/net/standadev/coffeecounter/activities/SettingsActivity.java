package net.standadev.coffeecounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.BankConnection;
import net.standadev.coffeecounter.data.Counter;

public class SettingsActivity extends AppCompatActivity {
    private BankConnection bankConnection;
    private EditText etCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Counter counter = Counter.getInstance(SettingsActivity.this);
        bankConnection = counter.getBankConnection();

        etCurrency = (EditText) findViewById(R.id.etCurrency);
        etCurrency.setText( bankConnection.getCurrency());
    }

    public void onSaveButtonClick(View view) {
        bankConnection.setCurrency(etCurrency.getText().toString());

        this.finish();
    }
}
