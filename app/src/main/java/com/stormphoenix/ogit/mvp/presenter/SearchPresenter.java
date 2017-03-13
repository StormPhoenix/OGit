package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepoSearch;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.model.interactor.SearchInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchPresenter extends ListItemPresenter<GitRepository, GitRepoSearch, ListItemView<GitRepository>> {
    private SearchInteractor mInteractor;
    private String keyword;

    public SearchPresenter(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        mInteractor = new SearchInteractor(context);
    }

    @Override
    protected List<GitRepository> transformBody(GitRepoSearch body) {
        return body.getItems();
    }

    @Override
    protected Observable<Response<GitRepoSearch>> load(int page) {
        if (keyword == null) {
            return null;
        }
        return mInteractor.searchRepos(keyword, page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(String keyword) {
        this.keyword = keyword;
        EventBus.getDefault().unregister(this);
    }
}
