package com.stormphoenix.ogit.mvp.model.api;

import com.stormphoenix.httpknife.github.GitNotification;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public interface NotificationApi {
    @GET("notifications")
    Observable<Response<List<GitNotification>>> loadOwnerNotifications();
}







