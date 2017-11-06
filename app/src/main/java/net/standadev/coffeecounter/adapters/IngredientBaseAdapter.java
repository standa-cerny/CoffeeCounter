package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import net.standadev.coffeecounter.data.IngredientCounter;

import java.util.ArrayList;

/**
 * Created by ces9cj on 6.11.2017.
 */

public abstract class IngredientBaseAdapter extends BaseAdapter {
    protected Context context;
    protected ArrayList<IngredientCounter> ingredientCounters;
    protected static LayoutInflater inflater = null;

    protected IngredientBaseAdapter(Context context, ArrayList<IngredientCounter> ingredientCounters) {
        this.context = context;
        this.ingredientCounters = ingredientCounters;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingredientCounters.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientCounters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((IngredientCounter) getItem(position)).getIngredient().getId();
    }
}
