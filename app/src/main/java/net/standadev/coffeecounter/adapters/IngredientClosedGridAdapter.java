package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class IngredientClosedGridAdapter extends IngredientTotalGridAdapter{

    public IngredientClosedGridAdapter(Context context, ArrayList<IngredientCounter> ingredientCounters) {
        super(context, ingredientCounters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_closed_ingredient, null);
        }

        TextView tvIngredientName = convertView.findViewById(R.id.tvIngredientName);
        TextView tvIngredientTypeName = convertView.findViewById(R.id.tvIngredientTypeName);
        TextView tvFrom = convertView.findViewById(R.id.tvFrom);

        //UserCounter userC = new User(0, "name");
        IngredientCounter ic = (IngredientCounter) getItem(position);
        Ingredient i = ic.getIngredient();
        tvIngredientTypeName.setText("Type: " + i.getIngredientType().getName());
        tvIngredientName.setText("Name: " + i.getName());
        tvFrom.setText("From : " + i.getDateFrom() );

        return convertView;
    }




}
