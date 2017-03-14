package com.stormphoenix.ogit.mvp.presenter.search;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitSearchResult;
import com.stormphoenix.ogit.mvp.model.interactor.SearchInteractor;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 专门用于搜索的 Presenter的基类，搜索的关键子将会被传入 {@link #keyword}，
 * 子类只需要覆盖写 {@link #load(int)}方法即可
 */

public abstract class SearchPresenter<T> extends ListItemPresenter<T, GitSearchResult<T>, ListItemView<T>> {
    protected SearchInteractor mInteractor;
    protected String keyword;

    public SearchPresenter(Context context) {
        super(context);
        mInteractor = new SearchInteractor(context);
    }

    @Override
    protected List<T> transformBody(GitSearchResult<T> body) {
        return body.getItems();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
