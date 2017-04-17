package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BaseRepoListPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.NetworkUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserStaredRepoListPresenter extends BaseRepoListPresenter {
    private UserInteractor mInfoInfoInteractor;

    public UserStaredRepoListPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new UserInteractor(context);
    }

    @Override
    protected List<GitRepository> transformBody(List<GitRepository> body) {
        return body;
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.USER_STARED_REPOS;
    }

    @Override
    protected Observable<Response<List<GitRepository>>> load(int page) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            return super.load(page);
        }
        return mInfoInfoInteractor.loadStarredRepository(PreferenceUtils.getUsername(mContext), page);
    }

    /**
     * 启动RepositoryActivity界面。
     * 讲GitRepository对象传递给此Activity的代码请参见 onItemClick 方法
     */
    @Override
    public void startRepoDetailsActivity() {
        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
    }
}
