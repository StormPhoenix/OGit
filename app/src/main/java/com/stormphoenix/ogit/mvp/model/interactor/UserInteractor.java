package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.api.UserApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserInteractor {
    private UserApi userApi = null;
    private Context mContext = null;

    public UserInteractor(Context context) {
        mContext = context;
        userApi = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(UserApi.class);
    }

    /**
     * 加载接受到的事件
     *
     * @param username
     * @param page
     * @return
     */
    public Observable<Response<List<GitEvent>>> loadReceiveEvents(final String username, final int page) {
        return userApi.loadGitEvents(username, String.valueOf(page));
    }

    public Observable<Response<List<GitRepository>>> loadStarredRepository(String user, int page) {
        return userApi.starredRepository(user, String.valueOf(page));
    }

    public Observable<List<GitRepository>> loadUserRepository(final String user, final int page) {
        return userApi.userRepository(user, String.valueOf(page));
    }

    public Observable<Response<GitUser>> loadUser(final String user) {
        return userApi.loadUser(user);
    }

    public Observable<Response<List<GitRepository>>> loadStaredCount(String user) {
        return userApi.loadStaredCount(user);
    }
}
