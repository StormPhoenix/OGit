package com.stormphoenix.ogit.mvp.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.component.BreadcrumbView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreadcrumbTreeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.file_bread_crumb)
    BreadcrumbView mFileBreadCrumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_tree;
    }

    @Override
    public void initializeInjector() {

    }
}
