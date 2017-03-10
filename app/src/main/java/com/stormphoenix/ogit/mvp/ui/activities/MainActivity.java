package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.FragmentsAdapter;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.MainPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.MainView;
import com.stormphoenix.ogit.utils.ImageUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView {
    @BindView(R.id.tab_layout)
    SmartTabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.pager_fragments)
    ViewPager mPagerFragments;
    // 个人信息布局
    View mUserInfoView;
    CircleImageView mHeaderImage;
    TextView mTextUsername;

    FragmentsAdapter mAdapter;
    ActionBarDrawerToggle mDrawerToggle;

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    private void initViews() {
//        mUserInfoView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mUserInfoView = mNavView.getHeaderView(0);
        mHeaderImage = (CircleImageView) mUserInfoView.findViewById(R.id.img_owner_header);
        mTextUsername = (TextView) mUserInfoView.findViewById(R.id.text_owner_name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
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
        mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragments, titles);

        mPagerFragments.setAdapter(mAdapter);
        mTabLayout.setViewPager(mPagerFragments);
    }

    @Override
    public void setHeaderImage(String url) {
        ImageUtils.getInstance().displayImage(url, mHeaderImage);
    }

    @Override
    public void setUsername(String username) {
        mTextUsername.setText(username);
    }
}
