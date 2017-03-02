package com.stormphoenix.ogit.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.GithubService;
import com.stormphoenix.ogit.mvp.model.RetrofitUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */
public class GitRepoInteractor {
    private GithubService githubService = null;
    private Context mContext = null;

    public GitRepoInteractor(Context context) {
        mContext = context;
        githubService = RetrofitUtils.getJsonRetrofitInstance(mContext).create(GithubService.class);
    }

    public Observable<Response<List<GitBranch>>> loadRepositoryBrances(final String user, final String repository) {
        return githubService.loadBranches(user, repository);
    }

    public Observable<Response<List<GitUser>>> loadContributors(String owner, String repository, String page) {
        return githubService.loadContributors(owner, repository, page);
    }
}
