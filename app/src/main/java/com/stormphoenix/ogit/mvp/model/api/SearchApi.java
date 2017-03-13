package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitRepoSearch;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 搜索功能
 */

public interface SearchApi {
    @GET("search/repositories?per_page=10")
    Observable<Response<GitRepoSearch>> searchRepos(@Query("q") String keyword,@Query("page")int page);

    //    https://api.github.com/search/repositories?q=imageselector+language:python&sort=stars&order=desc
    @GET("search/repositories?q={keyword}+language:{language}&sort={sort}&order={order}")
    Observable<Response<GitRepoSearch>> searchReposByLang(@Part("keyword") String keyword,
                                                          @Path("language") String language,
                                                          @Path("sort") String sort,
                                                          @Path("order") String order);

}
