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
    private float quantity;

    private Date dateFrom;
    private Date dateTo;

    private boolean closed;

    private int counterIndex = -1;  // Index to array list of counters

    public Ingredient(long id, String name, IngredientType ingredientType) {
        super(id, name);
        this.ingredientType = ingredientType;

        setPrice(0.0f);

        setQuantity(0.0f);

        setDateFrom(new Date());
        setDateTo(new Date());
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.IList.COL_NAME, this.getName());
        values.put(CounterDb.IList.COL_TYPE_ID, this.getIngredientType().getId());
        values.put(CounterDb.IList.COL_PRICE, this.getPrice());

        values.put(CounterDb.IList.COL_PRICE, this.getPrice());
        values.put(CounterDb.IList.COL_QUANTITY, this.getQuantity());
        //values.put(CounterDb.IList.COL_UNIT, this.getUnit());
        values.put(CounterDb.IList.COL_DATE_FROM, CounterDb.getDateTime(this.getDateFrom()));
        values.put(CounterDb.IList.COL_DATE_TO, CounterDb.getDateTime(this.getDateTo()));
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        if (!isClosed()){
            return new Date();
        }
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        setDateTo(new Date());
    }

    public int getCounterIndex() {
        return counterIndex;
    }

    void setCounterIndex(int counterIndex) {
        this.counterIndex = counterIndex;
    }

}
