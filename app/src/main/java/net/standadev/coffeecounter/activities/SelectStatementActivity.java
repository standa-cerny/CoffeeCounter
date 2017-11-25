package net.standadev.coffeecounter.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientTotalGridAdapter;
import net.standadev.coffeecounter.data.Counter;

public class SelectStatementActivity extends AppCompatActivity {

    IngredientTotalGridAdapter ingredientGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_statement);

        // Ingredients grid view
        Counter counter = Counter.getInstance();
        GridView gvIngredients;

        ingredientGridAdapter = new IngredientTotalGridAdapter(SelectStatementActivity.this, counter.getListOfIngredientCounter());

        gvIngredients = (GridView) findViewById(R.id.gvIngredients);
        gvIngredients.setAdapter(ingredientGridAdapter);

        // TODO Split ingredientGridAdapter to user implement different OnItemClick listener
        gvIngredients.setOnItemClickListener(ingredientGridAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        ingredientGridAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_statement, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = SelectStatementActivity.this;
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_add_ingredient_type:

                intent = new Intent(context, ChangeIngredientActivity.class);
                intent.putExtra(Counter.INGREDIENT_ID, 0);
                context.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
