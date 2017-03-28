package com.stormphoenix.ogit.mvp.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitOrgsAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.OrgListPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.OrgProfileActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgFragment extends ListWithPresenterFragment<GitOrganization> {

    @Inject
    public OrgListPresenter mPresenter;

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
    public BaseRecyclerAdapter<GitOrganization, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitOrgsAdapter(getActivity(), new ArrayList<GitOrganization>());
        mAdapter.addOnViewClickListener(R.id.owner_wrapper, new BaseRecyclerAdapter.OnInternalViewClickListener<GitOrganization>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitOrganization values) {
                EventBus.getDefault().postSticky(values);
                mPresenter.startOrgDetailsActivity(OrgProfileActivity.getIntent(getActivity()));
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitOrganization values) {
                return false;
            }
        });
        return mAdapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mPresenter;
    }

    public static BaseFragment getInstance() {
        return new OrgFragment();
    }
}
