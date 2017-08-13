package database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class DBCommonFunctions {
    static String advanceQueryBuilder(@NonNull String table, String[] projection, String where, HashMap<String, String> orderBy) {
        String query;
        int counter;
        query = "SELECT ";
        if (projection == null) query += "* ";
        else {
            for (counter = 0; counter < projection.length; counter++) {
                if (counter == projection.length - 1) query += projection[counter] + " ";
                else query += projection[counter] + ", ";
            }
        }
        if (where != null) query += "where " + where + " ";
        if (orderBy != null) {
            query += "order by ";
            counter = 0;
            for (Map.Entry<String, String> entry : orderBy.entrySet()) {
                counter++;
                String column_name = entry.getKey();
                String order_type = entry.getValue();
                query += column_name + " " + order_type;
                if (counter < orderBy.size()) query += ", ";
            }

        }
        return query;
    }

    static String basicQueryBuilder(@NonNull String table, String[] projection, String where, String orderBy) {
        String query;
        int counter;
        query = "SELECT ";
        if (projection == null) query += "* ";
        else {
            for (counter = 0; counter < projection.length; counter++) {
                if (counter == projection.length - 1) query += projection[counter] + " ";
                else query += projection[counter] + ", ";
            }
        }
        query += "FROM " + table + " ";
        if (where != null) query += "WHERE " + where + " ";
        if (orderBy != null) {
            query += "ORDER BY " + orderBy;
        }
        return query;
    }

    static String advanceQueryBuilder(@NonNull String table, ArrayList<String> projection, String where, String orderBy) {
        String query;
        int counter;
        query = "SELECT ";
        if (projection == null) query += "* ";
        else {
            for (counter = 0; counter < projection.size(); counter++) {
                if (counter == projection.size() - 1) query += projection.get(counter) + " ";
                else query += projection.get(counter) + ", ";
            }
        }
        query += "FROM " + table + " ";
        if (where != null) query += "WHERE " + where + " ";
        if (orderBy != null) {
            query += "ORDER BY " + orderBy;
        }
        return query;
    }

    static String ultimateQueryBuilder(@NonNull HashMap<String, String> tableAlias, HashMap<String, String> projection, String where, HashMap<String, Boolean> orderBy) {
        String query;
        int counter = 0;
        query = "SELECT ";
        if (projection == null) query += "* ";
        else {
            for (HashMap.Entry<String, String> entry : projection.entrySet()) {

                String col_name = entry.getKey();
                String table_alias = entry.getValue();
                if (counter == projection.size() - 1) query += table_alias + "." + col_name + " ";
                else query += table_alias + "." + col_name + ", ";
                counter++;
            }
        }
        counter = 0;
        query += "FROM ";
        for (HashMap.Entry<String, String> entry : tableAlias.entrySet()) {

            String table_name = entry.getKey();
            String table_alias = entry.getValue();
            if (counter == tableAlias.size() - 1) query += table_name + " " + table_alias + " ";
            else query += table_name + " " + table_alias + ", ";
            counter++;
        }
        if (where != null) query += "WHERE " + where + " ";
        counter = 0;
        if (orderBy != null) {
            query += "ORDER BY ";
            for (HashMap.Entry<String, Boolean> entry : orderBy.entrySet()) {

                String col_name = entry.getKey();
                String col_sort = entry.getValue() ? "ASC" : "DESC";
                if (counter == orderBy.size() - 1) query += col_name + " " + col_sort + " ";
                else query += col_name + " " + col_sort + ", ";
                counter++;
            }
        }

        return query;
    }
}
