package GeneralCode;

import java.text.DecimalFormat;

public class CommonVariables {
    public static String currentDay, currentMonth, currentYear;
    public static String currentDate, birthday;
    public static int BDay, BMonth, BYear;
    public static boolean dateset = false; // used in new_customers for checking if bithdate has been saved

    public static String getCommonDateFormat(int i) {
        if (i == 1) return "dd";
        if (i == 2) return "MM";
        if (i == 3) return "yyyy";
        return "dd/MM/yyyy HH:mm";
    }

    public static String setCurrentDate() {
        String Date = CommonFunctions.getCurrentDate();
        currentDate = Date;
        currentDay = currentDate.substring(0, 2);
        currentMonth = currentDate.substring(3, 5);
        currentYear = currentDate.substring(6, 10);
        return Date;
    }

    public static String createBirthDate() {
        DecimalFormat formatter = new DecimalFormat("00");
        birthday = formatter.format(BDay) + "/" + formatter.format(BMonth) + "/" + BYear;
        return formatter.format(BDay) + "/" + formatter.format(BMonth) + "/" + BYear;
    }

}
