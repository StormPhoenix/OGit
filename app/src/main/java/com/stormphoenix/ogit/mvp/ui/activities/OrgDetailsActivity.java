package com.stormphoenix.ogit.mvp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.OrgsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.OwnerDetailsActivity;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgDetailsActivity extends OwnerDetailsActivity {

    @Inject
    public OrgsPresenter mPresenter = null;

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
