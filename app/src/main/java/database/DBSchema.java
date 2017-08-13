package database;

public class DBSchema {
    static final int DATABASE_VERSION = 6;
    static final String DATABASE_NAME = "extralj.db";

    public static class Customers {
        public static final String _ID = "_id", NAME = "customer_name", NUMBER = "customer_number", BIRTHDAY = "customer_birthday", CREATION_DATE = "customer_creation_date";
        public static final String TableName = "customer_list_table";
        public static final String[] Columns = {Customers._ID, Customers.NAME, Customers.NUMBER, Customers.BIRTHDAY, CREATION_DATE};
        public static final String[] ColumnTypes = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT", "TEXT"};
    }

    public static class Reviews {
        public static final String _ID = "_id", CUSTOMER_ID = "customer_id", AMBIANCE_RATING = "ambiance_rating", FOOD_RATING = "food_rating", SERVICE_RATING = "service_rating", SUGGESTION = "suggestion", CREATION_DATE = "review_creation_date";
        public static final String TableName = "customer_review_table";
        public static final String[] Columns = {_ID, CUSTOMER_ID, AMBIANCE_RATING, FOOD_RATING, SERVICE_RATING, SUGGESTION, CREATION_DATE};
        public static final String[] ColumnTypes = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "TEXT", "TEXT"};
    }

}
