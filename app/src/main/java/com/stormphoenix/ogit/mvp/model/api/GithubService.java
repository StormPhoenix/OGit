package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitToken;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitUser;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public interface GithubService {
    @POST("authorizations")
    Observable<Response<GitToken>> createToken(@Body GitToken token, @Header("Authorization") String authorization);

    @PUT("authorization/clients/{clientId}")
    Observable<Response<GitToken>> getOrCreateToken(@Body GitToken token, @Path("clientId") String clientId, @Header("Authorization") String authorization);

    @GET("authorizations")
    Observable<Response<List<GitToken>>> listToken(@Header("Authorization") String authorization);

    @DELETE("authorizations/{id}")
    Observable<Response<GitEmpty>> removeToken(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("users/{user}/received_events?per_page=10")
    Observable<Response<List<GitEvent>>> loadGitEvents(@Path("user") String user, @Query("page") String page);

    @GET("users/{user}/repos?sort=pushed&per_page=10")
    Observable<List<GitRepository>> userRepository(@Path("user") String user, @Query("page") String page);

    @GET("users/{user}/starred?per_page=10")
    Observable<Response<List<GitRepository>>> starredRepository(@Path("user") String user, @Query("page") String page);

    @GET("repos/{owner}/{repo}/stargazers?&per_page=10")
    Observable<List<GitUser>> stargazers(@Path("owner") String owner, @Path("repo") String repository, @Query("page") String page);

    /**
     * 获取项目分支
     *
     * @param owner
     * @param repo
     * @return
     */
    @GET("/repos/{owner}/{repo}/branches")
    Observable<Response<List<GitBranch>>> loadBranches(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors?&per_page=10")
    Observable<Response<List<GitUser>>> loadContributors(@Path("owner") String owner, @Path("repo") String repository, @Query("page") String page);

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    Observable<Response<GitTree>> loadRepoTree(@Path("owner") String owner, @Path("repo") String repo, @Path("sha") String sha);

    @GET("/repos/{owner}/{repo}/contents/{path}")
    Observable<Response<GitTree>> loadRepoBlob(@Path("owner") String owner, @Path("repo") String repo, @Path("path") String path);

    /**
     * 获取blob文件的内容
     *
     * @param owner  拥有者
     * @param repo   仓库名字
     * @param path   blob文件所在路径
     * @param branch 仓库的分支
     * @return
     */
    @GET("/repos/{owner}/{repo}/contents/{path}")
    Observable<GitBlob> loadGitBlob(@Path("owner") String owner,
                                              @Path("repo") String repo,
                                              @Path("path") String path,
                                              @Query("ref") String branch);


//    Observable<Response<GitUser>> authUser();

//    @GET("/users/{username}")
//    Observable<Response<GitUser>> user(@Path("username") String username);
//
//    @GET("/users/{user}/following?per_page=10")
//    Observable<Response<List<GitUser>>> follwerings(@Path("user") String user, @Query("page") String page);
//
//    @GET("/users/{user}/followers?per_page=10")
//    Observable<Response<List<GitUser>>> followers(@Path("user") String user, @Query("page") String page);
//
//    @GET("/users/{user}/events/public?per_page=10")
//    Call<List<GitEvent>> publicEvent(@Path("user") String user, @Query("page") String page);
//
//    @GET("/user/starred/{owner}/{repo}")
//    Call<GitEmpty> hasStar(@Path("owner") String owner, @Path("repo") String repo);

//    @PUT("/user/starred/{owner}/{repo}")
//    Call<Empty> star(@Path("owner") String owner, @Path("repo") String repo);
//
//    @DELETE("/user/starred/{owner}/{repo}")
//    Call<Empty> unStar(@Path("owner") String owner, @Path("repo") String repo);
//
//    //Get count of starred repo of someone
//    @GET("/users/{user}/starred?&per_page=1")
//    Observable<Response<List<Repository>>> starredCount(@Path("user") String user);
//
//    //Api about repo
//
//    @GET("/repos/{owner}/{repo}/forks?&per_page=10")
//    Call<List<User>> forkers(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);
//
//    @GET("/repos/{owner}/{repo}/collaborators?&per_page=10")
//    Call<List<User>> collaborators(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);
//
//    @GET("/repos/{owner}/{repo}/forks")
//    Call<List<Repository>> fork(@Path("owner") String owner, @Path("repo") String repo);
//
//    @GET("/repos/{owner}/{repo}/contents/{path}")
//    Call<String> getRawContent(@Path("owner") String owner, @Path("repo") String repo,@Path("path") String path);
//
//    @GET("/repos/{owner}/{repo}")
//    Call<Repository> repo(@Path("owner") String owner, @Path("repo") String repo);
//
//    //Api about search
//    @GET("/search/users?&perpage=10")
//    Call<UserSearch> searchUser(@Query("q") String q, @Query("page") String page);
//
//    @GET("/search/repositories?&per_page=10")
//    Call<RepoSearch> searchRepo(@Query("q") String q, @Query("page") String page);
//
//    //==========
//    //Api about user-user relation
//    //==========
//
//    @GET("/user/following/{username}")
//    Observable<Response<Empty>> hasFollow(@Path("username") String username);
//
//    @PUT("/user/following/{username}")
//    Observable<Response<Empty>> follow(@Path("username") String username);
//
//    @DELETE("/user/following/{username}")
//    Observable<Response<Empty>> unFollow(@Path("username") String username);
}
