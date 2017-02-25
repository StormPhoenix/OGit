package com.stormphoenix.ogit.mvp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.component.FragmentViewPager;
import com.stormphoenix.ogit.mvp.ui.fragments.BaseFragment;
import com.stormphoenix.ogit.mvp.view.MainView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {
    @BindView(R.id.tab_layout)
    SmartTabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager_fragments)
    FragmentViewPager mPagerFragments;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initializeInjector() {

    }

    @Override
    public void initToolbar(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initDrawerView() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void initMainPagerFragments(String[] titles, List<BaseFragment> fragments) {
        mPagerFragments.setData(this, fragments, titles);
        mTabLayout.setViewPager(mPagerFragments);
    }
}
