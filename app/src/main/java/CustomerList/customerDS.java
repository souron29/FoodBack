package CustomerList;

import android.database.Cursor;

import database.DBSchema;

class customerDS {
    private String name;
    private String number;
    private String birthDate;
    private int visits;

    customerDS(Cursor data) {
        this.name = data.getString(data.getColumnIndex(DBSchema.Customers.NAME));
        this.number = data.getString(data.getColumnIndex(DBSchema.Customers.NUMBER));
        this.birthDate = data.getString(data.getColumnIndex(DBSchema.Customers.BIRTHDAY));
        this.visits = 0;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public int getVisits() {
        return visits;
    }
}
