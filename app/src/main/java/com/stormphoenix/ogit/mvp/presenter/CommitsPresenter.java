package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.model.interactor.CommitsInteractor;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitsPresenter extends ListItemPresenter<GitCommit, List<GitCommit>, ListItemView<GitCommit>> {

    private GitRepository repository;
    private CommitsInteractor interactor;

    @Inject
    public CommitsPresenter(Context context) {
        super(context);
        interactor = new CommitsInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(GitRepository repository) {
        this.repository = repository;
        EventBus.getDefault().unregister(this);
        loadNewlyListItem();
    }

    @Override
    protected List<GitCommit> transformBody(List<GitCommit> body) {
        return body;
    }

    @Override
    protected Observable<Response<List<GitCommit>>> load(int page) {
        if (repository == null) {
            return null;
        }
        return interactor.loadReposCommits(repository.getOwner().getLogin(), repository.getName());
    }
}
