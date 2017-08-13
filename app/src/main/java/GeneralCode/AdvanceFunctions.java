package GeneralCode;

import android.content.Context;

import java.util.Objects;

public class AdvanceFunctions {
    Context mContext;

    public AdvanceFunctions(Context mContext) {
        this.mContext = mContext;
    }

    public static <T extends Object> T getResource(Context context, String resourceType, String resourceName) {
        int id;
        if (Objects.equals(resourceType.toLowerCase(), "string") || Objects.equals(resourceType.toLowerCase(), "strings")) {
            id = context.getResources().getIdentifier(resourceName.toLowerCase(), resourceType.toLowerCase(), context.getPackageName());
            return (T) context.getResources().getString(id);
        }

        return null;
    }
}
