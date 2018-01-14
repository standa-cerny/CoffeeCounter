package net.standadev.coffeecounter.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
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
import net.standadev.coffeecounter.data.IngredientUser;
import net.standadev.coffeecounter.data.Statement;
import net.standadev.coffeecounter.data.StatementItem;
import net.standadev.coffeecounter.data.User;

public class StatementActivity extends AppCompatActivity {

    //private IngredientCounter ingredientCounter;
    //private ArrayList<UserCounter> userCounters;
    private Context context;
    long ingredientId;
    private Statement statement;
    private UserStatementGridAdapter userStatementGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        context = StatementActivity.this;

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
        if (statement.isClosed()) {
            btnCloseStatement.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Counter counter = Counter.getInstance();
        statement = counter.getStatement(ingredientId);

        loadHeader();
        userStatementGridAdapter.update(statement.getItems());
        userStatementGridAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!statement.isClosed()) {
            getMenuInflater().inflate(R.menu.menu_statement, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

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
        Resources res = context.getResources();
        String title = res.getString(R.string.statement_close_title);
        String message = res.getString(R.string.statement_close_message);

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the statement
                        Counter.getInstance().closeStatement(statement);
                        StatementActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void loadHeader() {
        IngredientCounter ic = statement.getIngredientCounter();
        Ingredient i = ic.getIngredient();

        TextView tvIngredientTypeName = (TextView) findViewById(R.id.tvIngredientTypeName);
        TextView tvIngredientName = (TextView) findViewById(R.id.tvIngredientName);
        TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        TextView tvQuantity = (TextView) findViewById(R.id.tvCount);
        TextView tvUnitPrice = (TextView) findViewById(R.id.tvUnitPrice);
        TextView tvDataFrom = (TextView) findViewById(R.id.tvBegin);

        // Prepare format strings
        Resources res = context.getResources();
        String currency = Counter.getInstance().getBankConnection().getCurrency();

        // Set values
        tvIngredientTypeName.setText(i.getIngredientType().getName());
        tvIngredientName.setText(i.getName());
        try {

            tvTotalPrice.setText(res.getString(R.string.format_price, ic.getIngredient().getPrice(), currency));
            tvQuantity.setText(res.getString(R.string.format_count, ic.getQuantity()));
            tvUnitPrice.setText(res.getString(R.string.format_price, ic.getUnitPrice(), currency));

            tvDataFrom.setText(res.getString(R.string.format_period, ic.getIngredient().getDateFrom(), ic.getIngredient().getDateTo()));

        } catch (Exception e) {
        }

    }
}
