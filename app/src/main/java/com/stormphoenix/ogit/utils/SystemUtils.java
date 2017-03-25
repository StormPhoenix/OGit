package com.stormphoenix.ogit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;
import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class SystemUtils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Get default display
     *
     * @param context
     * @return display
     */
    public static Display getDisplay(final Context context) {
        return ((WindowManager) context.getSystemService(WINDOW_SERVICE))
                .getDefaultDisplay();
    }

    /**
     * Get default display
     *
     * @param view
     * @return display
     */
    public static Display getDisplay(final View view) {
        return getDisplay(view.getContext());
    }

    /**
     * Get default display width
     *
     * @param context
     * @return display
     */
    @SuppressWarnings("deprecation")
    public static int getDisplayWidth(final Context context) {
        return getDisplay(context).getWidth();
    }

    /**
     * Get default display width
     *
     * @param view
     * @return display
     */
    public static int getDisplayWidth(final View view) {
        return getDisplayWidth(view.getContext());
    }

    /**
     * Get pixels from dps
     *
     * @param view
     * @param dp
     * @return pixels
     */
    public static float getPixels(final View view, final int dp) {
        return getPixels(view.getResources(), dp);
    }

    /**
     * Get pixels from dps
     *
     * @param resources
     * @param dp
     * @return pixels
     */
    public static float getPixels(final Resources resources, final int dp) {
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }

    /**
     * Get pixels from dps
     *
     * @param view
     * @param dp
     * @return pixels
     */
    public static int getIntPixels(final View view, final int dp) {
        return getIntPixels(view.getResources(), dp);
    }

    /**
     * Get pixels from dps
     *
     * @param context
     * @param dp
     * @return pixels
     */
    public static int getIntPixels(final Context context, final int dp) {
        return getIntPixels(context.getResources(), dp);
    }

    /**
     * Get pixels from dps
     *
     * @param resources
     * @param dp
     * @return pixels
     */
    public static int getIntPixels(final Resources resources, final int dp) {
        float pixels = TypedValue.applyDimension(COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
        return (int) Math.floor(pixels + 0.5F);
    }
}
