package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.adapters.FragmentsAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.PersonsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.OrgEventsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.OrgMembersPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.TabPagerActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.EventsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.PersonsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgDetailsActivity extends TabPagerActivity<FragmentsAdapter> {
    private GitOrganization organization;

    public static final Intent getIntent(Context context) {
        Intent intent = new Intent(context, OrgDetailsActivity.class);
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
    public void onMainThreadEvent(GitOrganization organization) {
        this.organization = organization;
        toolbar.setTitle(organization.getLogin());
        toolbar.setSubtitle(PreferenceUtils.getUsername(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().unregister(this);
        configureTabPager();
    }

    @Override
    protected FragmentsAdapter createAdapter() {
        //        String[] titleList = {"User"};
        String[] titleList = {"Events","Members"};
        List<BaseFragment> fragmentList = new ArrayList<>();

        // create the EventsFragment which is displayed for Organization events
        OrgEventsPresenter presenter = new OrgEventsPresenter(this);
        presenter.setOrgName(organization.getLogin());
        fragmentList.add(EventsFragment.newInstance(presenter));

        // create the EventsFragment which is displayed for Organization events
        OrgMembersPresenter membersPresenter = new OrgMembersPresenter(this);
        membersPresenter.setOrgName(organization.getLogin());
        fragmentList.add(PersonsFragment.newInstance(membersPresenter));

        FragmentsAdapter mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }

    public GitOrganization getOrganization() {
        return organization;
    }
}
