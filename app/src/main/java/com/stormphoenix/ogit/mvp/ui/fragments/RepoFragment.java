package com.stormphoenix.ogit.mvp.ui.fragments;

import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitRepositoryAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;
import com.stormphoenix.ogit.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 */

public class RepoFragment extends ListFragment<GitRepository> {

    private ListItemPresenter mPresenter = null;

    public static RepoFragment getInstance(ListItemPresenter presenter) {
        RepoFragment fragment = new RepoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.addOnViewClickListener(R.id.repository_card_wrapper, new BaseRecyclerAdapter.OnInternalViewClickListener() {
            @Override
            public void onClick(View parentV, View v, Integer position, Object values) {
                EventBus.getDefault().postSticky(values);
                ActivityUtils.startActivity(getActivity(), RepositoryActivity.getIntent(getActivity()));
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, Object values) {
                return false;
            }
        });
    }

    @Override
    public void initializeInjector() {
        // Do nothing
    }

    @Override
    public BaseRecyclerAdapter<GitRepository> getAdapter() {
        return new GitRepositoryAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    public void setPresenter(ListItemPresenter presenter) {
        mPresenter = presenter;
    }
}
