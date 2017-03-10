package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class ListItemPresenter<T, V extends ListItemView<T>> extends BasePresenter<V> {
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
        load(0).compose(RxJavaCustomTransformer.<Response<List<T>>>defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<List<T>>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<List<T>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            mView.loadNewlyListItem(listResponse.body());
                        } else if (listResponse.code() == 401) {
                            mView.reLogin();
                        } else {
                            Log.e(TAG, "onNext: " + listResponse.code() + " " + listResponse.message());
                            mView.showMessage(listResponse.message());
                        }
                        mView.hideProgress();
                    }
                });
    }

    public void loadMoreListItem() {
        mView.showProgress();
        load(mView.getListItemCounts() / 10 + 1)
                .compose(RxJavaCustomTransformer.<Response<List<T>>>defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<List<T>>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<List<T>> response) {
                        mView.loadMoreListItem(response.body());
                        mView.hideProgress();
                    }
                });
    }

    protected abstract Observable<Response<List<T>>> load(int page);
}