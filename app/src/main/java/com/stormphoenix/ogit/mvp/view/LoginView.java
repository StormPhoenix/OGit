package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public interface LoginView extends BaseUIView {
    void initToolbar(String title);

    void onLoginSuccess();

    void finishView();

    void startMainActivity();
}
