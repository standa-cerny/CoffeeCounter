package net.standadev.coffeecounter.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.standadev.coffeecounter.data.User;

import java.util.ArrayList;

/**
 * Created by Standa on 21.11.2017.
 */

public class CounterDataProvider {
    private CounterDbHelper dbHelper;

    public CounterDataProvider(Context context) {
        dbHelper = new CounterDbHelper(context);

    }

    public void insertUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert new line and return primary key
        long newId = db.insert(
                CounterDb.Users.TABLE_NAME,
                null,
                getUserValues(user));
        user.setId(newId);
    }

    public void updateUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Insert new line and return primary key
        String selection = CounterDb.Users._ID + " = ?";
        String[] selectionArgs = { "" + user.getId() };

        int count = db.update(
                CounterDb.Users.TABLE_NAME,
                getUserValues(user),
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

    private ContentValues getUserValues(User user){
        ContentValues values = new ContentValues();
        values.put(CounterDb.Users.COL_NAME, user.getName());
        values.put(CounterDb.Users.COL_ACTIVE, user.isActive());

        return values;
    }

}
