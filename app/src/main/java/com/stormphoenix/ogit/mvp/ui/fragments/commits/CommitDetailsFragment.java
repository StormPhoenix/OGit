package com.stormphoenix.ogit.mvp.ui.fragments.commits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitCommitFile;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.adapters.base.MultiTypeAdapter;
import com.stormphoenix.ogit.adapters.commits.GitCommitDetailsAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.commits.CommitDetailsPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;
import com.stormphoenix.ogit.mvp.view.CommitDetailsView;
import com.stormphoenix.ogit.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitDetailsFragment extends ListFragment<MultiTypeAdapter.Item> implements CommitDetailsView {
    @Inject
    public CommitDetailsPresenter mPresenter;
    private DiffStyler diffStyler;
    private String owner;
    private String repo;
    private String sha;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public static CommitDetailsFragment newInstance(String owner, String repo, String sha) {
        CommitDetailsFragment fragment = new CommitDetailsFragment();
        fragment.setOwner(owner);
        fragment.setRepo(repo);
        fragment.setSha(sha);
        return fragment;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter.setCommitSha(sha);
        mPresenter.setOwnerName(owner);
        mPresenter.setRepoName(repo);

        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public BaseRecyclerAdapter<MultiTypeAdapter.Item, RecyclerView.ViewHolder> getAdapter() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        diffStyler = new DiffStyler(getResources());
        mAdapter = new GitCommitDetailsAdapter(getActivity(), inflater, diffStyler);
        return mAdapter;
    }

    @Override
    public void showMessage(String message) {
        ViewUtils.showMessage(mRefreshLayout, message);
    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadListData(List<GitCommitFile> files) {
        assert mAdapter != null;
        GitCommitDetailsAdapter adapter = (GitCommitDetailsAdapter) this.mAdapter;
        adapter.clear();
        diffStyler.setFiles(files);
        for (GitCommitFile file : files) {
            adapter.addCommitFile(file);
        }
    }

    @Override
    public void initListItemView() {
        if (mAdapter == null) {
            mAdapter = getAdapter();
        }

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter = getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadCommitDetails();
            }
        });
    }
}
