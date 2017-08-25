package com.stormphoenix.ogit.mvp.ui.fragments.repository;

import android.support.v7.widget.RecyclerView;

import com.stormphoenix.httpknife.github.GitTrendRepository;
import com.stormphoenix.ogit.adapters.GitReposAdapter2;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.trend.TrendReposPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17316.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * focus on displaying repositories, just pass the correct RepoPresenter to it,
 * and it can work automately.
 */

public class ReposListFragment2 extends ListWithPresenterFragment<GitTrendRepository> {

    private TrendReposPresenter presenter;

    public static final ReposListFragment2 newInstance(TrendReposPresenter presenter) {
        ReposListFragment2 reposListFragment = new ReposListFragment2();
        reposListFragment.setPresenter(presenter);
        return reposListFragment;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
//        mAdapter.addOnViewClickListener(R.id.repository_card_wrapper, presenter);
    }

    @Override
    public BaseRecyclerAdapter<GitTrendRepository, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitReposAdapter2(getActivity(), new ArrayList<>());
        return mAdapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    public void setPresenter(TrendReposPresenter presenter) {
        this.presenter = presenter;
    }
}
