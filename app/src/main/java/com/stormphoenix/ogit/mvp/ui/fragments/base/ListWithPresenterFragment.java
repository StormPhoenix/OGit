package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.LoginActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 *
 * @author StormPhoenix
 *         <p>
 *         ListFragment + ListItemPresenter
 *         Usage:
 *         覆写{@link #getAdapter()} 获取RecyclerView的适配器
 *         覆写{@link #getListItemPresetner()} 获取逻辑交互的Presenter
 */
public abstract class ListWithPresenterFragment<T> extends ListFragment<T> implements ListItemView<T> {
    @Override
    public void initializeInjector() {
    }

    @Override
    public void clearAllItems() {
        mAdapter.setData(new ArrayList<T>());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreListItem(List<T> listItems) {
        mAdapter.addAll(listItems);
        mAdapter.notifyDataSetChanged();
    }

    public abstract ListItemPresenter getListItemPresetner();

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    public void initViews() {
        initListItemView();
        initRefreshLayout();
    }

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
    }

    @Override
    public int getListItemCounts() {
        return mAdapter.getItemCount();
    }

    /**
     * 加载最新数据列表
     *
     * @param listItems
     */
    @Override
    public void loadNewlyListItem(List<T> listItems) {
        mAdapter.setData(listItems);
        mAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initViews();
        getListItemPresetner().onAttachView(this);
        getListItemPresetner().onCreate(savedInstanceState);
        return rootView;
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(() -> getListItemPresetner().loadNewlyListItem());

        if (mScrollListener != null) {
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mRefreshLayout.setRefreshing(true);
                    getListItemPresetner().loadMoreListItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void reLogin() {
        PreferenceUtils.putBoolean(getActivity(), PreferenceUtils.IS_LOGIN, false);
        ActivityUtils.startActivity(getActivity(), LoginActivity.newIntent(getActivity()));
        getActivity().finish();
    }
}
