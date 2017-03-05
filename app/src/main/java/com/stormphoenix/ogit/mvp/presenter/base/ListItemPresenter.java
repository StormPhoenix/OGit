package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

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
        mView.startRefresh();
        load(0).compose(RxJavaCustomTransformer.<Response<List<T>>>defaultSchedulers())
                .subscribe(new Subscriber<Response<List<T>>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stopRefresh();
                    }

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
                        mView.stopRefresh();
                    }
                });
    }

    public void loadMoreListItem() {
        mView.startRefresh();
        load(mView.getListItemCounts() / 10 + 1)
                .compose(RxJavaCustomTransformer.<Response<List<T>>>defaultSchedulers())
                .subscribe(new Subscriber<Response<List<T>>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e(TAG, "onError: " + e.getMessage());
                        mView.stopRefresh();
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(Response<List<T>> itemList) {
                        mView.loadMoreListItem(itemList.body());
                        mView.stopRefresh();
                    }
                });
    }

    protected abstract Observable<Response<List<T>>> load(int page);
}