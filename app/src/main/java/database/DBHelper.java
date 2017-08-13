package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    DBHelper(Context context) {
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createListTbl(sqLiteDatabase);
        createReviewTbl(sqLiteDatabase);
    }

    private void createReviewTbl(SQLiteDatabase sqLiteDatabase) {
        String drop = "DROP TABLE IF EXISTS " + DBSchema.Reviews.TableName;
        String createTable = "CREATE TABLE " + DBSchema.Reviews.TableName + "(";
        for (int i = 0; i <= DBSchema.Reviews.Columns.length - 1; i++) {
            if (i == DBSchema.Reviews.Columns.length - 1)
                createTable += DBSchema.Reviews.Columns[i] + " " + DBSchema.Reviews.ColumnTypes[i];
            else
                createTable += DBSchema.Reviews.Columns[i] + " " + DBSchema.Reviews.ColumnTypes[i] + ",";
        }
        createTable += " )";
        sqLiteDatabase.execSQL(drop);
        sqLiteDatabase.execSQL(createTable);
    }

    private void createListTbl(SQLiteDatabase sqLiteDatabase) {
        String drop = "DROP TABLE IF EXISTS " + DBSchema.Customers.TableName;
        String createTable = "CREATE TABLE " + DBSchema.Customers.TableName + "(";
        for (int i = 0; i < DBSchema.Customers.Columns.length; i++) {
            if (i == DBSchema.Customers.Columns.length - 1)
                createTable += DBSchema.Customers.Columns[i] + " " + DBSchema.Customers.ColumnTypes[i];
            else
                createTable += DBSchema.Customers.Columns[i] + " " + DBSchema.Customers.ColumnTypes[i] + ",";
        }
        createTable += " )";
        sqLiteDatabase.execSQL(drop);
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBSchema.Customers.TableName);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    void connect() {
        db = this.getWritableDatabase();
    }

    void disconnect() {
        db.close();
    }

    void insert(String Table, ContentValues cv) {
        db.insert(Table, null, cv);
    }

    Cursor queryAll(String Table) {
        return db.query(Table, null, null, null, null, null, null);
    }

    Cursor queryNumber(String number) {
        String query = "SELECT _id from " + DBSchema.Customers.TableName + " where " + DBSchema.Customers.NUMBER + " = " + number;
        return db.rawQuery(query, null);
    }


    void wipe(String table) {
        db.delete(table, null, null);
    }

    Cursor rawQuery(String query, String[] selectionArgs) {
        return db.rawQuery(query, selectionArgs);
    }
}
