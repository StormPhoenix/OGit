package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.view.base.OwnerDetailsView;
import com.stormphoenix.ogit.utils.ViewUtils;
import com.stormphoenix.ogit.widget.ImageHorizontalKeyValueLabel;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;
import com.stormphoenix.ogit.widget.KeyValueLabel;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class OwnerProfileActivity extends BaseActivity implements OwnerDetailsView, AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_OF_SHOWING_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_OF_HIDEING_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    @BindView(R.id.owner_linearlayout_title)
    LinearLayout mTitleContainer;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.center_text_name)
    TextView mCenterTextName;
    @BindView(R.id.center_desc_text)
    TextView mCenterDescText;

    private boolean mIsTitleVisible = false;
    private boolean mIsTitleContainerVisible = true;

    @BindView(R.id.img_app_bar)
    protected ImageView mImageAppBar;
    @BindView(R.id.owner_details_toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.base_info_wrapper)
    protected LinearLayout mImageLabelsWrapper;
    @BindView(R.id.label_1)
    protected KeyValueLabel mLabel1;
    @BindView(R.id.label_2)
    protected KeyValueLabel mLabel2;
    @BindView(R.id.label_3)
    protected KeyValueLabel mLabel3;
    @BindView(R.id.dynamic_label_wrapper)
    LinearLayout mDynamicLabelWrapper;
    @BindView(R.id.avatar_head_image)
    protected CircleImageView circleHeadImage;
    @BindView(R.id.fab)
    protected FloatingActionButton mFab;
    @BindView(R.id.owner_textview_title)
    TextView mTextviewTitle;

    protected enum IsFollowed {
        follewed, unfollewd
    }

    protected IsFollowed isFollowed = IsFollowed.unfollewd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_details;
    }

    public void addBaseLabel(ImageVerticalKeyValueLabel label) {
        mImageLabelsWrapper.addView(label);
    }

    public void addDynamicLabel(ImageHorizontalKeyValueLabel label) {
        mDynamicLabelWrapper.addView(label);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppBar.addOnOffsetChangedListener(this);
        ViewUtils.startAlphaAnimation(mTextviewTitle, 0, View.INVISIBLE);
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

    @OnClick({R.id.base_info_wrapper, R.id.dynamic_label_wrapper})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_info_wrapper:
                onBaseInfoClicked();
                break;
            case R.id.dynamic_label_wrapper:
                onDynamicInfoClicked();
                break;
        }
    }

    @Override
    public void setUpToolbar(String title) {
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mTextviewTitle.setText(title);
        mCenterTextName.setText(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener((v) -> {
            onBackPressed();
        });
    }

    @Override
    public void setOwnerDescription(String description) {
        mCenterDescText.setText(description);
    }

    protected void onDynamicInfoClicked() {
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_OF_SHOWING_TITLE_AT_TOOLBAR) {
            if (!mIsTitleVisible) {
                ViewUtils.startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTitleVisible = true;
            }
        } else {
            if (mIsTitleVisible) {
                ViewUtils.startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_OF_HIDEING_TITLE_DETAILS) {
            if (mIsTitleContainerVisible) {
                ViewUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTitleContainerVisible = false;
            }

        } else {

            if (!mIsTitleContainerVisible) {
                ViewUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTitleContainerVisible = true;
            }
        }
    }

    protected void onBaseInfoClicked() {
    }
}
