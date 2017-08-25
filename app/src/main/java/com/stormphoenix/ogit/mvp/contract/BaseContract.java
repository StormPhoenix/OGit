package com.stormphoenix.ogit.mvp.contract;

import android.os.Bundle;

/**
 * Created by StormPhoenix on 17-8-23.
 * StormPhoenix is a intelligent Android developer.
 */

public interface BaseContract {
    interface Model {
    }

    interface View {
        void showProgress();

        void hideProgress();

        void showMessage(String message);
    }

    interface Presenter<V extends View> {
        void onAttachView(V view);

        void onCreate(Bundle onSavedInstanceState);

        void onDestory();
    }
}
