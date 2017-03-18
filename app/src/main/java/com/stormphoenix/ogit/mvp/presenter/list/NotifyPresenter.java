package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;
import android.os.Bundle;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class NotifyPresenter extends ListItemPresenter<GitNotification, List<GitNotification>, ListItemView<GitNotification>> {
    private List<GitNotification> notifications = null;

    @Inject
    public NotifyPresenter(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected List<GitNotification> transformBody(List<GitNotification> body) {
        return body;
    }

    @Override
    public void loadMoreListItem() {
        // Do nothing
    }

    @Override
    public void loadNewlyListItem() {
        load(0);
    }

    @Override
    protected Observable<Response<List<GitNotification>>> load(int page) {
        if (notifications == null) {
            return null;
        } else {
            mView.loadNewlyListItem(notifications);
            mView.hideProgress();
            return null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(List<GitNotification> notifications) {
        this.notifications = notifications;
        EventBus.getDefault().unregister(this);
        loadNewlyListItem();
    }
}
