package net.standadev.coffeecounter.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import net.standadev.coffeecounter.data.BaseId;
import net.standadev.coffeecounter.data.Ingredient;
import net.standadev.coffeecounter.data.IngredientOrder;
import net.standadev.coffeecounter.data.IngredientType;
import net.standadev.coffeecounter.data.User;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Standa on 21.11.2017.
 */

public class CounterDataProvider {
    private CounterDbHelper dbHelper;

    public CounterDataProvider(Context context) {
        dbHelper = new CounterDbHelper(context);

    }

    public void insert(BaseId baseId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert new line and return primary key
        long newId = db.insert(
                baseId.getTableName(),
                null,
                baseId.getValues());
        baseId.setId(newId);
    }

    public void update(BaseId baseId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Insert new line and return primary key
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { (new Long(baseId.getId())).toString() };

        int count = db.update(
                baseId.getTableName(),
                baseId.getValues(),
                selection,
                selectionArgs);
    }


    public ArrayList<User> getListOfUsers(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.Users._ID,
                CounterDb.Users.COL_NAME,
                CounterDb.Users.COL_ACTIVE
        };

// Filter results WHERE "title" = 'My Title'
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = { "My Title" };

        // Sort order
        String sortOrder =
                CounterDb.Users._ID + " ASC";

        // Query
        Cursor cursor = db.query(
                CounterDb.Users.TABLE_NAME,            // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        // Data processing
        ArrayList<User> result = new ArrayList<User>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.Users._ID));

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.Users.COL_NAME));

            User user = new User(id, name);

            Boolean isActive = 0 != cursor.getInt(
                    cursor.getColumnIndexOrThrow(CounterDb.Users.COL_ACTIVE));
            user.setActive(isActive);

            result.add(user);
        }
        cursor.close();

        return result;
    }



    public ArrayList<Ingredient> getListOfIngredients(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.IList._ID,
                CounterDb.IList.COL_NAME,
                CounterDb.IList.COL_TYPE_ID,
                CounterDb.IList.COL_PRICE,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.IList.COL_END + " is not null";
        String[] selectionArgs = {  };

        // Sort order
        //String sortOrder =
        //        CounterDb.Users._ID + " ASC";

        // Query
        Cursor cursor = db.query(
                CounterDb.IList.TABLE_NAME,            // The table to query
                projection,                            // The columns to return
                null, //selection,                             // The columns for the WHERE clause
                null, //selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );
        // Data processing
        ArrayList<Ingredient> result = new ArrayList<Ingredient>();
        while(cursor.moveToNext()) {
            // Load basic ingredient data
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList._ID));

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_NAME));

            long type_id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_TYPE_ID));

            Ingredient ingredient = new Ingredient(id, name, getIngredientType(type_id));

            // Load price
            float price = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_PRICE));
            ingredient.setPrice(price);

            result.add(ingredient);
        }
        cursor.close();

        return result;
    }

    public ArrayList<IngredientOrder> getListOfIngredientOrders(Ingredient ingredient){


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.IOrders.COL_INGREDIENT_ID,
                CounterDb.IOrders.COL_USER_ID,
                CounterDb.IOrders.COL_QUANTITY,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.IOrders.COL_INGREDIENT_ID + " = ?";
        String[] selectionArgs = { (new Long(ingredient.getId())).toString() };

        // Query
        Cursor cursor = db.query(
                CounterDb.IOrders.TABLE_NAME,            // The table to query
                projection,                            // The columns to return
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );

        ArrayList<IngredientOrder> result = new ArrayList<IngredientOrder>();
        while(cursor.moveToNext()) {
            // Load basic ingredient data
            long userId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IOrders.COL_USER_ID));

            long ingredientId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IOrders.COL_INGREDIENT_ID));

            float quantity = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IOrders.COL_QUANTITY));


            IngredientOrder io = new IngredientOrder(ingredientId, userId, quantity);
            result.add(io);
        }
        cursor.close();
        return result;
    }

    private IngredientType getIngredientType(long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.ITypes.COL_NAME,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.ITypes._ID + " = ?";
        String[] selectionArgs = { (new Long(id)).toString() };

        // Sort order
        //String sortOrder =
        //        CounterDb.Users._ID + " ASC";

        // Query
        Cursor cursor = db.query(
                CounterDb.ITypes.TABLE_NAME,           // The table to query
                projection,                            // The columns to return
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );
        // Data processing
        IngredientType result;
        if(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.ITypes.COL_NAME));
            result = new IngredientType(id, name);

        } else {
            result = new IngredientType(0, "");
        }
        cursor.close();

        return result;
    }


}



