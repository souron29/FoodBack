package CustomerList;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

import database.DBMethods;

public class CustomerDriver {
    private static ArrayList<String> customerNames = new ArrayList<>();
    private static HashMap<String, customerDS> customerList = new HashMap<>();

    public static ArrayList<String> getCustomerNames() {
        return customerNames;
    }

    public static HashMap<String, customerDS> getCustomerList() {
        return customerList;
    }

    private static void setCustomerList(Cursor data) {
        String name;
        while (data.moveToNext()) {
            name = data.getString(data.getColumnIndex("customer_name"));
            customerNames.add(name);
            customerList.put(name, new customerDS(data));
        }
    }

    public static void createCustomerList(Context context) {
        reset();
        DBMethods.connect(context);
        Cursor data = DBMethods.getCustomerList();
        setCustomerList(data);
        DBMethods.disconnect(data);
    }

    static private void reset() {
        customerNames = new ArrayList<>();
        customerList = new HashMap<>();
    }
}
