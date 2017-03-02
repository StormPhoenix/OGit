package com.stormphoenix.ogit.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitToken;
import com.stormphoenix.httpknife.http.Base64;
import com.stormphoenix.ogit.mvp.model.GithubService;
import com.stormphoenix.ogit.mvp.model.RetrofitUtils;
import com.stormphoenix.ogit.shares.PreferenceUtils;
import com.stormphoenix.ogit.shares.RxJavaCustomTransformer;

import java.util.Arrays;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitTokenInteractor {
    public final static String TOKEN_NOTE = "OGit Token";
    public final static String[] SCOPES = {"public_repo", "repo", "user", "gist"};

    private GithubService githubService = null;

    private Context mContext = null;

    public GitTokenInteractor(Context mContext) {
        githubService = RetrofitUtils.getRetrofitWithoutToken(mContext).create(GithubService.class);
    }

    public Observable<Response<GitToken>> getOrCreateGitToken(String username, String password) {
        GitToken token = new GitToken();
        token.setClientSecret(PreferenceUtils.CLIENT_SECRET);
        token.setNote(TOKEN_NOTE);
        token.setScopes(Arrays.asList(SCOPES));
        return githubService.getOrCreateToken(token, PreferenceUtils.CLIENT_ID, "Basic " + Base64.encode(username + ':' + password));
    }

    public Observable<Response<List<GitToken>>> listToken(String username, String password) {
        return githubService.listToken("Basic " + Base64.encode(username + ':' + password));
    }

    public Observable<Response<GitEmpty>> removeToken(String username, String password, String id) {
        return githubService.removeToken("Basic " + Base64.encode(username + ':' + password), id);
    }

    public Observable<Response<GitToken>> createGitToken(String username, String password) {
        GitToken token = new GitToken();
        token.setNote(TOKEN_NOTE);
        token.setScopes(Arrays.asList(SCOPES));
        return githubService.createToken(token, "Basic " + Base64.encode(username + ':' + password));
    }

    public Observable<Response<GitEmpty>> listAndRemoveToken(final String username, final String password) {
        return listToken(username, password)
                .flatMap(new Func1<Response<List<GitToken>>, Observable<Response<GitEmpty>>>() {
                    @Override
                    public Observable<Response<GitEmpty>> call(Response<List<GitToken>> listResponse) {
                        for (GitToken token : listResponse.body()) {
                            if (token.getNote().equals(TOKEN_NOTE)) {
                                return removeToken(username, password, String.valueOf(token.getId()));
                            }
                        }
                        return Observable.empty();
                    }
                });
    }
}
