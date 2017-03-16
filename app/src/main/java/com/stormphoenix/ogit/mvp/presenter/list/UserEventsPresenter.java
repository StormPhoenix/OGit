package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;
import android.content.Intent;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.mvp.model.interactor.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.EventsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.ui.activities.UserProfileActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserEventsPresenter extends EventsPresenter {
    /**
     * Interactor 用于提交网络请求获取数据
     **/
    private UserInteractor mInfoInfoInteractor;

    @Inject
    public UserEventsPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new UserInteractor(mContext);
    }

    @Override
    protected Observable<Response<List<GitEvent>>> load(int page) {
        // 加载最新收到的事件
        return mInfoInfoInteractor.loadReceiveEvents(PreferenceUtils.getUsername(mContext), page);
    }

    public void startOwnerProfileActivity() {
        Intent intent = new Intent(mContext, UserProfileActivity.class);
        ActivityUtils.startActivity(mContext, intent);
    }

    public void startRepoDetailsActivity() {
        Intent intent = RepositoryActivity.getIntent(mContext);
        ActivityUtils.startActivity(mContext, intent);
    }
}
