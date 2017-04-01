package com.stormphoenix.ogit.mvp.ui.fragments.repository;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitFoldersAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.repository.RepoTreePresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;
import com.stormphoenix.ogit.mvp.view.TreeItemView;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class FoldsFragment extends ListWithPresenterFragment<GitTreeItem> implements TreeItemView<GitTreeItem> {
    public static final String TAG = FoldsFragment.class.getSimpleName();

    private RepoTreePresenter mPresenter = null;

    public static FoldsFragment newInstance(RepoTreePresenter presenter) {
        FoldsFragment eventsFragment = new FoldsFragment();
        eventsFragment.setRepoTreePresenter(presenter);
        Bundle bundle = new Bundle();
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }

    public void setRepoTreePresenter(RepoTreePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        // 设置RecyclerView的点击事件
        mAdapter.addOnViewClickListener(R.id.file_card_wrapper, createOnInternalViewClickListener());
    }

    /**
     * 设置文件夹的点击事件
     *
     * @return
     */
    @NonNull
    private BaseRecyclerAdapter.OnInternalViewClickListener<GitTreeItem> createOnInternalViewClickListener() {
        return new BaseRecyclerAdapter.OnInternalViewClickListener<GitTreeItem>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitTreeItem values) {
                // 判断 GitTreeItem 类型，确定应该进行何种操作
                Log.d(TAG, "onClick: " + values.getType());
                switch (values.getType()) {
                    case GitTreeItem.TYPE_BLOB:
                        /** 如果点击的文件是文本（Blob）类型 **/
                        mPresenter.startCodeActivity(values);
                        break;
                    case GitTreeItem.TYPE_TREE:
                        /** 如果点击的文件是文件（Tree）类型，则进入下一个文件夹 **/
                        // 设置该文件的sha值，并更新界面
                        mPresenter.intoNewBreadcrum(values);
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
    public BaseRecyclerAdapter<GitTreeItem, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitFoldersAdapter(getActivity(), new ArrayList<>());
        return mAdapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    @Override
    public void initializeInjector() {
        // Do nothing
    }
}
