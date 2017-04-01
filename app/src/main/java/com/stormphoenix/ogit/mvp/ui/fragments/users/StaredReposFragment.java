package com.stormphoenix.ogit.mvp.ui.fragments.users;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitReposAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.user.UserStaredPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */
public class StaredReposFragment extends ListWithPresenterFragment<GitRepository> {
    @Inject
    UserStaredPresenter mRepositoryPresenter;

    public static StaredReposFragment newInstance(String username) {
        StaredReposFragment staredReposFragment = new StaredReposFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, username);
        staredReposFragment.setArguments(bundle);
        return staredReposFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this.getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public BaseRecyclerAdapter<GitRepository, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitReposAdapter(getActivity(), new ArrayList<>());
        return mAdapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return mRepositoryPresenter;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.addOnViewClickListener(R.id.repository_card_wrapper, mRepositoryPresenter);
    }
}
