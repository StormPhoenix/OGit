package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.mvp.model.interactor.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.EventsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.ui.activities.UserProfileActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserPerformedEventsPresenter extends EventsPresenter {
    /**
     * Interactor 用于提交网络请求获取数据
     **/
    private String ownerName;
    private UserInteractor mInfoInfoInteractor;

    @Inject
    public UserPerformedEventsPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new UserInteractor(mContext);
    }

    @Override
    protected Observable<Response<List<GitEvent>>> load(int page) {
        if (TextUtils.isEmpty(ownerName)) {
            return null;
        }
        // 加载最新执行的事件
        return mInfoInfoInteractor.performedEvents(ownerName, page);
    }

    public void startOwnerProfileActivity() {
//        Intent intent = new Intent(mContext, UserProfileActivity.class);
//        ActivityUtils.startActivity(mContext, intent);
    }

    public void startRepoDetailsActivity() {
        Intent intent = RepositoryActivity.getIntent(mContext);
        ActivityUtils.startActivity(mContext, intent);
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
