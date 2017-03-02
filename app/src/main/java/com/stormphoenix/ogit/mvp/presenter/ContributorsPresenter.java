package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.interactor.GitRepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */
public class ContributorsPresenter extends ListItemPresenter<GitUser> {
    private GitRepoInteractor mInteractor;
    private GitRepository mRepository;

    @Inject
    public ContributorsPresenter(Context context) {
        super(context);
        mInteractor = new GitRepoInteractor(context);
        EventBus.getDefault().register(this);
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        return mInteractor.loadContributors(mRepository.getOwner().getLogin(), mRepository.getName(), String.valueOf(page));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(GitRepository model) {
        mRepository = model;
        EventBus.getDefault().unregister(this);
    }
}
