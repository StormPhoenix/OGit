package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitBlob;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public interface GithubBlobService {
    @GET("repos/{owner}/{repo}/contents/{path}")
    Observable<GitBlob> loadGitBlob(@Path("owner") String owner, @Path("repo") String repo, @Path("path") String path, @Query("ref") String branch);
}
