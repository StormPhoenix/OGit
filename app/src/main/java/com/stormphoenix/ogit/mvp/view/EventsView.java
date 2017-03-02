package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitEvent;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public interface EventsView extends BaseView {

    /**
     * 初始化 github 事件数据列表
     */
    void initEventsListView();

    void showProgress();

    void showMessage(String message);

    void initRefreshLayout();

    void loadGitEvents(List<GitEvent> gitEvents);

    void startRefresh();

    void stopRefresh();

    int getGitEventCounts();

    void addGitEvents(List<GitEvent> gitEvents);
}
