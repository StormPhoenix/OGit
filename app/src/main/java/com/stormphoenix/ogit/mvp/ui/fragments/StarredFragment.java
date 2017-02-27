package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitRepositoryAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.StarredPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.StarredView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class StarredFragment extends BaseFragment implements StarredView {
    View rootView;
    @BindView(R.id.recy_starred_list)
    RecyclerView mRecyStarredList;

    GitRepositoryAdapter mAdapter;

    @Inject
    StarredPresenter mPresenter;

    public static StarredFragment getInstance(String username) {
        StarredFragment starredFragment = new StarredFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, username);
        starredFragment.setArguments(bundle);
        return starredFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_starred;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this.getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void initGitRepositoryList(List<GitRepository> repositories) {
        if (mAdapter == null) {
            mAdapter = new GitRepositoryAdapter(getActivity(), repositories);
        }

        mRecyStarredList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyStarredList.setHasFixedSize(true);
        mRecyStarredList.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}
