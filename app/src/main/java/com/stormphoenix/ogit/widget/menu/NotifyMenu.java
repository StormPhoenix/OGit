package com.stormphoenix.ogit.widget.menu;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.utils.UiUtils;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class NotifyMenu extends LinearLayout {
    private static final int CONTEXT_MENU_WIDTH = UiUtils.dpToPx(240);

    public NotifyMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        Log.e(NotifyMenu.class.getSimpleName(), "init: init");
        LayoutInflater.from(getContext()).inflate(R.layout.menu_notification, this, true);
        setBackgroundResource(R.drawable.bg_container_shadow);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(CONTEXT_MENU_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setNofityContent(String htmlMessage) {
        ((Button) NotifyMenu.this.findViewById(R.id.btn_unread)).setText(Html.fromHtml(htmlMessage));
    }

    public void dismiss() {
        Log.e(NotifyMenu.class.getSimpleName(), "dismiss: ");
        ((ViewGroup) getParent()).removeView(NotifyMenu.this);
    }
}
