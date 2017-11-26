package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

        TextView tvIngredientName = convertView.findViewById(R.id.tvIngredientName);
        TextView tvIngredientTypeName = convertView.findViewById(R.id.tvIngredientTypeName);
        TextView tvQuantity = convertView.findViewById(R.id.tvUserName);

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
        c.saveIngredientOrder( new IngredientOrder(ic.getIngredient(),  userCounter.getUser(), 1.0f));

        this.notifyDataSetChanged();
    }

}
