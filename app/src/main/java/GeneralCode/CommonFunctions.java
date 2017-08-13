package GeneralCode;

import android.graphics.Rect;
import android.text.InputType;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CommonFunctions {
    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int TOP = 4;
    public static final int BOTTOM = 5;


    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(CommonVariables.getCommonDateFormat(4), Locale.US);
        return df.format(cal.getTime());
    }

    public static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isNumber(String value, String[] exclude) {
        boolean ret = false;
        String toCheck = value;
        if (exclude != null) {
            for (String c : exclude) {
                toCheck = toCheck.replace(c, "");
            }
        }
        if (!isBlank(toCheck)) {
            ret = toCheck.matches("^[0-9]+$");
        }
        return ret;
    }

    public static boolean isPhoneNumber(String number) {
        if (isNumber(number, new String[]{"+91"}))
            if (clipString(number, new String[]{"+91"}).length() == 10) return true;
        return false;
    }

    public static String clipString(String input, String[] args) {
        String toCheck = input;
        if (args != null && toCheck != null) {
            for (String c : args) {
                toCheck = toCheck.replace(c, "");
            }
            return toCheck;
        }
        return null;
    }

    public static String decode(int value, int[] compare, int[] output) {
        if (compare != null && output != null && compare.length == output.length)
            for (int i = 0; i < compare.length; i++) {
                if (Objects.equals(value, compare[i])) return String.valueOf(output[i]);
            }

        return null;
    }

    public static Object decode(Object code, Object... args) {
        int index = 0;

        while (index < args.length) {
            if (args[index].equals(code)) {

                return args[index];
            }
            index++;
        }
        return null;
    }

    public static <T extends View> int getDimensions(T view, int type) {
        Rect rectf = new Rect();
        view.getGlobalVisibleRect(rectf);

        if (type == WIDTH) return rectf.width();
        if (type == HEIGHT) return rectf.height();
        if (type == TOP) return rectf.top;
        if (type == BOTTOM) return rectf.bottom;
        if (type == LEFT) return rectf.left;
        if (type == RIGHT) return rectf.right;

        return 0;
    }

    public static boolean isPassword(int input_type) {
        return decode(input_type, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD) != null;
    }
}
