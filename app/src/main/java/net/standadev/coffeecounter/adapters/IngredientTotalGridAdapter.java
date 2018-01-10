package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.activities.StatementActivity;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class IngredientTotalGridAdapter extends IngredientBaseAdapter implements AdapterView.OnItemClickListener {

    public IngredientTotalGridAdapter(Context context, ArrayList<IngredientCounter> ingredientCounters) {
        super(context, ingredientCounters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ingredient, null);
        }

        // Prepare format strings
        Resources res = context.getResources();

        TextView tvIngredientName = convertView.findViewById(R.id.tvIngredientName);
        TextView tvIngredientTypeName = convertView.findViewById(R.id.tvIngredientTypeName);
        TextView tvQuantity = convertView.findViewById(R.id.tvCount);

        //UserCounter userC = new User(0, "name");
        IngredientCounter ic = (IngredientCounter) getItem(position);
        Ingredient i = ic.getIngredient();
        //tvIngredientTypeName.setText("Type: " + i.getIngredientType().getName());
        //tvIngredientName.setText("Name: " + i.getName());
        //tvQuantity.setText("Quantity: " + ic.getQuantity() );

        // Set ingredient names
        tvIngredientTypeName.setText(i.getIngredientType().getName());
        tvIngredientName.setText(i.getName());

        // Set ingredient count
        try {
            tvQuantity.setText(res.getString(R.string.format_countx, ic.getQuantity()));
        } catch (Exception e) {
        }








        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IngredientCounter ic = (IngredientCounter) getItem(position);

        Context context = view.getContext();
        Counter c = Counter.getInstance();

        Intent intent = new Intent(context, StatementActivity.class);
        intent.putExtra(Counter.INGREDIENT_ID, ((IngredientCounter) getItem(position)).getIngredient().getId());
        context.startActivity(intent);

    }

}
