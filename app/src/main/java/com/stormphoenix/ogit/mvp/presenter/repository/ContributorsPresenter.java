package com.stormphoenix.ogit.mvp.presenter.repository;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.interactor.repository.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */
public class ContributorsPresenter extends OwnerPresenter {
    private RepoInteractor mInteractor;
    private GitRepository mRepository;

    public ContributorsPresenter(Context context) {
        super(context);
        mInteractor = new RepoInteractor(context);
        EventBus.getDefault().register(this);
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        if (mRepository != null) {
            return mInteractor.loadContributors(mRepository.getOwner().getLogin(), mRepository.getName(), String.valueOf(page));
        } else {
            return null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(GitRepository model) {
        mRepository = model;
        EventBus.getDefault().unregister(this);
    }
}
