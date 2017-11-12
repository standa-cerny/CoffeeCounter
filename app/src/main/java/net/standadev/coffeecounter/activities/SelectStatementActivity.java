package net.standadev.coffeecounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientBaseAdapter;
import net.standadev.coffeecounter.adapters.IngredientTotalGridAdapter;
import net.standadev.coffeecounter.adapters.IngredientUserGridAdapter;
import net.standadev.coffeecounter.data.Counter;

public class SelectStatementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_statement);

        // Ingredients grid view
        Counter counter = Counter.getInstance();
        GridView gvIngredients;
        IngredientTotalGridAdapter ingredientGridAdapter;
        ingredientGridAdapter = new IngredientTotalGridAdapter(SelectStatementActivity.this, counter.getListOfIngredientCounter());

        gvIngredients = (GridView) findViewById(R.id.gvIngredients);
        gvIngredients.setAdapter(ingredientGridAdapter);

        // TODO Split ingredientGridAdapter to user implement different OnItemClick listener
        gvIngredients.setOnItemClickListener(ingredientGridAdapter);

    }
}
