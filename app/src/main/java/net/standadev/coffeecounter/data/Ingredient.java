package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

/**
 * Created by Standa on 05.11.2017.
 */

public class Ingredient extends BaseId{

    private IngredientType ingredientType;
    private String description;

    private Float price;

    private int counterIndex = -1;  // Index to array list of counters

    public Ingredient(long id, String name, IngredientType ingredientType) {
        super(id, name);
        this.ingredientType = ingredientType;
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.IList.COL_NAME, this.getName());
        values.put(CounterDb.IList.COL_TYPE_ID, this.getIngredientType().getId());
        values.put(CounterDb.IList.COL_PRICE, this.getPrice());

        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.IList.TABLE_NAME;
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCounterIndex() {
        return counterIndex;
    }

    void setCounterIndex(int counterIndex) {
        this.counterIndex = counterIndex;
    }

}
