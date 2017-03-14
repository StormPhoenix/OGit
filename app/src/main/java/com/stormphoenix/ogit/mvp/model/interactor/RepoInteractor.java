package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.api.CodeFileApi;
import com.stormphoenix.ogit.mvp.model.api.RepoApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */
public class RepoInteractor {
    private RepoApi repoApi = null;
    private CodeFileApi codeFileApi = null;
    private Context mContext = null;

    public RepoInteractor(Context context) {
        mContext = context;
        repoApi = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(RepoApi.class);
        codeFileApi = RetrofitCreator.getStringRetrofit(mContext).create(CodeFileApi.class);
    }

    public Observable<Response<GitRepository>> loadRepository(String url) {
        return repoApi.loadRepo(url);
    }

    public Observable<Response<List<GitBranch>>> loadRepositoryBranch(final String user, final String repository) {
        return repoApi.loadBranches(user, repository);
    }

    public Observable<Response<List<GitUser>>> loadContributors(String owner, String repository, String page) {
        return repoApi.loadContributors(owner, repository, page);
    }

    public Observable<Response<GitTree>> loadRepoTrees(String owner, String repo, String sha) {
        return repoApi.loadRepoTree(owner, repo, sha);
    }

    /**
     * 加载代码内容
     *
     * @param url
     * @return
     */
    public Observable<String> loadCodeContent(String url) {
        return codeFileApi.loadCodeContent(url);
    }

    /**
     * "Basic " + Base64.encode(username + ':' + password)
     * 可以使用 String basicAuth = Credentials.basic(username, password);
     */
    public Observable<String> loadReadmeHtml(String owner, String repo, String ref) {
        return codeFileApi.loadReadMeHtml(owner, repo, ref);
    }
}
