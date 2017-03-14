package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

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
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    protected LinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;

    protected View rootView = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    public abstract BaseRecyclerAdapter<T> getAdapter();
}
