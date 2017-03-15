package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.widget.ImageHorizontalKeyValueLabel;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;
import com.stormphoenix.ogit.widget.KeyValueLabel;

import butterknife.BindView;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class OwnerDetailsActivity extends BaseActivity {

    @BindView(R.id.img_app_bar)
    protected ImageView mImgAppBar;
    @BindView(R.id.toolbar)
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
}
