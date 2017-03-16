package com.stormphoenix.ogit.mvp.ui.fragments;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.adapters.GitEventsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.EventsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 *
 * focus on handling event message, just pass the correct EventPresenter to it,
 * and it can work automately.
 */

public class EventsFragment extends ListWithPresenterFragment<GitEvent> {

    private EventsPresenter presenter;

    public static final EventsFragment newInstance(EventsPresenter presenter) {
        EventsFragment eventsFragment = new EventsFragment();
        eventsFragment.setPresenter(presenter);
        return eventsFragment;
    }

    @Override
    public BaseRecyclerAdapter<GitEvent> getAdapter() {
        return new GitEventsAdapter(getActivity(), new ArrayList<GitEvent>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    public void setPresenter(EventsPresenter presenter) {
        this.presenter = presenter;
    }
    // waiting for coding ... ...
}
