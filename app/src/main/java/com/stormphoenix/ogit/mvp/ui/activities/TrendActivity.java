package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.stormphoenix.ogit.adapters.base.FragmentsAdapter;
import com.stormphoenix.ogit.mvp.presenter.trend.TrendReposPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.TabPagerActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.repository.ReposListFragment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-4-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class TrendActivity extends TabPagerActivity<FragmentsAdapter> {
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TrendActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("Trending");
        configureTabPager();
    }

    @Override
    public void initializeInjector() {
    }

    @Override
    protected FragmentsAdapter createAdapter() {
        //        String[] titleList = {"User"};
        String[] titleList = {"All Language"};
        List<BaseFragment> fragmentList = new ArrayList<>();

        TrendReposPresenter trendReposPresenter = new TrendReposPresenter(this);
        trendReposPresenter.setTrendType(TrendReposPresenter.TREND_TYPE_REPOSITORY);
        fragmentList.add(ReposListFragment2.newInstance(trendReposPresenter));

        FragmentsAdapter mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
