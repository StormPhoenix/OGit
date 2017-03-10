package com.stormphoenix.ogit.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by StormPhoenix on 17-3-6.
 * StormPhoenix is a intelligent Android developer.
 */

public class ActivityUtils {
    public static void startActivity(Context context, Intent intent) {
        if (context instanceof Activity) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, null);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * Created by StormPhoenix on 17-2-25.
     * StormPhoenix is a intelligent Android developer.
     */

    public static class ViewUtils {
        public static void clear(View v) {
            ViewCompat.setAlpha(v, 1);
            ViewCompat.setScaleY(v, 1);
            ViewCompat.setScaleX(v, 1);
            ViewCompat.setTranslationY(v, 0);
            ViewCompat.setTranslationX(v, 0);
            ViewCompat.setRotation(v, 0);
            ViewCompat.setRotationY(v, 0);
            ViewCompat.setRotationX(v, 0);
            v.setPivotY(v.getMeasuredHeight() / 2);
            ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
            ViewCompat.animate(v).setInterpolator(null);
        }

        public static void showMessage(View view, String message) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }
}
