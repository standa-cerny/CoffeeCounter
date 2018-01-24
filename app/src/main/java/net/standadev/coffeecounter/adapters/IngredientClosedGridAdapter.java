package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class IngredientClosedGridAdapter extends IngredientTotalGridAdapter {

    public IngredientClosedGridAdapter(Context context, ArrayList<IngredientCounter> ingredientCounters) {
        super(context, ingredientCounters);
    }

    @Override
    public int getCount() {
        return ingredientCounters.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position > 0) {
            return ingredientCounters.get(position - 1);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_closed_statement, null);
        }

        // Prepare format strings
        Resources res = context.getResources();
        String currency = Counter.getInstance().getBankConnection().getCurrency();


        TextView tvIngredientTypeName = convertView.findViewById(R.id.tvIngredientTypeName);
        TextView tvIngredientName = convertView.findViewById(R.id.tvIngredientName);
        TextView tvPeriod = convertView.findViewById(R.id.tvPeriod);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        //Set User name
        IngredientCounter item = (IngredientCounter) getItem(position);
        if (item == null) {
            // The header row

            // Set style
            int headerStyle = R.style.TextAppearance_AppCompat_Medium;
            tvIngredientTypeName.setTextAppearance(context, headerStyle);
            tvIngredientName.setTextAppearance(context, headerStyle);
            tvPeriod.setTextAppearance(context, headerStyle);
            tvPrice.setTextAppearance(context, headerStyle);

            // Set value
            tvIngredientTypeName.setText(res.getString(R.string.ingredient_type));
            tvIngredientName.setText(res.getString(R.string.ingredient_name));
            tvPeriod.setText(res.getString(R.string.statement_period));
            tvPrice.setText(res.getString(R.string.statement_price));
        } else {
            // Data row
            Ingredient i = item.getIngredient();
            tvIngredientTypeName.setText(i.getIngredientType().getName());
            tvIngredientName.setText(i.getName());
            try {
                tvPeriod.setText(res.getString(R.string.format_period, i.getDateFrom(), i.getDateTo()));
                tvPrice.setText(res.getString(R.string.format_price, i.getPrice(), currency));
            } catch (Exception e) {
            }

        }


        return convertView;
    }


}
