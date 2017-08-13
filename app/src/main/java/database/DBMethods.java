package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;

import GeneralCode.CommonFunctions;

public class DBMethods {
    private static DBHelper dbHelper;

    static public void connect(Context context) {
        dbHelper = new DBHelper(context);
        dbHelper.connect();
    }

    static public void disconnect(Cursor cursors) {
        if (cursors != null) {
            if (!cursors.isClosed())
                cursors.close();
        }

        dbHelper.disconnect();
        dbHelper = null;
    }

    static public void insert(String table, ContentValues data) {
        dbHelper.insert(table, data);
    }

    public static long checkNumber(String number) {
        String value = CommonFunctions.clipString(number, new String[]{"+91"});

        Cursor query = dbHelper.queryNumber(value);
        long id = -1;

        if (query != null && query.getCount() > 0) {

            query.moveToFirst();
            id = query.getInt(0);
            query.close();
        }
        return id;
    }

    public static Cursor getCustomerList() {
        String query = DBCommonFunctions.basicQueryBuilder(DBSchema.Customers.TableName, null, null, "1 desc");
        /*String query = "select * from " + TABLE_CUSTOMER_LIST + " order by 1 desc";*/
        return dbHelper.rawQuery(query, null);
    }

    static public void delete(String table, String where) {
        dbHelper.wipe(table);
    }

    public static Cursor getReviewList() {
        String a = "a", b = "b", where;
        HashMap<String, Boolean> orderBy = new HashMap<>();
        HashMap<String, String> table = new HashMap<>();
        HashMap<String, String> columns = new HashMap<>();
        // Entering table names
        table.put(DBSchema.Reviews.TableName, a);
        table.put(DBSchema.Customers.TableName, b);
        // Entering Column Names
        for (String c : DBSchema.Reviews.Columns) {
            columns.put(c, a);
        }
        columns.put(DBSchema.Customers.NAME, b);
        columns.put(DBSchema.Customers.NUMBER, b);
        columns.put(DBSchema.Customers.BIRTHDAY, b);
        where = a + "." + DBSchema.Reviews.CUSTOMER_ID + "=" + b + "." + DBSchema.Customers._ID;
        orderBy.put(a + "." + DBSchema.Reviews._ID, false);
        String query = DBCommonFunctions.ultimateQueryBuilder(table, columns, where, orderBy);
        return dbHelper.rawQuery(query, null);
    }
}
