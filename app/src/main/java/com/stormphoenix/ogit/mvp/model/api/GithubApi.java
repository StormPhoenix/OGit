package com.stormphoenix.ogit.mvp.model.api;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public interface GithubApi {
    @GET
    Observable<Response<String>> loadTrendRepos(@Url String url);
}
