package com.stormphoenix.ogit.mvp.ui.fragments.search;

import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitRepositoryAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.search.SearchRepoPresenter;
import com.stormphoenix.ogit.mvp.presenter.search.SearchPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 用户显示搜索得到的Repository信息
 */

public class SearchRepoFragment extends SearchFragment<GitRepository> {

    @Inject
    public SearchRepoPresenter mPresenter = null;

    public static SearchRepoFragment getInstance() {
        SearchRepoFragment fragment = new SearchRepoFragment();
        return fragment;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.addOnViewClickListener(R.id.repository_card_wrapper, new BaseRecyclerAdapter.OnInternalViewClickListener() {
            @Override
            public void onClick(View parentV, View v, Integer position, Object values) {
                EventBus.getDefault().postSticky(values);
                ActivityUtils.startActivity(getActivity(), RepositoryActivity.getIntent(getActivity()));
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, Object values) {
                return false;
            }
        });
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public BaseRecyclerAdapter<GitRepository> getAdapter() {
        return new GitRepositoryAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    protected SearchPresenter<GitRepository> getSearchPresenter() {
        return mPresenter;
    }
}
