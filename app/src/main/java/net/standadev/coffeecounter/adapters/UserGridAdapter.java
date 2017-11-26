package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class UserGridAdapter extends UserBaseAdapter implements AdapterView.OnItemClickListener {
    public UserGridAdapter(Context context, ArrayList<UserCounter> userCounters) {
        super(context, userCounters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user, null);
        }

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUserDebt = (TextView) convertView.findViewById(R.id.tvUserDebt);

        // UserCounter userC = new User(0, "name");
        UserCounter uc = userCounters.get(position);
        tvUserName.setText("Name: " + uc.getUser().getName());
        tvUserDebt.setText("Debt: " + uc.getDebt());

        // Ingredients grid view
        User user = uc.getUser();
        Counter counter = Counter.getInstance();

        GridView gvIngredients;


        IngredientUserGridAdapter ingredientGridAdapter;
        ingredientGridAdapter = new IngredientUserGridAdapter(context, uc);


        gvIngredients = (GridView) convertView.findViewById(R.id.gvIngredients);

        // Prevent nested grid view to be clicked (
        gvIngredients.setClickable(false);
        gvIngredients.setFocusable(false);
        gvIngredients.setFocusableInTouchMode(false);

        gvIngredients.setEnabled(false);
        gvIngredients.setAdapter(ingredientGridAdapter);

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
