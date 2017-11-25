package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

/**
 * Created by Standa on 05.11.2017.
 */

public class User extends BaseId {

    private boolean isActive = true;

    public User(long id, String name) {
        super(id, name);
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(CounterDb.Users.COL_NAME, this.getName());
        values.put(CounterDb.Users.COL_ACTIVE, this.isActive());

        return values;
    }

    @Override
    public String getTableName() {
        return CounterDb.Users.TABLE_NAME;
    }


}
