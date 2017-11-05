package net.standadev.coffeecounter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

public class SelectIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient);

        Intent intent = getIntent();
        long user_id = intent.getLongExtra(Counter.USER_ID, 0);
        UserCounter uc = Counter.getInstance().getUserCounterFromId(user_id);
        String message = "user id: " + user_id + " " + uc.getUser().getName();
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.tvUserName);
        textView.setText(message);
    }
}
