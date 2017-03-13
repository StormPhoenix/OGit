package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;

import com.stormphoenix.ogit.adapters.FragmentsAdapter;
import com.stormphoenix.ogit.mvp.presenter.SearchPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.TabPagerActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.ContributorsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.RepoFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.app.SearchManager.QUERY;
import static android.content.Intent.ACTION_SEARCH;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class SearchActivity extends TabPagerActivity {
    private String keyword;

    @Override
    public void initializeInjector() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parseIntent(getIntent());
        EventBus.getDefault().postSticky(keyword);
        configureTabPager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void parseIntent(Intent intent) {
        if (ACTION_SEARCH.equals(intent.getAction()))
            search(intent.getStringExtra(QUERY));
    }

    private void search(final String query) {
        this.keyword = query;
        getSupportActionBar().setTitle(query);
    }

    @Override
    protected PagerAdapter createAdapter() {
        String[] titleList = {"Repo", "User"};
//        String[] titleList = {"Repositories"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(RepoFragment.getInstance(new SearchPresenter(this)));
//        fragmentList.add(StaredFragment.getInstance(PreferenceUtils.getString(this, PreferenceUtils.USERNAME)));
        fragmentList.add(ContributorsFragment.getInstance());

        FragmentsAdapter mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
