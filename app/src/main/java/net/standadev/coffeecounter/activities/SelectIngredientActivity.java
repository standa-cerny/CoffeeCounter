package net.standadev.coffeecounter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientUserGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

public class SelectIngredientActivity extends AppCompatActivity {

    private Context context;

    private UserCounter userCounter;
    private User user;

    private Button btnClearDebt;
    private TextView tvUserName;

    private IngredientUserGridAdapter ingredientGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient);

        context = SelectIngredientActivity.this;

        Intent intent = getIntent();
        long user_id = intent.getLongExtra(Counter.USER_ID, 0);
        userCounter = Counter.getInstance().getUserCounterFromId(user_id);
        user = userCounter.getUser();

        // Header
        tvUserName = (TextView) findViewById(R.id.tvCount);
        btnClearDebt = (Button)findViewById(R.id.btnClearDebt);
        loadUser();

        // Ingredients grid view
        Counter counter = Counter.getInstance();
        GridView gvIngredients;
        ingredientGridAdapter = new IngredientUserGridAdapter(SelectIngredientActivity.this, userCounter);

        gvIngredients = (GridView) findViewById(R.id.gvIngredients);
        gvIngredients.setAdapter(ingredientGridAdapter);
        gvIngredients.setOnItemClickListener(ingredientGridAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUser();
        ingredientGridAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_ingredient, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = SelectIngredientActivity.this;
        switch (item.getItemId()) {
            case R.id.menu_change_user:

                Intent intent = new Intent(context, ChangeUserActivity.class);
                intent.putExtra(Counter.USER_ID, user.getId());
                context.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClearDebtButtonClick(View view) {
        Counter counter = Counter.getInstance();
        counter.clearDebt(userCounter);
        loadUser();
    }


    private void loadUser(){
        tvUserName.setText(user.getName());

        // Prepare format strings
        Resources res = context.getResources();
        String currency = Counter.getInstance().getBankConnection().getCurrency();

        // Display button for paying
        if (userCounter.getDebt() > 0.0f ) {
            try {
                btnClearDebt.setText(res.getString(R.string.btn_pay, userCounter.getDebt(), currency));
            }catch(Exception e){
            }

        }else{
            btnClearDebt.setVisibility(View.INVISIBLE);
        }


    }

}
