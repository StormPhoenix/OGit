package com.stormphoenix.ogit.mvp.presenter.search;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitSearchResult;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.presenter.search.SearchPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchUsersPresenter extends SearchPresenter<GitUser> {

    @Inject
    public SearchUsersPresenter(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<GitSearchResult<GitUser>>> load(int page) {
        if (keyword == null) {
            return null;
        }
        return mInteractor.searchUsers(keyword, page);
    }
}
