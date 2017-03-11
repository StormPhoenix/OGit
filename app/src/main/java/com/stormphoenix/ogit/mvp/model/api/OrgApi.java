package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitOrg;
import com.stormphoenix.httpknife.github.GitUser;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public interface OrgApi {
    //    @Header("Authorization")
    @Headers("Cache-Control: public, max-age=600")
    @GET("/user/orgs?per_page=10?per_page=100")
    Observable<Response<List<GitOrg>>> loadOwnerOrgs(@Query("page") int page);

    @Headers("Cache-Control: public, max-age=600")
    @GET("/users/{username}/orgs")
    Observable<List<GitOrg>> loadOwnerOrgs(@Header("Authorization") String auth,
                                           @Path("username") String username,
                                           @Query("page") int pageId);

    @Headers("Cache-Control: public, max-age=600")
    @GET("/orgs/{org}/members")
    Observable<List<GitUser>> getMembers(@Header("Authorization") String authm,
                                         @Path("org") String org,
                                         @Query("page") int pageId);
}