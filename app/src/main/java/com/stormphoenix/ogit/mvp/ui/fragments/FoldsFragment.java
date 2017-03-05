package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitFoldersAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.RepoTreePresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.component.BreadcrumbView;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;
import com.stormphoenix.ogit.mvp.view.TreeItemView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class FoldsFragment extends ListFragment<GitTreeItem> implements TreeItemView<GitTreeItem> {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    public RepoTreePresenter mPresenter;
    private BreadcrumbTreeActivity.BreadcrumbTreeController mBreadcrumbTreeController;

    public static FoldsFragment getInstance(BreadcrumbTreeActivity.BreadcrumbTreeController breadcrumbTreeController) {
        FoldsFragment eventsFragment = new FoldsFragment();
        eventsFragment.setBreadcrumbTree(breadcrumbTreeController);

        Bundle bundle = new Bundle();
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }

    public void setBreadcrumbTree(BreadcrumbTreeActivity.BreadcrumbTreeController breadcrumbTreeController) {
        mBreadcrumbTreeController = breadcrumbTreeController;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.setOnViewClickListener(R.id.file_card_wrapper, mPresenter);
    }

    @Override
    public BaseRecyclerAdapter<GitTreeItem> getAdapter() {
        return new GitFoldersAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void addBreadcrumb(BreadcrumbView.Breadcrumb crumb) {
        mBreadcrumbTreeController.addBreadcrumb(crumb);
    }

    @Override
    public String getAbsolutPath() {
        return mBreadcrumbTreeController.getAbsolutePath();
    }
}
