package com.stormphoenix.ogit.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.IntDef;

import com.stormphoenix.ogit.mvp.contract.BaseContract;

/**
 * Created by StormPhoenix on 17-8-23.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {
    protected V mView = null;

    @Override
    public void onAttachView(V view) {
        mView = view;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
    }

    @Override
    public void onDestory() {

    }

    public static class NotifyEvent<T> {
        public static final int CREATE_NOTE = 0;
        public static final int MODIFY_NOTE = 1;
        private int type;
        private T data;

        @IntDef({CREATE_NOTE, MODIFY_NOTE})
        public @interface Type {
        }

        public int getType() {
            return type;
        }

        public void setType(@NotifyEvent.Type int type) {
            this.type = type;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
