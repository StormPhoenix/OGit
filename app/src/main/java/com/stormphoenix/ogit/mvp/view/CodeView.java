package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.mvp.view.base.BaseView;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */
public interface CodeView extends BaseUIView {
    void initWebView();

    void loadCodeContent(String codeContent);
}
