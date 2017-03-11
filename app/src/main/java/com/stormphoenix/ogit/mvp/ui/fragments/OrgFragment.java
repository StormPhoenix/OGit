package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitOrg;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitOrgsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.OrgPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgFragment extends ListFragment<GitOrg> {
    @BindView(R.id.recy_org_list)
    RecyclerView mRecyOrgList;
    @BindView(R.id.org_refresh_layout)
    SwipeRefreshLayout mOrgRefreshLayout;

    @Inject
    public OrgPresenter mPresenter;

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orgs;
    }

    @Override
    public BaseRecyclerAdapter<GitOrg> getAdapter() {
        return new GitOrgsAdapter(getActivity(), new ArrayList<GitOrg>());
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return mOrgRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyOrgList;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    public static BaseFragment getInstance() {
        return new OrgFragment();
    }
}
