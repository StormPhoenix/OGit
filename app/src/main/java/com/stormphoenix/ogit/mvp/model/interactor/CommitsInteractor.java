package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.ogit.mvp.model.api.CommitsApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitsInteractor {
    private Context mContext = null;
    private CommitsApi api = null;

    public CommitsInteractor(Context context) {
        mContext = context;
        api = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(CommitsApi.class);
    }

    public Observable<Response<List<GitCommit>>> loadReposCommits(String owner, String repo) {
        return api.loadRepoCommits(owner, repo);
    }
}
