package net.standadev.coffeecounter.data.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static net.standadev.coffeecounter.data.db.CounterDb.*;

/**
 * Created by Standa on 13.11.2017.
 */

public class CounterDbHelper extends SQLiteOpenHelper {

    public CounterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE " + Users.TABLE_NAME + " ("
               + Users._ID           + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + Users.COL_NAME      + " TEXT,"
               + Users.COL_ACTIVE    + " NUMERIC)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + ITypes.TABLE_NAME + " ("
                + ITypes._ID         + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITypes.COL_NAME    + " TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + IList.TABLE_NAME + " ("
                + IList.COL_NAME     + " TEXT,"
                + IList.COL_PRICE    + " REAL,"
                + IList.COL_CURRENCY + " TEXT,"
                + IList.COL_QUANTITY + " REAL,"
                + IList.COL_BEGIN    + " NUMERIC,"
                + IList.COL_END      + " NUMERIC)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + IOrders.TABLE_NAME + " ("
                + IOrders.COL_DATETIME       + " NUMERIC,"
                + IOrders.COL_INGREDIENT_ID  + " INTEGER,"
                + IOrders.COL_USER_ID        + " INTEGER,"
                + IOrders.COL_QUANTITY       + " REAL)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + IUsers.TABLE_NAME + " ("
                + IUsers.COL_INGREDIENT_ID   + " INTEGER,"
                + IUsers.COL_USER_ID         + " INTEGER,"
                + IUsers.COL_QUANTITY        + " REAL,"
                + IUsers.COL_PRICE           + " REAL,"
                + IUsers.COL_CLEARED         + " NUMERIC)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;

        sql = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + ITypes.TABLE_NAME;
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + IList.TABLE_NAME;
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + IUsers.TABLE_NAME;
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + IOrders.TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
