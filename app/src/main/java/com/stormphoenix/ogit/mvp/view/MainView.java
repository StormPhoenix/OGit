package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.mvp.view.base.MessageView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public interface MainView extends MessageView {
    void saveNotificationMessage(List<GitNotification> notifications);
}
