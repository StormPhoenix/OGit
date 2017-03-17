package com.stormphoenix.ogit.widget.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.utils.UiUtils;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class VerticalMenu extends LinearLayout {
    private static final int CONTEXT_MENU_WIDTH = UiUtils.dpToPx(240);

    public VerticalMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        Log.e(VerticalMenu.class.getSimpleName(), "init: init");
        LayoutInflater.from(getContext()).inflate(R.layout.menu_test, this, true);
        setBackgroundResource(R.drawable.bg_container_shadow);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(CONTEXT_MENU_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void dismiss() {
        Log.e(VerticalMenu.class.getSimpleName(), "dismiss: ");
        ((ViewGroup) getParent()).removeView(VerticalMenu.this);
    }
}
