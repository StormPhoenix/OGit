package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;

import com.stormphoenix.ogit.adapters.FragmentsAdapter;
import com.stormphoenix.ogit.mvp.ui.activities.base.TabPagerActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchRepoFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchUsersFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static android.app.SearchManager.QUERY;
import static android.content.Intent.ACTION_SEARCH;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 用于响应搜索功能。当调用系统搜索框架时，SearchActivity就会进行响应
 */

public class SearchActivity extends TabPagerActivity<FragmentsAdapter> {
    /**
     * 搜索的关键字，SearchActivity被启动的时候，keyword就可以通过Intent进行赋值
     */
    private String keyword;

    @Override
    public void initializeInjector() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parseIntent(getIntent());
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

    public String getSearchKeyWord() {
        return keyword;
    }

    @Override
    protected FragmentsAdapter createAdapter() {
//        String[] titleList = {"User"};
        String[] titleList = {"Repo", "User"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(SearchRepoFragment.getInstance());
        fragmentList.add(SearchUsersFragment.getInstance());

        FragmentsAdapter mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
