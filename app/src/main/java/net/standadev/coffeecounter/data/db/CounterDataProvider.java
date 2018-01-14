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
import net.standadev.coffeecounter.data.IngredientUser;
import net.standadev.coffeecounter.data.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Standa on 21.11.2017.
 */

public class CounterDataProvider {
    private CounterDbHelper dbHelper;

    public CounterDataProvider(Context context) {
        dbHelper = new CounterDbHelper(context);

    }

    public void insert(BaseId baseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert new line and return primary key
        long newId = db.insert(
                baseId.getTableName(),
                null,
                baseId.getValues());
        baseId.setId(newId);
    }

    public void update(BaseId baseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Insert new line and return primary key
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {(new Long(baseId.getId())).toString()};

        int count = db.update(
                baseId.getTableName(),
                baseId.getValues(),
                selection,
                selectionArgs);
    }


    public ArrayList<User> getListOfUsers() {
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
        while (cursor.moveToNext()) {
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

    public ArrayList<Ingredient> getListOfIngredients(boolean isClosed) {
        return getListOfIngredients(-1, isClosed);
    }

    public Ingredient getIngredient(long ingredientId){
        ArrayList<Ingredient> list;
        list = getListOfIngredients(ingredientId, true);

        return list.get(0);
    }

    private ArrayList<Ingredient> getListOfIngredients(long ingredientId, boolean isClosed) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.IList._ID,
                CounterDb.IList.COL_NAME,
                CounterDb.IList.COL_TYPE_ID,
                CounterDb.IList.COL_PRICE,
                CounterDb.IList.COL_QUANTITY,
                CounterDb.IList.COL_DATE_FROM,
                CounterDb.IList.COL_DATE_TO,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.IList.COL_CLOSED + " = ?";

        if (ingredientId >= 0) {
            selection += " and " + CounterDb.IList._ID + " = ?";
        }else{
            selection += " and " + CounterDb.IList._ID + " > ?";
        }

        String[] selectionArgs = {getDbBoolean(isClosed), Long.valueOf(ingredientId).toString()};


        // Sort order
        String sortOrder = CounterDb.IList.COL_TYPE_ID + " ASC";

        // Query
        Cursor cursor = db.query(
                CounterDb.IList.TABLE_NAME,            // The table to query
                projection,                            // The columns to return
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                sortOrder                                   // The sort order
        );
        // Data processing
        ArrayList<Ingredient> result = new ArrayList<Ingredient>();
        while (cursor.moveToNext()) {
            // Load basic ingredient data
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList._ID));

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_NAME));

            long type_id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_TYPE_ID));

            Ingredient ingredient = new Ingredient(id, name, getIngredientType(type_id));

            // Set status of statement (especially for closed statements, this line must be before
            // setting of dateTo field
            ingredient.setClosed(isClosed);

            // Load price
            float price = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_PRICE));
            ingredient.setPrice(price);

            // Load quantity
            float quantity = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_QUANTITY));
            ingredient.setQuantity(quantity);

            // Load period of ingredient
            long date_from = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_DATE_FROM));
            ingredient.setDateFrom(new Date(date_from));

            long date_to = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IList.COL_DATE_TO));
            ingredient.setDateTo(new Date(date_to));

            result.add(ingredient);
        }
        cursor.close();

        return result;
    }

    public ArrayList<IngredientOrder> getListOfIngredientOrders(Ingredient ingredient) {


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.IOrders.COL_INGREDIENT_ID,
                CounterDb.IOrders.COL_USER_ID,
                CounterDb.IOrders.COL_QUANTITY,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.IOrders.COL_INGREDIENT_ID + " = ?";
        String[] selectionArgs = {(new Long(ingredient.getId())).toString()};

        // Query
        Cursor cursor = db.query(
                CounterDb.IOrders.TABLE_NAME,          // The table to query
                projection,                            // The columns to return
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );

        ArrayList<IngredientOrder> result = new ArrayList<IngredientOrder>();
        while (cursor.moveToNext()) {
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

    public ArrayList<IngredientUser> getListOfStatementItems(User user, boolean onlyNotCleared) {
        return getListOfStatementItems(CounterDb.IUsers.COL_USER_ID, user.getId(), onlyNotCleared);
    }

    public ArrayList<IngredientUser> getListOfStatementItems(Long ingredientId) {
        return getListOfStatementItems(CounterDb.IUsers.COL_INGREDIENT_ID, ingredientId, false);
    }

    private ArrayList<IngredientUser> getListOfStatementItems(String keyColumnName, long keyColumnId, boolean onlyNotCleared) {


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.IUsers.COL_INGREDIENT_ID,
                CounterDb.IUsers.COL_USER_ID,
                CounterDb.IUsers.COL_QUANTITY,
                CounterDb.IUsers.COL_PRICE,
        };

        // Filter
        String selection =  keyColumnName + " = ? ";

        if (onlyNotCleared){
            selection += " and " + CounterDb.IUsers.COL_CLEARED + " = 0";
        }
        String[] selectionArgs = { Long.valueOf(keyColumnId).toString()};

        // Query
        Cursor cursor = db.query(
                CounterDb.IUsers.TABLE_NAME,          // The table to query
                projection,                            // The columns to return
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );

        ArrayList<IngredientUser> result = new ArrayList<IngredientUser>();
        while (cursor.moveToNext()) {
            // Load basic ingredient data
            long userId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IUsers.COL_USER_ID));

            long ingredientId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CounterDb.IUsers.COL_INGREDIENT_ID));

            float quantity = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IUsers.COL_QUANTITY));

            float price = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(CounterDb.IUsers.COL_PRICE));


            IngredientUser iu = new IngredientUser(ingredientId, userId);
            iu.setQuantity(quantity);
            iu.setPrice(price);
            result.add(iu);
        }
        cursor.close();
        return result;
    }

    public void clearDebt(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Insert new line and return primary key
        String selection = CounterDb.IUsers.COL_USER_ID + " = ? and "
                + CounterDb.IUsers.COL_CLEARED + " = ?";
        String[] selectionArgs = { Long.valueOf(user.getId()).toString(), "0"};

        ContentValues values = new ContentValues();
        values.put(CounterDb.IUsers.COL_CLEARED, true);

        int count = db.update(
                CounterDb.IUsers.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }



    private IngredientType getIngredientType(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                CounterDb.ITypes.COL_NAME,
                CounterDb.ITypes.COL_ACTIVE,
                CounterDb.ITypes.COL_UNIT,

        };

// Filter results WHERE "title" = 'My Title'
        String selection = CounterDb.ITypes._ID + " = ?";
        String[] selectionArgs = {(new Long(id)).toString()};

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
        if (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.ITypes.COL_NAME));
            result = new IngredientType(id, name);

            Boolean isActive = 0 != cursor.getInt(
                    cursor.getColumnIndexOrThrow(CounterDb.ITypes.COL_ACTIVE));
            result.setActive(isActive);

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(CounterDb.ITypes.COL_UNIT));
            result.setUnit(unit);

        } else {
            result = new IngredientType(0, "");
        }
        cursor.close();

        return result;
    }


    private String getDbBoolean(boolean bool){
        if (bool){
            return "1";
        }
        return "0";
    }


}



