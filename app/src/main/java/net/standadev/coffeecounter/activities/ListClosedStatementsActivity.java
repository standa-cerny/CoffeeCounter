package net.standadev.coffeecounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientClosedGridAdapter;
import net.standadev.coffeecounter.adapters.IngredientTotalGridAdapter;
import net.standadev.coffeecounter.data.Counter;
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
        ListView lvIngredients;

        ArrayList<IngredientCounter> ic = new ArrayList<IngredientCounter>();
        ingredientGridAdapter = new IngredientClosedGridAdapter(ListClosedStatementsActivity.this, counter.getListOfClosedIngredientCounter());

        lvIngredients = (ListView) findViewById(R.id.lvClosedIngredients);
        lvIngredients.setAdapter(ingredientGridAdapter);

        // Set on click adapter
        lvIngredients.setOnItemClickListener(ingredientGridAdapter);
    }
}
