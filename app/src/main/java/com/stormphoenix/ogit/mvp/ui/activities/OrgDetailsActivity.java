package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.OrgDetailsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.OwnerDetailsActivity;
import com.stormphoenix.ogit.mvp.view.OrgDetailsView;
import com.stormphoenix.ogit.utils.ImageUtils;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgDetailsActivity extends OwnerDetailsActivity implements OrgDetailsView {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, OrgDetailsActivity.class);
        return intent;
    }

    @Inject
    public OrgDetailsPresenter mPresenter = null;

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mLabel1.setKeyName(getString(R.string.member));
        mLabel2.setKeyName(getString(R.string.followers));
        mLabel3.setKeyName(getString(R.string.followings));
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setMembersCount(String memberCount) {
        mLabel1.setValueName(memberCount);
    }

    @Override
    public void setFollowersCount(String followersCount) {
        mLabel2.setValueName(followersCount);
    }

    @Override
    public void setFolloweringCount(String followeringCount) {
        mLabel3.setValueName(followeringCount);
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void loadOrgHeaderImage(String url) {
        ImageUtils.getInstance().displayImage(url, mImgAppBar);
    }
}
