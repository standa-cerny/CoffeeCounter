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
import net.standadev.coffeecounter.data.User;
import net.standadev.coffeecounter.data.UserCounter;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class UserStatementGridAdapter extends UserBaseAdapter implements AdapterView.OnItemClickListener {

    private IngredientCounter ingredientCounterTotal;

    public UserStatementGridAdapter(Context context, ArrayList<UserCounter> userCounters, IngredientCounter ingredientCounterTotal) {
        super(context, userCounters);
        this.ingredientCounterTotal = ingredientCounterTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_statement, null);
        }

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvQuantity = convertView.findViewById(R.id.tvUserName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);


        //Set User name
        UserCounter uc = (UserCounter) getItem(position);
        User u = uc.getUser();
        tvUserName.setText(u.getName());

        Ingredient i = ingredientCounterTotal.getIngredient();

        IngredientCounter ingredientCounterUser = uc.getIngredientCounters().get(i.getCounterIndex());

        tvQuantity.setText("Quantity: " + ingredientCounterUser.getQuantity() );
        tvPrice.setText("Price: " + ingredientCounterUser.getQuantity() * ingredientCounterTotal.getUnitPrice() );

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IngredientCounter ic = (IngredientCounter) getItem(position);

        Context context = view.getContext();
        Counter c = Counter.getInstance();



    }

}
