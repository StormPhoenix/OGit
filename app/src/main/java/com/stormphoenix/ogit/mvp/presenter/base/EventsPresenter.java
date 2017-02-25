package com.stormphoenix.ogit.mvp.presenter.base;

import android.os.Bundle;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.mvp.view.EventsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsPresenter extends BasePresenter<EventsView> {

    private List<GitEvent> eventsList = null;

    @Inject
    public EventsPresenter() {
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);

        loadGitEventList();
    }

    private void loadGitEventList() {
        eventsList = new ArrayList<>();
        mView.initEventsListView(eventsList);
    }
}
