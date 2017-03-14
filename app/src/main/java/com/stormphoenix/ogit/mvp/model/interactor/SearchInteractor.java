package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitSearchResult;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.api.SearchApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchInteractor {
    private Context mContext;
    private SearchApi searchApi = null;

    public SearchInteractor(Context context) {
        mContext = context;
        searchApi = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(SearchApi.class);
    }

    public Observable<Response<GitSearchResult<GitRepository>>> searchRepos(String keyword, int page) {
        return searchApi.searchRepos(keyword, page);
    }

    public Observable<Response<GitSearchResult<GitRepository>>> searchReposByLang(String keyword, String lang) {
        return searchApi.searchReposByLang(keyword, lang);
    }

    public Observable<Response<GitSearchResult<GitUser>>> searchUsers(String keyword, int page) {
        return searchApi.searchUsers(keyword, page);
    }
}
