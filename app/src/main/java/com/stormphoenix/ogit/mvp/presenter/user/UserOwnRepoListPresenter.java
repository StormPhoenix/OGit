package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.repository.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BaseRepoListPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.NetworkUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserOwnRepoListPresenter extends BaseRepoListPresenter {
    private RepoInteractor mInteractor;

    public UserOwnRepoListPresenter(Context context) {
        super(context);
        mInteractor = new RepoInteractor(context);
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.USER_REPOSITORIES;
    }

    @Override
    public void startRepoDetailsActivity() {
        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
    }

    @Override
    protected Observable<Response<List<GitRepository>>> load(int page) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            return super.load(page);
        }
        return mInteractor.loadOwnerRepos();
    }
}
