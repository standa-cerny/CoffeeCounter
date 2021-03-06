package net.standadev.coffeecounter.data;

import android.content.ContentValues;


import net.standadev.coffeecounter.data.db.CounterDb;
import java.util.Date;



/**
 * Created by Standa on 25.11.2017.
 */

public class IngredientOrder extends BaseId {
    private long ingredientId;
    private long userId;
    private float quantity;

    public IngredientOrder(long ingredientId, long userId,  float quantity) {
        super(0, "");


        this.ingredientId = ingredientId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public IngredientOrder(Ingredient ingredient, User user, Float quantity) {
        this(ingredient.getId(), user.getId(), quantity);
    }

    public IngredientOrder(Ingredient ingredient, User user) {
        this(ingredient.getId(), user.getId(), 1.0f);
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();

        values.put(CounterDb.IOrders.COL_USER_ID, getUserId());
        values.put(CounterDb.IOrders.COL_INGREDIENT_ID, getIngredientId());
        values.put(CounterDb.IOrders.COL_QUANTITY, getQuantity());

        // set the format to sql date time

        values.put(CounterDb.IOrders.COL_DATETIME, CounterDb.getDateTime(new Date()) );

        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.IOrders.TABLE_NAME;
    }

    public long getUserId() {
        return userId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public float getQuantity() {
        return quantity;
    }
}



