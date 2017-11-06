package net.standadev.coffeecounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Counter counter = Counter.getInstance();
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
}
