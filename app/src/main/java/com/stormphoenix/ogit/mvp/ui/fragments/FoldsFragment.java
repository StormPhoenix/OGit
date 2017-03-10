package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitFoldersAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.RepoTreePresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;
import com.stormphoenix.ogit.mvp.view.TreeItemView;
import com.stormphoenix.ogit.widget.BreadcrumbView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class FoldsFragment extends ListFragment<GitTreeItem> implements TreeItemView<GitTreeItem> {
    public static final String TAG = FoldsFragment.class.getSimpleName();

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
        // 设置RecyclerView的点击事件
        mAdapter.setOnViewClickListener(R.id.file_card_wrapper, createOnInternalViewClickListener());
    }

    @NonNull
    private BaseRecyclerAdapter.OnInternalViewClickListener<GitTreeItem> createOnInternalViewClickListener() {
        return new BaseRecyclerAdapter.OnInternalViewClickListener<GitTreeItem>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitTreeItem values) {
                mPresenter.setSha(values.getSha());
                // 判断 GitTreeItem 类型，确定应该进行何种操作
                Log.d(TAG, "onClick: " + values.getType());
                switch (values.getType()) {
                    case GitTreeItem.TYPE_BLOB:
                        /** 如果点击的文件是文本（Blob）类型 **/
                        Log.e(TAG, "onClick: " + getAbsolutPath() + " " + values.getPath());
                        mPresenter.startCodeActivity(values);
                        break;
                    case GitTreeItem.TYPE_TREE:
                        /** 如果点击的文件是文件（Tree）类型，则进入下一个文件夹 **/
                        // 创建新的 BreadcrumbView，并添加给TreeItemView
                        BreadcrumbTreeActivity.Breadcrumb<GitTreeItem> crumb = mPresenter.createBreadcrubm(values);
                        addBreadcrumb(crumb);
                        // 设置该文件的sha值，并更新界面
                        mPresenter.setSha(values.getSha());
                        mPresenter.loadNewlyListItem();
                        break;
                    case GitTreeItem.TYPE_COMMIT:
                        /**
                         * Do something
                         */
                        break;
                }
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitTreeItem values) {
                return false;
            }
        };
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
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void addBreadcrumb(BreadcrumbTreeActivity.Breadcrumb crumb) {
        mBreadcrumbTreeController.addBreadcrumb(crumb);
    }

    @Override
    public String getAbsolutPath() {
        return mBreadcrumbTreeController.getAbsolutePath();
    }

    public void onBreadcrumbSelect(Object attachedInfo) {
        GitTreeItem treeItem = (GitTreeItem) attachedInfo;
        mPresenter.setSha(treeItem.getSha());
        mPresenter.loadNewlyListItem();
    }
}
