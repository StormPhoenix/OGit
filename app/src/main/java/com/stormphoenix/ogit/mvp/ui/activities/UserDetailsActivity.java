package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.adapters.base.FragmentsAdapter;
import com.stormphoenix.ogit.mvp.presenter.user.UserPerformedEventsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.TabPagerActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.EventsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserDetailsActivity extends TabPagerActivity<FragmentsAdapter> {
    private GitUser mUser;

    public static final Intent getIntent(Context context) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        return intent;
    }

    @Override
    public void initializeInjector() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(GitUser user) {
        this.mUser = user;
        toolbar.setTitle(user.getLogin());
//        toolbar.setSubtitle(PreferenceUtils.getUsername(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().unregister(this);
        configureTabPager();
    }

    @Override
    protected FragmentsAdapter createAdapter() {
        //        String[] titleList = {"User"};
        String[] titleList = {"Actions"};
        List<BaseFragment> fragmentList = new ArrayList<>();

        // create the EventsFragment which is displayed for User performed events
        UserPerformedEventsPresenter presenter = new UserPerformedEventsPresenter(this);
        presenter.setOwnerName(mUser.getLogin());
        fragmentList.add(EventsFragment.newInstance(presenter));

//        OrgMembersPresenter membersPresenter = new OrgMembersPresenter(this);
//        membersPresenter.setOrgName(mUser.getLogin());
//        fragmentList.add(UsersFragment.newInstance(membersPresenter));

        FragmentsAdapter mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
