package net.standadev.coffeecounter.data;

import android.content.ContentValues;

import net.standadev.coffeecounter.data.db.CounterDb;

/**
 * Created by Standa on 05.11.2017.
 */

public abstract class BaseId {

    protected long id;
    protected String name;

    public BaseId(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    abstract public ContentValues getValues();

    abstract public String getTableName();



}
