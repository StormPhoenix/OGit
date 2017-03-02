package com.stormphoenix.ogit.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.model.GithubService;
import com.stormphoenix.ogit.mvp.model.RetrofitUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitPersonInfoInteractor {
    private GithubService githubService = null;
    private Context mContext = null;

    public GitPersonInfoInteractor(Context context) {
        mContext = context;
        githubService = RetrofitUtils.getJsonRetrofitInstance(mContext).create(GithubService.class);
    }

    public Observable<Response<List<GitEvent>>> loadGitEvents(final String username, final int page) {
        return githubService.loadGitEvents(username, String.valueOf(page));
    }

    public Observable<Response<List<GitRepository>>> loadStarredRepository(String user, int page) {
        return githubService.starredRepository(user, String.valueOf(page));
    }

    public Observable<List<GitRepository>> loadPersonalRepository(final String user, final int page) {
        return githubService.userRepository(user, String.valueOf(page));
    }
}
