package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.LoginActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class ListFragment<T> extends BaseFragment implements ListItemView<T> {
    protected LinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;

    protected View rootView = null;

    @Override
    public void loadMoreListItem(List<T> listItems) {
        mAdapter.addAll(listItems);
    }

    @Override
    public void startRefresh() {
        getRefreshLayout().setRefreshing(true);
    }

    public abstract BaseRecyclerAdapter<T> getAdapter();

    public abstract SwipeRefreshLayout getRefreshLayout();

    public abstract RecyclerView getRecyclerView();

    public abstract ListItemPresenter getListItemPresetner();

    @Override
    public void stopRefresh() {
        getRefreshLayout().setRefreshing(false);
    }

    @Override
    public void initListItemView() {
        if (mAdapter == null) {
            mAdapter = getAdapter();
        }

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        if (getRecyclerView() != null) {
            getRecyclerView().setLayoutManager(mLayoutManager);
            getRecyclerView().setHasFixedSize(true);
            getRecyclerView().setAdapter(mAdapter);
        }
    }

    @Override
    public int getListItemCounts() {
        return mAdapter.getItemCount();
    }

    /**
     * 加载最新数据列表
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
        getListItemPresetner().onAttachView(this);
        getListItemPresetner().onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void initRefreshLayout() {
        getRefreshLayout().setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListItemPresetner().loadNewlyListItem();
            }
        });

        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    getRefreshLayout().setRefreshing(true);
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
        getActivity().startActivity(LoginActivity.getInstance(getActivity()));
        getActivity().finish();
    }
}
