package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.standadev.coffeecounter.R;
import net.standadev.coffeecounter.data.Counter;
import net.standadev.coffeecounter.data.IngredientCounter;
import net.standadev.coffeecounter.data.IngredientUser;
import net.standadev.coffeecounter.data.StatementItem;
import net.standadev.coffeecounter.data.User;

import java.util.ArrayList;


/**
 * Created by ces9cj on 6.11.2017.
 */

public class UserStatementGridAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    protected Context context;
    protected ArrayList<StatementItem> items;
    protected static LayoutInflater inflater = null;

    public UserStatementGridAdapter(Context context, ArrayList<StatementItem> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_statement, null);
        }

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        //Set User name
        StatementItem item = (StatementItem) getItem(position);
        User u = item.getUser();
        tvUserName.setText(u.getName());

        // Set quantity and price
        IngredientUser iu = item.getIngredientUser();
        tvQuantity.setText("Quantity: " + iu.getQuantity() );
        tvPrice.setText("Price: " + iu.getPrice() );

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IngredientCounter ic = (IngredientCounter) getItem(position);

        Context context = view.getContext();
        Counter c = Counter.getInstance();



    }

}
