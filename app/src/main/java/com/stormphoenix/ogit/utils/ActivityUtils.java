package com.stormphoenix.ogit.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by StormPhoenix on 17-3-6.
 * StormPhoenix is a intelligent Android developer.
 */

public class ActivityUtils {
    public static void startActivity(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (context instanceof Activity) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, null);
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        } else {
            context.startActivity(intent);
        }
    }
}
