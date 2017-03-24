package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 *
 * UserApi 主要用于获取用户个人信息，接收的事件等信息
 */

public interface UserApi {
    /**
     * 加载用户信息
     *
     * @param user 用户名字
     * @return
     */
    @GET("/users/{user}")
    Observable<Response<GitUser>> loadUser(@Path("user") String user);

    @GET("users/{user}/received_events?per_page=10")
    Observable<Response<List<GitEvent>>> loadGitEvents(@Path("user") String user, @Query("page") String page);

    @GET("users/{user}/repos?sort=pushed&per_page=10")
    Observable<List<GitRepository>> userRepository(@Path("user") String user, @Query("page") String page);

    /**
     * 获取用户Starred的项目，每次10个
     *
     * @param user
     * @param page
     * @return
     */
    @GET("users/{user}/starred?per_page=10")
    Observable<Response<List<GitRepository>>> starredRepository(@Path("user") String user, @Query("page") String page);

    @GET("repos/{owner}/{repo}/stargazers?&per_page=10")
    Observable<List<GitUser>> stargazers(@Path("owner") String owner, @Path("repo") String repository, @Query("page") String page);

    @GET("/users/{user}/starred?per_page=1")
    Observable<Response<List<GitRepository>>> loadStaredCount(@Path("user") String user);

    @GET("/user/following/{user}")
    Observable<Response<GitEmpty>> hasFollow(@Path("user") String user);

    @PUT("/user/following/{user}")
    Observable<Response<GitEmpty>> follow(@Path("user") String user);

    @DELETE("/user/following/{user}")
    Observable<Response<GitEmpty>> unFollow(@Path("user") String user);
}
