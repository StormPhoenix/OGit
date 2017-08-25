package com.stormphoenix.ogit.mvp.view.base;

import com.stormphoenix.ogit.mvp.contract.BaseContract;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public interface ProgressView extends BaseContract.View {
    void showProgress();

    void hideProgress();
}
