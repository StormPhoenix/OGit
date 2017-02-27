package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitEventsAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.EventsPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.EventsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsFragment extends BaseFragment implements EventsView {

    @BindView(R.id.recy_event_list)
    RecyclerView mRecyEventList;

    View rootView;

    private GitEventsAdapter mAdapter = null;

    @Inject
    public EventsPresenter mPresenter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void initEventsListView(List<GitEvent> events) {
        if (mAdapter == null) {
            mAdapter = new GitEventsAdapter(getActivity(), events);
        }

        mRecyEventList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyEventList.setHasFixedSize(true);
        mRecyEventList.setAdapter(mAdapter);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }
}
