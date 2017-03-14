package com.stormphoenix.ogit.mvp.ui.fragments;

import com.stormphoenix.httpknife.github.GitOrg;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitOrgsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.list.OrgPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgFragment extends ListWithPresenterFragment<GitOrg> {

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
        return R.layout.fragment_refresh_recyclerview;
    }

    @Override
    public BaseRecyclerAdapter<GitOrg> getAdapter() {
        return new GitOrgsAdapter(getActivity(), new ArrayList<GitOrg>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    public static BaseFragment getInstance() {
        return new OrgFragment();
    }
}
