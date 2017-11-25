package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

/**
 * Created by Standa on 05.11.2017.
 */

public class IngredientType extends BaseId{

    public IngredientType(long id, String name) {
        super(id, name);
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.ITypes.COL_NAME, this.getName());

        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.ITypes.TABLE_NAME;
    }


}
