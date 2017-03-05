package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitBlob;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * GithubCodeService 获取文件内容
 * BASE_URL http://raw.githubusercontent.com/
 */

public interface GithubCodeService {
     @GET
    Observable<String> loadCodeContent(@Url String url);
}
