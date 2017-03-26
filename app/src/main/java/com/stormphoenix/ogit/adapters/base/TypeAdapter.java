package com.stormphoenix.ogit.adapters.base;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stormphoenix.ogit.shares.ViewUpdater;

/**
 * Created by StormPhoenix on 17-3-26.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class TypeAdapter extends BaseAdapter {
    protected final ViewUpdater updater = new ViewUpdater();

    public TypeAdapter() {
    }

    protected View initialize(View view, int[] children) {
        return this.updater.initialize(view, children);
    }

    protected TextView setText(int childViewIndex, CharSequence text) {
        return this.updater.setText(childViewIndex, text);
    }

    protected TextView setText(View parentView, int childViewIndex, CharSequence text) {
        return this.updater.setText(parentView, childViewIndex, text);
    }

    protected TextView setText(View parentView, int childViewIndex, int resId) {
        return this.updater.setText(parentView, childViewIndex, resId);
    }

    protected View setGone(int childViewIndex, boolean gone) {
        return this.updater.setGone(childViewIndex, gone);
    }

    protected View setGone(View parentView, int childViewIndex, boolean gone) {
        return this.updater.setGone(parentView, childViewIndex, gone);
    }

    protected void setCurrentView(View view) {
        this.updater.setCurrentView(view);
    }
}
