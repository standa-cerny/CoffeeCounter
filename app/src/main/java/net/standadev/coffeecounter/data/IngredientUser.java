package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

import java.util.Date;


/**
 * Created by Standa on 25.11.2017.
 */

public class IngredientUser extends BaseId {
    private long ingredientId;
    private long userId;
    private float quantity;
    private float price;
    private boolean cleared;

    public IngredientUser(long ingredientId, long userId ) {
        super(0, "");

        this.ingredientId = ingredientId;
        this.userId = userId;
        setQuantity(0.0f);
        setPrice(0.0f);
        setCleared(false);
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();

        values.put(CounterDb.IUsers.COL_USER_ID, getUserId());
        values.put(CounterDb.IUsers.COL_INGREDIENT_ID, getIngredientId());
        values.put(CounterDb.IUsers.COL_QUANTITY, getQuantity());
        values.put(CounterDb.IUsers.COL_PRICE, getPrice());
        values.put(CounterDb.IUsers.COL_CLEARED, isCleared());

        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.IUsers.TABLE_NAME;
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

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = (float) Math.ceil(price);
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }


}



