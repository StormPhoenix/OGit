package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.UserDetailsPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.view.UserDetailsView;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.widget.KeyValueLabel;

import javax.inject.Inject;

import butterknife.BindView;

public class UserProfileActivity extends BaseActivity implements UserDetailsView {

    public static final Intent getIntent(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        return intent;
    }

    @BindView(R.id.img_user_header)
    ImageView mImgUserHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_email)
    TextView mTextEmail;
    @BindView(R.id.text_location)
    TextView mTextLocation;
    @BindView(R.id.text_join)
    TextView mTextJoinTime;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.label_user_star)
    KeyValueLabel mLabelStarredNum;
    @BindView(R.id.label_user_followers)
    KeyValueLabel mLabelFollowersNum;
    @BindView(R.id.label_user_following)
    KeyValueLabel mLabelFollowingsNum;

    @Inject
    public UserDetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void setUpToolbar(String title) {
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener((v) -> {
            onBackPressed();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setFollowers(String followers) {
        mLabelFollowersNum.setValueName(followers);
    }

    @Override
    public void setFollowings(String followings) {
        mLabelFollowingsNum.setValueName(followings);
    }

    @Override
    public void setEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            mTextEmail.setText(getResources().getString(R.string.unknown));
        }
        mTextEmail.setText(email);
    }

    @Override
    public void setLocation(String location) {
        mTextLocation.setText(location);
    }

    @Override
    public void setJoinTime(String joinTime) {
        mTextJoinTime.setText(joinTime);
    }

    @Override
    public void stopProgress() {
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void showMessage(String message) {
        ActivityUtils.ViewUtils.showMessage(mImgUserHeader, message);
    }

    @Override
    public ImageView getHeadImageView() {
        return mImgUserHeader;
    }

    @Override
    public void setStaredCount(String count) {
        mLabelStarredNum.setValueName(count);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }
}
