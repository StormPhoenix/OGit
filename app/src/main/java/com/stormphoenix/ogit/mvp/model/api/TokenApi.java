package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitToken;
import com.stormphoenix.httpknife.github.GitUser;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public interface TokenApi {
    /**
     * 利用用户名和密码生成 Token
     *
     * @param token
     * @param authorization
     * @return
     */
    @POST("authorizations")
    Observable<Response<GitToken>> createToken(@Body GitToken token, @Header("Authorization") String authorization);

    @PUT("authorization/clients/{clientId}")
    Observable<Response<GitToken>> getOrCreateToken(@Body GitToken token, @Path("clientId") String clientId, @Header("Authorization") String authorization);

    /**
     * 列出该用户在Github服务端所有的Token
     *
     * @param authorization
     * @return
     */
    @GET("authorizations")
    Observable<Response<List<GitToken>>> listToken(@Header("Authorization") String authorization);

    /**
     * 删除该用户某一个Token
     *
     * @param authorization
     * @param id
     * @return
     */
    @DELETE("authorizations/{id}")
    Observable<Response<GitEmpty>> removeToken(@Header("Authorization") String authorization, @Path("id") String id);

    /**
     * 加载用户信息
     *
     * @param user 用户名字
     * @return
     */
    @GET("/users/{user}")
    Observable<Response<GitUser>> loadUser(@Path("user") String user);
}
