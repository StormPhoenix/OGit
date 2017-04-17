package com.stormphoenix.ogit.mvp.presenter.trend;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.stormphoenix.httpknife.github.GitTrendRepository;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.trend.TrendInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.JsoupParser;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

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
        }).compose(RxJavaCustomTransformer.defaultSchedulers()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                FileCache.saveCacheFile(getCacheType(), s);
            }
        });
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.TREND_REPOS;
    }

    @Override
    protected Observable<Response<String>> load(int page) {
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
}
