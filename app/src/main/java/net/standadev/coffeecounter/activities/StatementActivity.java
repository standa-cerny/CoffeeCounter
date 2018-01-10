package net.standadev.coffeecounter.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.UserStatementGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.Statement;

public class StatementActivity extends AppCompatActivity {

    //private IngredientCounter ingredientCounter;
    //private ArrayList<UserCounter> userCounters;
    long ingredientId;
    private Statement statement;
    private UserStatementGridAdapter userStatementGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        Counter counter = Counter.getInstance();

        // Prepare ingredient data
        Intent intent = getIntent();
        ingredientId = intent.getLongExtra(Counter.INGREDIENT_ID, 0);
        //ingredientCounter = counter.getIngredientCounterFromId(ingredient_id);
        //userCounters = counter.getListOfUserCounter();
        statement = counter.getStatement(ingredientId);


        // Load statement header
        loadHeader();


        // Build list view of statement items
        ListView lvUserStatements;
        userStatementGridAdapter = new UserStatementGridAdapter(StatementActivity.this, statement.getItems());

        lvUserStatements = (ListView) findViewById(R.id.lvUserStatement);
        lvUserStatements.setAdapter(userStatementGridAdapter);

        Button btnCloseStatement = (Button) findViewById(R.id.btnCloseStatement);
        if (statement.isClosed()){
            btnCloseStatement.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadHeader();
        userStatementGridAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (! statement.isClosed()) {
            getMenuInflater().inflate(R.menu.menu_statement, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = StatementActivity.this;
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_change_ingredient:

                intent = new Intent(context, ChangeIngredientActivity.class);
                intent.putExtra(Counter.INGREDIENT_ID, ingredientId);
                context.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onCloseStatementClick(View view) {
        Counter.getInstance().closeStatement(statement);
        this.finish();
    }

    private void loadHeader(){
        IngredientCounter ic = statement.getIngredientCounter();
        Ingredient i = ic.getIngredient();

        TextView tvIngredientTypeName = (TextView) findViewById(R.id.tvIngredientTypeName);
        tvIngredientTypeName.setText(i.getIngredientType().getName());

        TextView tvIngredientName = (TextView) findViewById(R.id.tvIngredientName);
        tvIngredientName.setText(i.getName());

        TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText("Total price: " + ic.getIngredient().getPrice());

        TextView tvQuantity = (TextView) findViewById(R.id.tvCount);
        tvQuantity.setText("Quantity: " + ic.getQuantity());

        TextView tvUnitPrice = (TextView) findViewById(R.id.tvUnitPrice);
        tvUnitPrice.setText("Unit price: " + ic.getUnitPrice());

        TextView tvBegin = (TextView) findViewById(R.id.tvBegin);
        tvBegin.setText("Begin: " + ic.getIngredient().getDateFrom());
    }
}
