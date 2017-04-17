package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerPresenter;
import com.stormphoenix.ogit.utils.NetworkUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-2.
 * StormPhoenix is a intelligent Android developer.
 */
public class FollowersPresenter extends OwnerPresenter {
    private UserInteractor mInteractor;

    public FollowersPresenter(Context context) {
        super(context);
        mInteractor = new UserInteractor(context);
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.USER_FOLLERS;
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            return super.load(page);
        }
        return mInteractor.loadFollowers(PreferenceUtils.getUsername(mContext), String.valueOf(page));
    }
}
