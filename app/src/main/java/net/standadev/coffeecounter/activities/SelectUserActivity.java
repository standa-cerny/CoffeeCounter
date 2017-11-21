package net.standadev.coffeecounter.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.adapters.UserGridAdapter;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;

import java.util.ArrayList;



public class SelectUserActivity extends AppCompatActivity {
    GridView userGrid;
    UserGridAdapter userGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        Counter counter = Counter.getInstance(SelectUserActivity.this);
        userGridAdapter = new UserGridAdapter(SelectUserActivity.this, counter.getListOfUserCounter());

        userGrid = (GridView) findViewById(R.id.gvUsers);
        userGrid.setAdapter(userGridAdapter);
        userGrid.setOnItemClickListener(userGridAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userGridAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = SelectUserActivity.this;
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_add_user:

                intent = new Intent(context, ChangeUserActivity.class);
                intent.putExtra(Counter.USER_ID, 0);
                context.startActivity(intent);
                return true;
            case R.id.menu_select_statement:
                intent = new Intent(context, SelectStatementActivity.class);
                context.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
