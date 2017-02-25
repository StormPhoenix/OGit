package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.dagger2.InjectorInitializer;

import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class BaseFragment extends Fragment implements InjectorInitializer{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(getLayoutId(), container, true);
        ButterKnife.bind(contentView);
        initializeInjector();
        return contentView;
    }

    protected abstract int getLayoutId();
}
