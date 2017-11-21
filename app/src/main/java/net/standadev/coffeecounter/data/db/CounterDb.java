package net.standadev.coffeecounter.data.db;

import android.provider.BaseColumns;

/**
 * Created by Standa on 13.11.2017.
 */

public class CounterDb {
    private CounterDb(){}

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "counter.db";

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME   = "users";
        public static final String COL_NAME     = "name";
        public static final String COL_ACTIVE   = "active";
    }

    public static class ITypes implements BaseColumns {
        public static final String TABLE_NAME   = "i_types";
        public static final String COL_NAME     = "name";
    }

    public static class IList implements BaseColumns {
        public static final String TABLE_NAME   = "i_list";
        public static final String COL_NAME     = "name";
        public static final String COL_PRICE    = "price";
        public static final String COL_CURRENCY = "currency";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_BEGIN    = "begin";
        public static final String COL_END      = "end";
    }

    public static class IOrders implements BaseColumns {
        public static final String TABLE_NAME           = "i_orders";
        public static final String COL_DATETIME         = "datetime";
        public static final String COL_INGREDIENT_ID    = "ingredient_id";
        public static final String COL_USER_ID          = "user_id";
        public static final String COL_QUANTITY         = "quantity";
    }

    public static class IUsers implements BaseColumns {
        public static final String TABLE_NAME           = "i_users";
        public static final String COL_INGREDIENT_ID    = "ingredient_id";
        public static final String COL_USER_ID          = "user_id";
        public static final String COL_QUANTITY         = "quantity";
        public static final String COL_PRICE            = "price";
        public static final String COL_CLEARED          = "cleared";
    }

}
