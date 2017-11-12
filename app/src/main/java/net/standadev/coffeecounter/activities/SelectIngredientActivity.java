package net.standadev.coffeecounter.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.IngredientUserGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

public class SelectIngredientActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient);

        Intent intent = getIntent();
        long user_id = intent.getLongExtra(Counter.USER_ID, 0);
        UserCounter uc = Counter.getInstance().getUserCounterFromId(user_id);
        user = uc.getUser();

        // User text
        loadUser();

        // Ingredients grid view
        Counter counter = Counter.getInstance();
        GridView gvIngredients;
        IngredientUserGridAdapter ingredientGridAdapter;
        ingredientGridAdapter = new IngredientUserGridAdapter(SelectIngredientActivity.this, uc.getIngredientCounters(), user);

        gvIngredients = (GridView) findViewById(R.id.gvIngredients);
        gvIngredients.setAdapter(ingredientGridAdapter);
        gvIngredients.setOnItemClickListener(ingredientGridAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUser();
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


    private void loadUser(){
        String message = "user id: " + user.getId() + " " + user.getName();
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.tvQuantity);
        textView.setText(message);
    }

}
