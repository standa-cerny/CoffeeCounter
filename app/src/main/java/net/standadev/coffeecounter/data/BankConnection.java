package net.standadev.coffeecounter.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.standadev.coffeecounter.R;

import static android.provider.Settings.Global.getString;

/**
 * Created by Standa on 06.01.2018.
 */

public class BankConnection {
    private SharedPreferences sharedPref;
    private String currency;

    private String currencyKey;

    public BankConnection(Context context){

        // Read Share Preferences keys
        this.currencyKey = context.getResources().getString(R.string.key_currency);

        // Get value of currency
        this.sharedPref = context.getSharedPreferences("", Context.MODE_PRIVATE);
        String defaultCurrency = context.getResources().getString(R.string.default_currency);
        this.currency = sharedPref.getString(currencyKey, defaultCurrency);
    }

    public String getCurrency(){
        return this.currency;
    }

    public void setCurrency(String newCurrency){
        // Update object attribute
        this.currency = newCurrency;

        // Save to preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(currencyKey, newCurrency);
        editor.commit();
    }

}
