package com.stormphoenix.ogit.mvp.view;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public interface LoginView extends BaseView {
    void initToolbar(String title);

    void showMessage(String string);

    String getUsernameText();

    String getPasswordText();

    void onLoginSuccess();

    void finishView();

    void startMainActivity();
}
