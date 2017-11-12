package net.standadev.coffeecounter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientUserGridAdapter;
import net.standadev.coffeecounter.adapters.UserStatementGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.UserCounter;

import java.util.ArrayList;

public class StatementActivity extends AppCompatActivity {

    private IngredientCounter ingredientCounter;
    private ArrayList<UserCounter> userCounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        Counter counter = Counter.getInstance();

        // Prepare ingredient data
        Intent intent = getIntent();
        long ingredient_id = intent.getLongExtra(Counter.INGREDIENT_ID, 0);
        ingredientCounter = counter.getIngredientCounterFromId(ingredient_id);
        userCounters = counter.getListOfUserCounter();
        Ingredient i = ingredientCounter.getIngredient();

        // Load statement header
        TextView tvIngredientTypeName = (TextView) findViewById(R.id.tvIngredientTypeName);
        tvIngredientTypeName.setText(i.getIngredientType().getName());

        TextView tvIngredientName = (TextView) findViewById(R.id.tvIngredientName);
        tvIngredientName.setText(i.getName());

        TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText("Total price: " + ingredientCounter.getPrice());

        TextView tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvQuantity.setText("Quantity: " + ingredientCounter.getQuantity());

        TextView tvUnitPrice = (TextView) findViewById(R.id.tvUnitPrice);
        tvUnitPrice.setText("Unit price: " + ingredientCounter.getUnitPrice());


        // Build list view of statement items
        ListView lvUserStatements;
        UserStatementGridAdapter userStatementGridAdapter;
        userStatementGridAdapter = new UserStatementGridAdapter(StatementActivity.this, userCounters, ingredientCounter);

        lvUserStatements = (ListView) findViewById(R.id.lvUserStatement);
        lvUserStatements.setAdapter(userStatementGridAdapter);

    }

    public void onCloseStatementClick(View view) {
        Counter.getInstance().closeStatement(ingredientCounter.getIngredient());
    }
}
