package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitCommit;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public interface CommitsApi {
    /**
     * 获取一个仓库中所有提交的信息
     *
     * @param owner
     * @param repo
     * @return
     */
    @GET("repos/{owner}/{repo}/commits")
    Observable<Response<List<GitCommit>>> loadRepoCommits(@Path("owner") String owner, @Path("repo") String repo);

    /**
     * 获取某个提交的详细信息
     *
     * @param owner
     * @param repo
     * @param sha
     * @return
     */
    @GET("repos/{owner}/{repo}/commits/{sha}")
    Observable<Response<GitCommit>> loadSingleCommit(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("sha") String sha
    );
}
