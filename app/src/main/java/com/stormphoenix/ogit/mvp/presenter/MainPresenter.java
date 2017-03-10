package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.EventsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.StaredFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.MainView;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class MainPresenter extends BasePresenter<MainView> {
    private Context mContext;

    @Inject
    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        initToolbar();
        mView.initDrawerView();
        initPagerFragments();
        initNavigationView();
    }

    private void initNavigationView() {
        if (!TextUtils.isEmpty(PreferenceUtils.getString(mContext, PreferenceUtils.AVATAR_URL))) {
            mView.setHeaderImage(PreferenceUtils.getString(mContext, PreferenceUtils.AVATAR_URL));
        }
        mView.setUsername(PreferenceUtils.getUsername(mContext));
    }

    private void initPagerFragments() {
        String[] titleList = {"Event", "Starred"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(EventsFragment.getInstance(PreferenceUtils.getString(mContext, PreferenceUtils.USERNAME)));
        fragmentList.add(StaredFragment.getInstance(PreferenceUtils.getString(mContext, PreferenceUtils.USERNAME)));
        mView.initMainPagerFragments(titleList, fragmentList);
    }

    private void initToolbar() {
        mView.initToolbar(mContext.getString(R.string.ogit));
    }
}
