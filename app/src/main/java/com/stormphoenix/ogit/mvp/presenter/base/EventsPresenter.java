package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stormphoenix.httpknife.GitEventParser;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class EventsPresenter extends ListItemPresenter<GitEvent, List<GitEvent>, ListItemView<GitEvent>> {
    public EventsPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitEvent> transformBody(List<GitEvent> body) {
        return body;
    }

    @Override
    protected Observable<Response<List<GitEvent>>> load(int page) {
        Observable.create(new Observable.OnSubscribe<List<GitEvent>>() {
            @Override
            public void call(Subscriber<? super List<GitEvent>> subscriber) {
                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                // 注意Type类型
                builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                gson = builder.create();

                Type type = new TypeToken<List<GitEvent>>() {
                }.getType();
                String s = FileCache.getCachedFile(getCacheType());
                subscriber.onNext(gson.fromJson(s, type));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Action1<List<GitEvent>>() {
                    @Override
                    public void call(List<GitEvent> gitEvents) {
                        mView.loadNewlyListItem(gitEvents);
                    }
                });
        return null;
    }

    public abstract void startOwnerProfileActivity();


    @Override
    protected void makeDataCached(List<GitEvent> data) {
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

    public abstract void startRepoDetailsActivity();
}
