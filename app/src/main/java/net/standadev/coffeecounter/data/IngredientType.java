package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

/**
 * Created by Standa on 05.11.2017.
 */

public class IngredientType extends BaseId{
    private boolean isActive = true;
    private String unit;

    public IngredientType(long id, String name) {
        super(id, name);
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.ITypes.COL_NAME, this.getName());
        values.put(CounterDb.ITypes.COL_ACTIVE, this.isActive());
        values.put(CounterDb.ITypes.COL_UNIT, this.getUnit());
        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.ITypes.TABLE_NAME;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
