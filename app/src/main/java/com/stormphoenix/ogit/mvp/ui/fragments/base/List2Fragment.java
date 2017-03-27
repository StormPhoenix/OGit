package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.stormphoenix.ogit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 *
 * @author StormPhoenix
 *         <p>
 *         SwipeRefreshLayout + ListView + Fragment样式模板
 *         Usage:
 *         覆写{@link #getAdapter()} 获取RecyclerView的适配器
 */
public abstract class List2Fragment extends BaseFragment {
    @BindView(R.id.list_view)
    protected ListView mListView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    protected BaseAdapter mAdapter;

    protected View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_listview;
    }

    public abstract BaseAdapter getAdapter();
}
