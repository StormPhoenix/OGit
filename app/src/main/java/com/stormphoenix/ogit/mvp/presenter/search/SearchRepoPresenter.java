package com.stormphoenix.ogit.mvp.presenter.search;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitSearchResult;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.presenter.search.SearchPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchRepoPresenter extends SearchPresenter<GitRepository> {
    @Inject
    public SearchRepoPresenter(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<GitSearchResult<GitRepository>>> load(int page) {
        if (keyword == null) {
            return null;
        }
        return mInteractor.searchRepos(keyword, page);
    }
}
