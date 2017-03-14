package com.stormphoenix.ogit.mvp.ui.fragments.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.presenter.search.SearchPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.SearchActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class SearchFragment<T> extends ListWithPresenterFragment<T> {
    /**
     * 搜索的关键字，从SearchActvity中获取
     */
    private String keyword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        assert getActivity() instanceof SearchActivity;
        keyword = ((SearchActivity) getActivity()).getSearchKeyWord();
        getSearchPresenter().setKeyword(keyword);
        getSearchPresenter().loadNewlyListItem();
        return rootView;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return getSearchPresenter();
    }

    protected abstract SearchPresenter<T> getSearchPresenter();
}
