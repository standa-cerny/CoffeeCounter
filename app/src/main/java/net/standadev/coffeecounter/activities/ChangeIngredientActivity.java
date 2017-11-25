package net.standadev.coffeecounter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.IngredientType;

import java.util.Locale;

public class ChangeIngredientActivity extends AppCompatActivity {

    private Ingredient ingredient;
    EditText etIngredientType;
    EditText etIngredientName;
    EditText etIngredientPrice;
    EditText etIngredientCurrency;
    EditText etIngredientQuantity;
    EditText etIngredientUnit;
    ImageButton btnUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ingredient);

        // Get user Id from intent
        Intent intent = getIntent();
        long ingredient_id = intent.getLongExtra(Counter.INGREDIENT_ID, 0);

        // Prepare screen objects
        etIngredientType = (EditText) findViewById(R.id.etIngredientType);
        etIngredientName = (EditText) findViewById(R.id.etIngredientName);
        etIngredientPrice = (EditText) findViewById(R.id.etIngredientPrice);
        etIngredientCurrency = (EditText) findViewById(R.id.etIngredientCurrency);
        etIngredientQuantity = (EditText) findViewById(R.id.etIngredientQuantity);
        etIngredientUnit = (EditText) findViewById(R.id.etIngredientUnit);
        btnUnlock = (ImageButton) findViewById(R.id.btnUnlock);

        // Initialize screen objects
        if (ingredient_id > 0) {
            IngredientCounter ic = Counter.getInstance().getIngredientCounterFromId(ingredient_id);
            ingredient = ic.getIngredient();


        } else {
            ingredient = new Ingredient(0, "", new IngredientType(0, ""));
        }

        // Load input fields
        etIngredientType.setText( ingredient.getIngredientType().getName() );
        etIngredientName.setText( ingredient.getName() );
        etIngredientPrice.setText( String.format(Locale.getDefault(), "%f", ingredient.getPrice()) );
        etIngredientCurrency.setText( ingredient.getCurrency());
        etIngredientQuantity.setText( String.format(Locale.getDefault(),"%f", ingredient.getQuantity())  );
        etIngredientUnit.setText( ingredient.getUnit() );
    }

    public void onSaveButtonClick(View view) {

        ingredient.getIngredientType().setName(etIngredientType.getText().toString());
        ingredient.setName(etIngredientName.getText().toString());
        ingredient.setPrice(Float.valueOf(etIngredientPrice.getText().toString()));
        ingredient.setQuantity(Float.valueOf(etIngredientQuantity.getText().toString()));

        try {
            Counter.getInstance().saveIngredient(ingredient);
        }catch(Exception e) {
            Log.d("TAG", "msg", e);
        }
        this.finish();
    }

    public void onUnlockButtonClick(View view){
        etIngredientType.setEnabled(true);
        btnUnlock.setVisibility(View.GONE);
    }

}
