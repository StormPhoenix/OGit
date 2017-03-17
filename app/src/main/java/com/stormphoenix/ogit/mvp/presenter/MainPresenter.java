package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.mvp.model.interactor.NotifyInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.MainView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public static final String TAG = MainPresenter.class.getSimpleName();
    private Context mContext;
    private NotifyInteractor mInteractor;

    @Inject
    public MainPresenter(Context context) {
        mContext = context;
        mInteractor = new NotifyInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        loadNotification();
    }

    public void loadNotification() {
        mInteractor.loadNotification().compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<List<GitNotification>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(Response<List<GitNotification>> response) {
                        Log.e(TAG, "onNext: ");
                        mView.saveNotificationMessage(response.body());
                    }
                });
    }
}





