package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitUserAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.ContributorsPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class ContributorsFragment extends ListFragment<GitUser> {
    @Inject
    public ContributorsPresenter mPresenter;

    @BindView(R.id.recy_contributor_list)
    RecyclerView mRecyContributorList;
    @BindView(R.id.contibuters_refresh_layout)
    SwipeRefreshLayout mContibutersRefreshLayout;

    public static BaseFragment getInstance() {
        return new ContributorsFragment();
    }

    @Override
    public BaseRecyclerAdapter<GitUser> getAdapter() {
        return new GitUserAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return mContibutersRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyContributorList;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contributors;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
