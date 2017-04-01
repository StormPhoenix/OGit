package com.stormphoenix.ogit.mvp.model.interactor.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.mvp.model.api.NotificationApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class NotifyInteractor {
    private NotificationApi api;
    private Context mContext;

    public NotifyInteractor(Context context) {
        mContext = context;
        api = RetrofitCreator.getJsonRetrofitWithToken(context).create(NotificationApi.class);
    }

    public Observable<Response<List<GitNotification>>> loadNotification() {
        return api.loadOwnerNotifications();
    }
}



