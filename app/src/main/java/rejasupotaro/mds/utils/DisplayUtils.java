package rejasupotaro.mds.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
    public static int getWidthPixels(Context context) {
        if (context == null) {
            return 0;
        }
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getHeightPixels(Context context) {
        if (context == null) {
            return 0;
        }
        return getDisplayMetrics(context).widthPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        return context.getResources().getDisplayMetrics();
    }
}
