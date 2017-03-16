package com.stormphoenix.ogit.mvp.ui.fragments;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitReposAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ReposPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17316.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * focus on displaying repositories, just pass the correct RepoPresenter to it,
 * and it can work automately.
 */

public class ReposFragment extends ListWithPresenterFragment<GitRepository> {

    private ReposPresenter presenter;

    public static final ReposFragment newInstance(ReposPresenter presenter) {
        ReposFragment reposFragment = new ReposFragment();
        reposFragment.setPresenter(presenter);
        return reposFragment;
    }

    @Override
    public void initListItemView() {
        super.initListItemView();
        mAdapter.addOnViewClickListener(R.id.repository_card_wrapper, presenter);
    }

    @Override
    public BaseRecyclerAdapter<GitRepository> getAdapter() {
        return new GitReposAdapter(getActivity(), new ArrayList<GitRepository>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    public void setPresenter(ReposPresenter presenter) {
        this.presenter = presenter;
    }
}
