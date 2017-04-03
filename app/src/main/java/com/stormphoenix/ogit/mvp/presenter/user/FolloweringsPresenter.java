package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerPresenter;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-2.
 * StormPhoenix is a intelligent Android developer.
 */
public class FolloweringsPresenter extends OwnerPresenter {
    private UserInteractor mInteractor;

    public FolloweringsPresenter(Context context) {
        super(context);
        mInteractor = new UserInteractor(context);
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        return mInteractor.loadFollowings(PreferenceUtils.getUsername(mContext), String.valueOf(page));
    }
}
