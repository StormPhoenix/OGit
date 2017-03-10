package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitEventsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.EventsPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsFragment extends ListFragment<GitEvent> {
    @BindView(R.id.recy_event_list)
    RecyclerView mRecyEventList;

    @BindView(R.id.events_refresh_layout)
    SwipeRefreshLayout mEventsRefreshLayout;

    @Inject
    public EventsPresenter mEventPresenter;

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.setOnViewClickListener(R.id.header_image, mEventPresenter);
    }

    public static EventsFragment getInstance(String username) {
        EventsFragment eventsFragment = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, username);
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_events;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public BaseRecyclerAdapter<GitEvent> getAdapter() {
        return new GitEventsAdapter(getActivity(), new ArrayList<GitEvent>());
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return mEventsRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyEventList;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mEventPresenter;
    }
}
