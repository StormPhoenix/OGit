package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.mvp.model.interactor.GitPersonInfoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.PreferenceUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsPresenter extends ListItemPresenter<GitEvent, ListItemView<GitEvent>> {
    /**
     * Interactor 用于提交网络请求获取数据
     **/
    private GitPersonInfoInteractor mInfoInfoInteractor;

    @Inject
    public EventsPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new GitPersonInfoInteractor(mContext);
    }

    @Override
    protected Observable<Response<List<GitEvent>>> load(int page) {
        return mInfoInfoInteractor.loadGitEvents(PreferenceUtils.getUsername(mContext), page);
    }
}
