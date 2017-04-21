package com.stormphoenix.ogit.mvp.presenter.trend;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stormphoenix.httpknife.GitEventParser;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitTrendRepository;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.trend.TrendInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.JsoupParser;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.utils.NetworkUtils;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by StormPhoenix on 17-4-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class TrendReposPresenter extends ListItemPresenter<GitTrendRepository, String, ListItemView<GitTrendRepository>> {
    public static final int TREND_TYPE_REPOSITORY = 0;
    public static final int TREND_TYPE_DEVELOPER = 1;

    private int trendType;
    private String languageType;

    public int getTrendType() {
        return trendType;
    }

    public void setTrendType(int trendType) {
        this.trendType = trendType;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    private TrendInteractor trendInteractor;

    public TrendReposPresenter(Context context) {
        super(context);
        trendInteractor = new TrendInteractor(mContext);
    }

    @Override
    protected List<GitTrendRepository> transformBody(String body) {
        return JsoupParser.parseTrendRepositories(body);
    }

    @Override
    protected void makeDataCached(List<GitTrendRepository> data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Gson gson = new Gson();
                subscriber.onNext(gson.toJson(data));
            }
        }).compose(RxJavaCustomTransformer.ioSchedulers()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                FileCache.saveCacheFile(getCacheType(), s);
            }
        });
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        switch (trendType) {
            case TREND_TYPE_REPOSITORY:
                if (languageType == null) {
                    return FileCache.CacheType.TREND_REPOS_All_Lang;
                }
                switch (languageType) {
                    case "All Language":
                        return FileCache.CacheType.TREND_REPOS_All_Lang;
                    case "JavaScript":
                        return FileCache.CacheType.TREND_REPOS_JavaScript;
                    case "Java":
                        return FileCache.CacheType.TREND_REPOS_Java;
                    case "Go":
                        return FileCache.CacheType.TREND_REPOS_Go;
                    case "CSS":
                        return FileCache.CacheType.TREND_REPOS_Css;
                    case "Objective-C":
                        return FileCache.CacheType.TREND_REPOS_Objective_C;
                    case "Python":
                        return FileCache.CacheType.TREND_REPOS_Python;
                    case "Swift":
                        return FileCache.CacheType.TREND_REPOS_Swift;
                    case "HTML":
                        return FileCache.CacheType.TREND_REPOS_Html;
                    default:
                        return null;
                }
            case TREND_TYPE_DEVELOPER:
                return FileCache.CacheType.TREND_DEVELOPER;
            default:
                return null;
        }
    }

    @Override
    protected Observable<Response<String>> load(int page) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            loadOnNetworkError();
            return null;
        }
        return loadOnNetworkNormal();
    }

    private Observable<Response<String>> loadOnNetworkNormal() {
        String url = "https://github.com/trending";
        switch (trendType) {
            case TREND_TYPE_DEVELOPER:
                url += "/developers";
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(languageType)) {
            url += ("/" + languageType);
        }
        return trendInteractor.loadTrendRepos(url);
    }

    private void loadOnNetworkError() {
        Observable.create(new Observable.OnSubscribe<List<GitTrendRepository>>() {
            @Override
            public void call(Subscriber<? super List<GitTrendRepository>> subscriber) {
                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                // 注意Type类型
                builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                gson = builder.create();

                Type type = new TypeToken<List<GitTrendRepository>>() {
                }.getType();
                String s = FileCache.getCachedFile(getCacheType());
                subscriber.onNext(gson.fromJson(s, type));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Action1<List<GitTrendRepository>>() {
                    @Override
                    public void call(List<GitTrendRepository> repositories) {
                        mView.loadNewlyListItem(repositories);
                    }
                });
    }
}
