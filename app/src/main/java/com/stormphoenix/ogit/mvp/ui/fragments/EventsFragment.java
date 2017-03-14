package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.view.View;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitEventsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.list.EventsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsFragment extends ListWithPresenterFragment<GitEvent> {

    @Inject
    public EventsPresenter mEventPresenter;

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.addOnViewClickListener(R.id.header_image, new BaseRecyclerAdapter.OnInternalViewClickListener<GitEvent>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitEvent values) {
                EventBus.getDefault().postSticky(values.getActor());
                mEventPresenter.startUserDetailsActivity();
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitEvent values) {
                return false;
            }
        });
        mAdapter.addOnViewClickListener(R.id.text_event_info, new BaseRecyclerAdapter.OnInternalViewClickListener<GitEvent>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitEvent values) {
                EventBus.getDefault().postSticky(values.getRepo());
                mEventPresenter.startRepoDetailsActivity();
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitEvent values) {
                return false;
            }
        });
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
        return R.layout.fragment_refresh_recyclerview;
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
    public ListItemPresenter getListItemPresetner() {
        return mEventPresenter;
    }
}
