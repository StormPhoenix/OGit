package com.stormphoenix.ogit.mvp.ui.fragments;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.adapters.GitNotificationsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.NotifyPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class NotifyFragment extends ListWithPresenterFragment<GitNotification> {
    private NotifyPresenter presenter;

    public static NotifyFragment newInstance(NotifyPresenter presenter) {
        NotifyFragment fragment = new NotifyFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public BaseRecyclerAdapter<GitNotification> getAdapter() {
        return new GitNotificationsAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    public void setPresenter(NotifyPresenter presenter) {
        this.presenter = presenter;
    }
}
