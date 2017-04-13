package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

/**
 * Created by StormPhoenix on 17-4-11.
 * StormPhoenix is a intelligent Android developer.
 */

public interface IssueView extends BaseUIView {
    void onSendIssueSuccess();
    void onSendIssueFailed();
}
