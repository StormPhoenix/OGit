package com.stormphoenix.ogit.mvp.ui.fragments.search;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.adapters.GitUserAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.search.SearchUsersPresenter;
import com.stormphoenix.ogit.mvp.presenter.search.SearchPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchUsersFragment extends SearchFragment<GitUser> {

    @Inject
    public SearchUsersPresenter mPresenter = null;

    /**
     * 搜索的关键字，从SearchActvity中获取
     */
    private String keyword;

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    public static SearchUsersFragment getInstance() {
        SearchUsersFragment fragment = new SearchUsersFragment();
        return fragment;
    }

    @Override
    protected SearchPresenter<GitUser> getSearchPresenter() {
        return mPresenter;
    }

    @Override
    public BaseRecyclerAdapter<GitUser> getAdapter() {
        return new GitUserAdapter(getActivity(), new ArrayList<>());
    }
}
