package com.stormphoenix.ogit.utils;

/**
 * Created by StormPhoenix on 17-3-25.
 * StormPhoenix is a intelligent Android developer.
 */

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;

public class ViewUtils {
    public static <V extends View> V setGone(V view, boolean gone) {
        if(view != null) {
            if(gone) {
                if(View.GONE != view.getVisibility()) {
                    view.setVisibility(View.GONE);
                }
            } else if(View.VISIBLE != view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    public static <V extends View> V setInvisible(V view, boolean invisible) {
        if(view != null) {
            if(invisible) {
                if(View.INVISIBLE != view.getVisibility()) {
                    view.setVisibility(View.INVISIBLE);
                }
            } else if(View.VISIBLE != view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    public static void increaseHitRectBy(int amount, View delegate) {
        increaseHitRectBy(amount, amount, amount, amount, delegate);
    }

    public static void increaseHitRectBy(final int top, final int left, final int bottom, final int right, final View delegate) {
        final View parent = (View)delegate.getParent();
        if(parent != null && delegate.getContext() != null) {
            parent.post(new Runnable() {
                public void run() {
                    float densityDpi = (float)delegate.getContext().getResources().getDisplayMetrics().densityDpi;
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
        return transformToDensityPixel(regularPixel, (float)displayMetrics.densityDpi);
    }

    public static int transformToDensityPixel(int regularPixel, float densityDpi) {
        return (int)((float)regularPixel * densityDpi);
    }

    private ViewUtils() {
    }
}

