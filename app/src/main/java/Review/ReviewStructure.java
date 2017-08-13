package Review;

import android.database.Cursor;

import database.DBSchema;

class ReviewStructure {
    private String name;
    private String number;
    private String date, birthDate, feedback;
    private String service_rating, ambiance_rating, food_rating;

    ReviewStructure(Cursor data) {

        name = data.getString(data.getColumnIndex(DBSchema.Customers.NAME));

        number = data.getString(data.getColumnIndex(DBSchema.Customers.NUMBER));

        date = data.getString(data.getColumnIndex(DBSchema.Reviews.CREATION_DATE));

        birthDate = data.getString(data.getColumnIndex(DBSchema.Customers.BIRTHDAY));

        ambiance_rating = String.valueOf(data.getInt(data.getColumnIndex(DBSchema.Reviews.AMBIANCE_RATING)));
        food_rating = String.valueOf(data.getInt(data.getColumnIndex(DBSchema.Reviews.FOOD_RATING)));
        service_rating = String.valueOf(data.getInt(data.getColumnIndex(DBSchema.Reviews.SERVICE_RATING)));

        feedback = data.getString(data.getColumnIndex(DBSchema.Reviews.SUGGESTION));
    }

    public String getDate() {
        return date;
    }

    public String getFeedback() {
        return feedback;
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

    public String getAmbiance_rating() {
        return ambiance_rating;
    }

    public String getFood_rating() {
        return food_rating;
    }

    public String getService_rating() {
        return service_rating;
    }

}
