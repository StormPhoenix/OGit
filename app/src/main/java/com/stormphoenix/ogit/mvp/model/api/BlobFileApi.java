package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitBlob;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public interface BlobFileApi {
    /**
     * 获取blob文件的内容
     *
     * @param user  拥有者
     * @param repo   仓库名字
     * @param path   blob文件所在路径
     * @param branch 仓库的分支
     * @return
     */
    @GET("repos/{user}/{repo}/contents/{path}")
    Observable<GitBlob> loadBlob(@Path("user") String user, @Path("repo") String repo, @Path("path") String path, @Query("ref") String branch);
}
