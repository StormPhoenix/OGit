package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class ReposPresenter extends ListItemPresenter<GitRepository, List<GitRepository>, ListItemView<GitRepository>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitRepository> {

    @Override
    public void onClick(View parentV, View v, Integer position, GitRepository values) {
        EventBus.getDefault().postSticky(values);
        startRepoDetailsActivity();
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitRepository values) {
        return false;
    }

    public ReposPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitRepository> transformBody(List<GitRepository> body) {
        return body;
    }

    public abstract void startRepoDetailsActivity();
}
