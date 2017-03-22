package com.stormphoenix.ogit.shares.rx.subscribers;

import android.util.Log;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class DefaultUiSubscriber<T, V extends BaseUIView> extends Subscriber<T> {
    public static final String TAG = DefaultUiSubscriber.class.getSimpleName();

    private V ui;
    private String errorMessage;

    public DefaultUiSubscriber(V ui, String errorMessage) {
        this.ui = ui;
        this.errorMessage = errorMessage;
    }

    @Override
    public void onStart() {
        super.onStart();
        ui.showProgress();
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ");
        ui.hideProgress();
        ui.showMessage(e.toString());
    }

    @Override
    public void onCompleted() {
        ui.hideProgress();
    }
}
