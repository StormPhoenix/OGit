package com.stormphoenix.ogit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {
    List<BaseFragment> mFragmentList;
    String[] mTitles;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<BaseFragment> fragmentList, String[] titles) {
        mFragmentList = fragmentList;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (mFragmentList != null) {
            return mFragmentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        } else {
            return null;
        }
    }
}
