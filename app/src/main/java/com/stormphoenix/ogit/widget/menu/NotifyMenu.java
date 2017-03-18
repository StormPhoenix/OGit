package com.stormphoenix.ogit.widget.menu;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.UiUtils;
import com.stormphoenix.ogit.widget.manager.NotifyMenuManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

    public void setNofityContent(String htmlMessage, List<GitNotification> notifications) {
        ((Button) NotifyMenu.this.findViewById(R.id.btn_unread)).setText(Html.fromHtml(htmlMessage));
        NotifyMenu.this.findViewById(R.id.btn_unread).setOnClickListener((v) -> {
            NotifyMenuManager.getInstance().toggleMenuFromView(null);
            EventBus.getDefault().postSticky(notifications);
            Bundle bundle = new Bundle();
            bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_NOTIFICATION);
            ActivityUtils.startActivity(getContext(), ToolbarActivity.newIntent(getContext(), bundle));
        });
        NotifyMenu.this.findViewById(R.id.btn_cancel).setOnClickListener((v) -> {
            NotifyMenuManager.getInstance().toggleMenuFromView(null);
        });
    }

    public void dismiss() {
        Log.e(NotifyMenu.class.getSimpleName(), "dismiss: ");
        ((ViewGroup) getParent()).removeView(NotifyMenu.this);
    }
}
