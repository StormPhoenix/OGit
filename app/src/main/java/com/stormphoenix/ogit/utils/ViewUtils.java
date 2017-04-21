package com.stormphoenix.ogit.utils;

/**
 * Created by StormPhoenix on 17-3-25.
 * StormPhoenix is a intelligent Android developer.
 */

import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class ViewUtils {

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

    public static <V extends View> V setGone(V view, boolean gone) {
        if (view != null) {
            if (gone) {
                if (View.GONE != view.getVisibility()) {
                    view.setVisibility(View.GONE);
                }
            } else if (View.VISIBLE != view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    public static <V extends View> V setInvisible(V view, boolean invisible) {
        if (view != null) {
            if (invisible) {
                if (View.INVISIBLE != view.getVisibility()) {
                    view.setVisibility(View.INVISIBLE);
                }
            } else if (View.VISIBLE != view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    public static void increaseHitRectBy(int amount, View delegate) {
        increaseHitRectBy(amount, amount, amount, amount, delegate);
    }

    public static void increaseHitRectBy(final int top, final int left, final int bottom, final int right, final View delegate) {
        final View parent = (View) delegate.getParent();
        if (parent != null && delegate.getContext() != null) {
            parent.post(new Runnable() {
                public void run() {
                    float densityDpi = (float) delegate.getContext().getResources().getDisplayMetrics().densityDpi;
                    Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= ViewUtils.transformToDensityPixel(top, densityDpi);
                    r.left -= ViewUtils.transformToDensityPixel(left, densityDpi);
                    r.bottom += ViewUtils.transformToDensityPixel(bottom, densityDpi);
                    r.right += ViewUtils.transformToDensityPixel(right, densityDpi);
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        }

    }

    public static int transformToDensityPixel(int regularPixel, DisplayMetrics displayMetrics) {
        return transformToDensityPixel(regularPixel, (float) displayMetrics.densityDpi);
    }

    public static int transformToDensityPixel(int regularPixel, float densityDpi) {
        return (int) ((float) regularPixel * densityDpi);
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private ViewUtils() {
    }
}

