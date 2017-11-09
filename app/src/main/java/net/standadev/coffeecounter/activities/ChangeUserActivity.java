package net.standadev.coffeecounter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

public class ChangeUserActivity extends AppCompatActivity {

    // TODO Move assigning user id to the DB level
    static private long userId = 10;

    private User user;
    EditText etUserName;
    CheckBox cbUserIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        // Get user Id from intent
        Intent intent = getIntent();
        long user_id = intent.getLongExtra(Counter.USER_ID, 0);

        // Prepare screen objects
        etUserName = (EditText) findViewById(R.id.etUserName);
        cbUserIsActive = (CheckBox) findViewById(R.id.cbUserIsActive);

        // Initialize screen objects
        if (user_id > 0) {
            UserCounter uc = Counter.getInstance().getUserCounterFromId(user_id);
            user = uc.getUser();

            etUserName.setText(user.getName());
            cbUserIsActive.setChecked(user.isActive());
        } else {
            userId++;
            user = new User(userId, "");

            cbUserIsActive.setChecked(true);
        }

    }

    public void OnSaveButtonClick(View view) {

        // TODO set new values
        user.setName(etUserName.getText().toString());
        user.setActive(cbUserIsActive.isChecked());

        Counter.getInstance().saveUser(user);
        this.finish();
    }
}
