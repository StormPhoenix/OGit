package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.interactor.GitPersonInfoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.EventsView;
import com.stormphoenix.ogit.shares.PreferenceUtils;
import com.stormphoenix.ogit.shares.RxJavaCustomTransformer;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EventsPresenter extends BasePresenter<EventsView> {

    private Context mContext = null;

    private GitPersonInfoInteractor mInfoInfoInteractor;

    @Inject
    public EventsPresenter(Context context) {
        mContext = context;
        mInfoInfoInteractor = new GitPersonInfoInteractor(mContext);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        initGitEventList();
    }

    private void initGitEventList() {
        mView.showProgress();
        mInfoInfoInteractor.loadGitEvents(PreferenceUtils.getString(mContext, PreferenceUtils.USERNAME), 0)
                .compose(RxJavaCustomTransformer.<List<GitEvent>>defaultSchedulers())
                .subscribe(new Subscriber<List<GitEvent>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(List<GitEvent> gitEvents) {
                        mView.initEventsListView(gitEvents);
                    }
                });
    }
}
