package com.stormphoenix.ogit.mvp.presenter.base;

import android.os.Bundle;
import android.support.annotation.IntDef;

import com.stormphoenix.ogit.mvp.view.BaseView;


/**
 * Created by Developer on 17-1-21.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T mView = null;

    public void onAttachView(T view) {
        mView = view;
    }

    public void onCreate(Bundle onSavedInstanceState) {
    }

    public void onStart() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }

    public void onDestory() {
    }

    public void onSaveInstanceState(Bundle outState) {
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

        public void setType(@Type int type) {
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
