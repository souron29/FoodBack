package Review;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

import database.DBMethods;
import database.DBSchema;

public class ReviewDriver {
    static ArrayList<Long> reviewID = new ArrayList<>();
    static HashMap<Long, ReviewStructure> reviewList = new HashMap<>();

    public static void createReviewList(Context context) {
        reset();
        DBMethods.connect(context);
        Cursor data = DBMethods.getReviewList();
        if (data != null && data.getCount() > 0)
            setReviewList(data);
        DBMethods.disconnect(data);
    }

    private static void setReviewList(Cursor data) {
        Long id;
        while (data.moveToNext()) {
            id = data.getLong(data.getColumnIndex(DBSchema.Reviews._ID));
            reviewID.add(id);
            reviewList.put(id, new ReviewStructure(data));
        }
    }

    static private void reset() {
        reviewID = new ArrayList<>();
        reviewList = new HashMap<>();
    }
}
