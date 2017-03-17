package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.ogit.R;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 代表 TabLayout + ViewPager的通用Activity
 * 子类只需要覆盖 {@link #createAdapter()}方法，并调用 {@link #configureTabPager()}就可以完成
 * 页面的设置
 */

public abstract class TabPagerActivity<V extends PagerAdapter> extends BaseActivity implements ViewPager.OnPageChangeListener {
    /**
     * toolbar
     */
    protected Toolbar toolbar;
    /**
     * Tab layout
     */
    protected ViewPager viewPager;

    /**
     * Pager Adapter
     */
    protected SmartTabLayout tabLayout;

    /**
     * Pager adapter
     */
    protected V adapter;

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }

    /**
     * 获取当前Pager的标题
     *
     * @param position
     * @return
     */
    protected String getTitle(final int position) {
        return adapter.getPageTitle(position).toString();
    }

    protected void setCurrentItem(int position) {
    }

    /**
     * create pager adapter
     *
     * @return PagerAdapter
     */
    protected abstract V createAdapter();

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_pager;
    }

    private void createPager() {
        adapter = createAdapter();
        // 防止Fragment频繁被销毁
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    /**
     * Create tab using information from current adapter
     * <p>
     * This can be called when the tabs changed but must be called after an
     * initial call to {@link #configureTabPager()}
     */
    private void configTabLayout() {
        tabLayout.setViewPager(viewPager);
    }

    /**
     * Configure tabs and pager
     */
    protected void configureTabPager() {
        if (adapter == null) {
            createPager();
            configTabLayout();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
