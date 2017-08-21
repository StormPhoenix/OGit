package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */
public interface CodesView extends BaseUIView {
    void initWebView();

    void setMarkdown(boolean isMarkdown);

    void setSource(String name, GitBlob blob);
}
