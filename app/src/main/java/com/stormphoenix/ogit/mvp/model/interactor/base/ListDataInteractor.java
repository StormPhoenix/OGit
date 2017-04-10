package com.stormphoenix.ogit.mvp.model.interactor.base;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-4.
 * StormPhoenix is a intelligent Android developer.
 */

public interface ListDataInteractor<T> {
    Observable<Response<List<T>>> loadListData(int page);

    int dataType();
}
