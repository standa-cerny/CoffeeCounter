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
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class IngredientGridAdapter extends IngredientBaseAdapter implements AdapterView.OnItemClickListener {
    protected User user;

    public IngredientGridAdapter(Context context, ArrayList<IngredientCounter> ingredientCounters, User user) {
        super(context, ingredientCounters);
        this.user = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ingredient, null);
        }

        TextView tvIngredientName = (TextView) convertView.findViewById(R.id.tvIngredientName);
        TextView tvIngredientTypeName = (TextView) convertView.findViewById(R.id.tvIngredientTypeName);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);

        //UserCounter userC = new User(0, "name");
        IngredientCounter ic = (IngredientCounter) getItem(position);
        Ingredient i = ic.getIngredient();
        tvIngredientTypeName.setText("Type: " + i.getIngredientType().getName());
        tvIngredientName.setText("Name: " + i.getName());
        tvQuantity.setText("Quantity: " + ic.getQuantity() );

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IngredientCounter ic = (IngredientCounter) getItem(position);

        Context context = view.getContext();

        Counter c = Counter.getInstance();
        c.orderIngredient(user, ic.getIngredient(), 1.0f);

        this.notifyDataSetChanged();
    }

}
