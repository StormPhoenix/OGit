package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 *
 * @author StormPhoenix
 *         <p>
 *         SwipeRefreshLayout + RecyclerView + Fragment样式模板
 *         Usage:
 *         覆写{@link #getAdapter()} 获取RecyclerView的适配器
 */
public abstract class ListFragment<T> extends BaseFragment {
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    protected LinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;

    protected View rootView = null;
    protected RecyclerView.OnScrollListener mScrollListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public abstract BaseRecyclerAdapter<T, RecyclerView.ViewHolder> getAdapter();
}
