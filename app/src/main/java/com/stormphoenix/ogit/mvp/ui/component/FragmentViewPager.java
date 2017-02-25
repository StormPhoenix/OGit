package com.stormphoenix.ogit.mvp.ui.component;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.stormphoenix.ogit.mvp.ui.fragments.BaseFragment;

import java.util.List;

/**
 * Created by developer on 16-10-18.
 */

public class FragmentViewPager extends ViewPager {

    private String[] titles = null;
    private List<BaseFragment> fragments = null;
    private FragmentActivity fragmentActivity = null;

    public FragmentViewPager(Context context) {
        super(context);
    }

    public FragmentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isDataSet() {
        if (fragmentActivity == null || fragments == null || titles == null) {
            return false;
        } else {
            return true;
        }
    }

//    @Override
//    public void setAdapter(PagerAdapter adapter) {
//        super.setAdapter(adapter);
//    }

    public void setData(FragmentActivity activity, List<BaseFragment> fragmentList, String[] fragmentTitles) {
        if (fragmentList.size() != fragmentTitles.length) {
            throw new IllegalArgumentException("the size of fragments is not equal to the size of titles.");
        }
        this.fragmentActivity = activity;
        this.fragments = fragmentList;
        this.titles = fragmentTitles;
        PageAdapter adapter = new PageAdapter(fragmentActivity.getSupportFragmentManager());
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
    }

    protected class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
