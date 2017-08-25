package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.organization.OrgProfilePresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.OwnerProfileActivity;
import com.stormphoenix.ogit.mvp.view.OrgDetailsView;
import com.stormphoenix.ogit.utils.ImageUtils;

import javax.inject.Inject;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgProfileActivity extends OwnerProfileActivity implements OrgDetailsView {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, OrgProfileActivity.class);
        return intent;
    }

    @Inject
    public OrgProfilePresenter mPresenter = null;

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

    public void initViews() {
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
    public void setFollowingCount(String followeringCount) {
        mLabel3.setValueName(followeringCount);
    }

    @Override
    public void stopProgress() {

    }

    @Override
    public void loadHeaderImage(String url) {
        ImageUtils.getInstance().displayImage(url, mImageAppBar);
        ImageUtils.getInstance().displayImage(url, circleHeadImage);
    }

    @Override
    protected void onBaseInfoClicked() {
        super.onBaseInfoClicked();
        mPresenter.startOrgDetailsActivity();
    }
}
