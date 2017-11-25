package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;
import java.util.Date;

/**
 * Created by Standa on 05.11.2017.
 */

public class Ingredient extends BaseId{

    private IngredientType ingredientType;
    private String description;

    private float price;
    private String currency;
    private float quantity;
    private String unit;

    private Date begin;
    private Date end;

    private boolean closed;

    private int counterIndex = -1;  // Index to array list of counters

    public Ingredient(long id, String name, IngredientType ingredientType) {
        super(id, name);
        this.ingredientType = ingredientType;

        setPrice(0.0f);
        setCurrency("CZK");

        setQuantity(0.0f);
        setUnit("Kg");

        setBegin(new Date());
        setEnd(new Date());
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.IList.COL_NAME, this.getName());
        values.put(CounterDb.IList.COL_TYPE_ID, this.getIngredientType().getId());
        values.put(CounterDb.IList.COL_PRICE, this.getPrice());

        values.put(CounterDb.IList.COL_PRICE, this.getPrice());
        values.put(CounterDb.IList.COL_CURRENCY, this.getCurrency());
        values.put(CounterDb.IList.COL_QUANTITY, this.getQuantity());
        values.put(CounterDb.IList.COL_UNIT, this.getUnit());
        values.put(CounterDb.IList.COL_BEGIN, CounterDb.getDateTime(this.getBegin()));
        values.put(CounterDb.IList.COL_END, CounterDb.getDateTime(this.getEnd()));
        values.put(CounterDb.IList.COL_CLOSED, this.isClosed());

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        if (!isClosed()){
            return new Date();
        }
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        setEnd(new Date());
    }

    public int getCounterIndex() {
        return counterIndex;
    }

    void setCounterIndex(int counterIndex) {
        this.counterIndex = counterIndex;
    }

}
