package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stormphoenix.ogit.dagger2.InjectorInitializer;

import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class BaseActivity extends AppCompatActivity implements InjectorInitializer {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initializeInjector();
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();
}
