package com.stormphoenix.ogit.mvp.ui.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.ImageUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener, MenuItemCompat.OnActionExpandListener {
    private static final String TAG = MainActivity.class.getSimpleName();
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

        mNavView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_toolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ComponentName componentName = getComponentName();

//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(componentName));
//        searchView.setQueryHint(getString(R.string.search));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return true;
//            }
//        });
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setUsername(String username) {
        mTextUsername.setText(username);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                return true;
            case R.id.nav_org:
                Bundle bundle = new Bundle();
                bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_ORGANIZATION);
                ActivityUtils.startActivity(this, ToolbarActivity.newIntent(this, bundle));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}
