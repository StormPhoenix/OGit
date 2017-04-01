package com.stormphoenix.ogit.mvp.ui.fragments.commits;

import android.support.v7.widget.RecyclerView;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.adapters.commits.GitCommitsAdapter;
import com.stormphoenix.ogit.mvp.presenter.commits.CommitsPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitsFragment extends ListWithPresenterFragment<GitCommit> {
    private CommitsPresenter presenter = null;

    public static CommitsFragment newInstance(CommitsPresenter presenter) {
        CommitsFragment fragment = new CommitsFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public BaseRecyclerAdapter<GitCommit, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitCommitsAdapter(getActivity(), new ArrayList<>());
        mAdapter.addOnViewClickListener(R.id.text_commit_info, presenter);
        return mAdapter;
    }

    @Override
    public void loadNewlyListItem(List<GitCommit> listItems) {
        super.loadNewlyListItem(listItems);
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    public void setPresenter(CommitsPresenter presenter) {
        this.presenter = presenter;
    }
}
