package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.IngredientOrder;
import net.standadev.coffeecounter.data.UserCounter;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class IngredientUserGridAdapter extends IngredientBaseAdapter implements AdapterView.OnItemClickListener {
    protected UserCounter userCounter;

    public IngredientUserGridAdapter(Context context, UserCounter userCounter) {
        super(context, userCounter.getIngredientCounters());
        this.userCounter = userCounter;
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
        // Click effect
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(500);
        view.startAnimation(animation1);


        // Save ingredient order
        IngredientCounter ic = (IngredientCounter) getItem(position);
        Counter c = Counter.getInstance();
        c.saveIngredientOrder(new IngredientOrder(ic.getIngredient(), userCounter.getUser()));

        this.notifyDataSetChanged();
    }

}
