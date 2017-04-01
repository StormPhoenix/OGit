package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.model.interactor.repository.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.repository.ReposPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserReposPresenter extends ReposPresenter {
    private RepoInteractor mInteractor;

    public UserReposPresenter(Context context) {
        super(context);
        mInteractor = new RepoInteractor(context);
    }

    @Override
    public void startRepoDetailsActivity() {
        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
    }

    @Override
    protected Observable<Response<List<GitRepository>>> load(int page) {
        return mInteractor.loadOwnerRepos();
    }
}
