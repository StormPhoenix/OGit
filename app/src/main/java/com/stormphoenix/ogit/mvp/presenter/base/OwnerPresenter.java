package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stormphoenix.httpknife.GitEventParser;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by StormPhoenix on 17-3-21.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class OwnerPresenter extends ListItemPresenter<GitUser, List<GitUser>, ListItemView<GitUser>> {
    public OwnerPresenter(Context context) {
        super(context);
    }

    @Override
    protected void makeDataCached(List<GitUser> data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Gson gson = new Gson();
                subscriber.onNext(gson.toJson(data));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                FileCache.saveCacheFile(getCacheType(), s);
            }
        });
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        Observable.create(new Observable.OnSubscribe<List<GitUser>>() {
            @Override
            public void call(Subscriber<? super List<GitUser>> subscriber) {
                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                // 注意Type类型
                builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                gson = builder.create();

                Type type = new TypeToken<List<GitUser>>() {
                }.getType();
                String s = FileCache.getCachedFile(getCacheType());
                subscriber.onNext(gson.fromJson(s, type));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Action1<List<GitUser>>() {
                    @Override
                    public void call(List<GitUser> gitEvents) {
                        mView.loadNewlyListItem(gitEvents);
                    }
                });
        return null;
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }
}
