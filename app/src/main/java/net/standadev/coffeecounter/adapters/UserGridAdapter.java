package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.activities.SelectIngredientActivity;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

import java.util.ArrayList;

/**
 * Created by Standa on 05.11.2017.
 */

public class UserGridAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private ArrayList<UserCounter> userCounters;
    private static LayoutInflater inflater = null;

    public UserGridAdapter(Context context, ArrayList<UserCounter> userCounters) {
        this.context = context;
        this.userCounters = userCounters;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userCounters.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user, null);
        }

        TextView userNameTextView = (TextView) convertView.findViewById(R.id.tvUserName);

        //UserCounter userC = new User(0, "name");
        UserCounter uc = userCounters.get(position);
        userNameTextView.setText("Name: " + uc.getUser().getName());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        UserCounter uc = userCounters.get(position);

        Context context = view.getContext();

        Intent intent = new Intent(context, SelectIngredientActivity.class);
        intent.putExtra(Counter.USER_ID, uc.getUser().getId());
        context.startActivity(intent);

    }

}
