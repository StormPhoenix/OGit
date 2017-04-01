package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class ListItemPresenter<T, R, V extends ListItemView<T>> extends BasePresenter<V> {
    public static String TAG = ListItemPresenter.class.getClass().getName();

    protected Context mContext = null;

    public ListItemPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        mView.initListItemView();
        mView.initRefreshLayout();
        loadNewlyListItem();
    }

    public void loadNewlyListItem() {
        mView.hideProgress();
        Observable<Response<R>> observable = load(0);
        if (observable == null) {
            return;
        }
        mView.clearAllItems();
        load(0).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<R>, BaseUIView>(mView, "network error") {
                    @Override
                    public void onNext(Response<R> response) {
                        RxHttpLog.logResponse(response);
                        if (response.isSuccessful()) {
                            mView.loadNewlyListItem(transformBody(response.body()));
                        } else if (response.code() == 401) {
                            Log.e(TAG, "onNext: " + response.code());
                            mView.reLogin();
                        } else {
                            Log.e(TAG, "onNext: " + response.code() + " " + response.message());
                            mView.showMessage(response.message());
                        }
                        mView.hideProgress();
                    }
                });
    }

    public void loadMoreListItem() {
        mView.showProgress();

        // temperary handle
        if (mView.getListItemCounts() % 10 != 0) {
            mView.hideProgress();
            return;
        }

        Observable<Response<R>> observable = load(mView.getListItemCounts() / 10 + 1);
        if (observable == null) {
            mView.hideProgress();
            return;
        }
        observable.compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<R>, BaseUIView>(mView, "network error") {
                    @Override
                    public void onNext(Response<R> response) {
                        RxHttpLog.logResponse(response);
                        mView.loadMoreListItem(transformBody(response.body()));
                        mView.hideProgress();
                    }
                });
    }

    protected abstract List<T> transformBody(R body);

    protected abstract Observable<Response<R>> load(int page);
}