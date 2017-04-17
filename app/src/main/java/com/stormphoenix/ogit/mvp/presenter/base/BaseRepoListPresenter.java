package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stormphoenix.httpknife.GitEventParser;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import org.greenrobot.eventbus.EventBus;

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

public abstract class BaseRepoListPresenter extends ListItemPresenter<GitRepository, List<GitRepository>, ListItemView<GitRepository>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitRepository> {
    @Override
    protected void makeDataCached(List<GitRepository> data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                // 注意Type类型
                builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                gson = builder.create();
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
    protected Observable<Response<List<GitRepository>>> load(int page) {
        Observable.create(new Observable.OnSubscribe<List<GitRepository>>() {
            @Override
            public void call(Subscriber<? super List<GitRepository>> subscriber) {
                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                // 注意Type类型
                builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                gson = builder.create();

                Type type = new TypeToken<List<GitRepository>>() {
                }.getType();
                String s = FileCache.getCachedFile(getCacheType());
                subscriber.onNext(gson.fromJson(s, type));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Action1<List<GitRepository>>() {
                    @Override
                    public void call(List<GitRepository> repositories) {
                        mView.loadNewlyListItem(repositories);
                    }
                });
        return null;
    }

    @Override
    public void onClick(View parentV, View v, Integer position, GitRepository values) {
        EventBus.getDefault().postSticky(values);
        startRepoDetailsActivity();
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitRepository values) {
        return false;
    }

    public BaseRepoListPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitRepository> transformBody(List<GitRepository> body) {
        return body;
    }

    public abstract void startRepoDetailsActivity();

    /**
     * 启动RepositoryActivity界面。
     * 讲GitRepository对象传递给此Activity的代码请参见 onItemClick 方法
     */
//    private void startRepositoryActivity() {
//        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
//    }
}
