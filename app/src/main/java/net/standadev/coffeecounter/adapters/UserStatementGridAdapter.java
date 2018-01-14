package net.standadev.coffeecounter.adapters;

import android.content.Context;
import android.content.res.Resources;
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
import net.standadev.coffeecounter.data.UserCounter;

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

    public void update(ArrayList<StatementItem> items){
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position > 0) {
            return items.get(position - 1);
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
            convertView = inflater.inflate(R.layout.item_statement, null);
        }

        // Prepare format strings
        Resources res = context.getResources();
        String currency = Counter.getInstance().getBankConnection().getCurrency();



        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvCount = convertView.findViewById(R.id.tvCount);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        //Set User name
        StatementItem item = (StatementItem) getItem(position);
        if (item == null){
            // The header row

            // Set style
            int headerStyle = R.style.TextAppearance_AppCompat_Medium;
            tvUserName.setTextAppearance(context, headerStyle);
            tvCount.setTextAppearance(context, headerStyle);
            tvPrice.setTextAppearance(context, headerStyle);

            // Set value
            tvUserName.setText(res.getString(R.string.user_name));
            tvCount.setText(res.getString(R.string.statement_count));
            tvPrice.setText(res.getString(R.string.statement_price));
        } else {
            // Data row
            User u = item.getUser();
            tvUserName.setText(u.getName());

            // Set quantity and price
            IngredientUser iu = item.getIngredientUser();

            try {
                tvCount.setText(res.getString(R.string.format_count, iu.getQuantity()));
                tvPrice.setText(res.getString(R.string.format_price, iu.getPrice(), currency));
            } catch (Exception e) {
            }
        }



        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IngredientCounter ic = (IngredientCounter) getItem(position);

        Context context = view.getContext();
        Counter c = Counter.getInstance();



    }

}
