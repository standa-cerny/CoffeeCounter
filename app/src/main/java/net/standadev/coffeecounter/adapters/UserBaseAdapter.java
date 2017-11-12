package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.UserCounter;

import java.util.ArrayList;

/**
 * Created by ces9cj on 6.11.2017.
 */

public abstract class UserBaseAdapter extends BaseAdapter {
    protected Context context;
    protected ArrayList<UserCounter> userCounters;
    protected static LayoutInflater inflater = null;

    protected UserBaseAdapter(Context context, ArrayList<UserCounter> userCounters) {
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
        return userCounters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((UserCounter) getItem(position)).getUser().getId();
    }
}
