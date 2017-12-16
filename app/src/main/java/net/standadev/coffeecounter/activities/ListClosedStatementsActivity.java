package net.standadev.coffeecounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientClosedGridAdapter;
import net.standadev.coffeecounter.adapters.IngredientTotalGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;

import java.util.ArrayList;

public class ListClosedStatementsActivity extends AppCompatActivity {

    IngredientTotalGridAdapter ingredientGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_closed_statements);

        // Ingredients grid view
        Counter counter = Counter.getInstance();
        GridView gvIngredients;

        ArrayList<IngredientCounter> ic = new ArrayList<IngredientCounter>();
        ingredientGridAdapter = new IngredientClosedGridAdapter(ListClosedStatementsActivity.this, counter.getListOfClosedIngredientCounter());

        gvIngredients = (GridView) findViewById(R.id.gvClosedIngredients);
        gvIngredients.setAdapter(ingredientGridAdapter);

        // Set on click adapter
        gvIngredients.setOnItemClickListener(ingredientGridAdapter);
    }
}
