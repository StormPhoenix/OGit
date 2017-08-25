package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.GitEventsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.EventsPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17316.
 * StormPhoenix is a intelligent Android developer.
 * <p>
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
    public BaseRecyclerAdapter<GitEvent, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitEventsAdapter(getActivity(), new ArrayList());
        mAdapter.addOnViewClickListener(R.id.header_image, new BaseRecyclerAdapter.OnInternalViewClickListener<GitEvent>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitEvent values) {
                EventBus.getDefault().postSticky(values.getActor());
                presenter.startOwnerProfileActivity();
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
                presenter.startRepoDetailsActivity();
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitEvent values) {
                return false;
            }
        });
        return mAdapter;
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
