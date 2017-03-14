package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.widget.ImageKeyValueLabel;

import butterknife.BindView;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class OwnerDetailsActivity extends BaseActivity {

    @BindView(R.id.img_app_bar)
    ImageView mImgAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image_labels_wrapper)
    LinearLayout mImageLabelsWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_details;
    }

    public void addImageLabel(int drawabelId, String keyText, String valueText) {
        ImageKeyValueLabel label = new ImageKeyValueLabel(this);
        label.setImageDrawableResourceId(drawabelId);
        label.setKeyName(keyText);
        label.setValueName(valueText);
        mImageLabelsWrapper.addView(label);
    }

    public void addImageLabel(ImageKeyValueLabel label) {
        mImageLabelsWrapper.addView(label);
    }
}
