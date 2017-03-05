package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseView;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */
public interface CodeView extends BaseView {
    void initWebView();

    void stopRefresh();

    void showMessage(String message);

    void startRefresh();

    void loadCodeContent(String codeContent);
}
