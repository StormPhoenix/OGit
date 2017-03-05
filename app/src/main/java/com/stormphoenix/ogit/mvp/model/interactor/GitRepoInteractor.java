package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.api.GithubCodeService;
import com.stormphoenix.ogit.mvp.model.api.GithubService;
import com.stormphoenix.ogit.mvp.model.rx.RetrofitUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */
public class GitRepoInteractor {
    private GithubService repoService = null;
    private GithubCodeService blobService = null;
    private Context mContext = null;

    public GitRepoInteractor(Context context) {
        mContext = context;
        repoService = RetrofitUtils.getJsonRetrofitInstance(mContext).create(GithubService.class);
        blobService = RetrofitUtils.getStringRetrofitInstance(mContext).create(GithubCodeService.class);
    }

    public Observable<Response<List<GitBranch>>> loadRepositoryBrances(final String user, final String repository) {
        return repoService.loadBranches(user, repository);
    }

    public Observable<Response<List<GitUser>>> loadContributors(String owner, String repository, String page) {
        return repoService.loadContributors(owner, repository, page);
    }

    public Observable<Response<GitTree>> loadRepoTrees(String owner, String repo, String sha) {
        return repoService.loadRepoTree(owner, repo, sha);
    }

    public Observable<GitBlob> loadGitBlob(String owner, String repo, String path, String brance) {
        return repoService.loadGitBlob(owner, repo, path, brance);
    }

    public Observable<String> loadCodeContent(String url) {
        return blobService.loadCodeContent(url);
    }
}
