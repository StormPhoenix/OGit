package com.stormphoenix.ogit.mvp.presenter.trend;

import android.content.Context;
import android.text.TextUtils;

import com.stormphoenix.httpknife.github.GitTrendRepository;
import com.stormphoenix.ogit.mvp.model.interactor.trend.TrendInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.JsoupParser;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

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
