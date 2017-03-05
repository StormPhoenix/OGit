package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.CodeFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.ContributorsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import butterknife.BindView;

public class WrapperActivity extends BaseActivity {
    public static final String OWNER = "owner";
    public static final String REPO = "repo";
    public static final String PATH = "path";
    public static final String BRANCH = "branch";

    private Bundle bundle;
    public static final String TYPE = "type";
    public static int TYPE_CONTRIBUTOR = 1;
    public static int TYPE_CODE = 2;
    private int type;

    // Toolbar 设置的标题
    private String title;
    // Toolbar 设置的子标题
    private String subTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private BaseFragment currentFragment = null;

    public static Intent getIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WrapperActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();

        if (type == TYPE_CONTRIBUTOR) {
            title = getString(R.string.contributor);
            currentFragment = ContributorsFragment.getInstance();
        } else if (type == TYPE_CODE) {
            String owner = bundle.getString(OWNER);
            String repo = bundle.getString(REPO);
            String path = bundle.getString(PATH);
            String branch = bundle.getString(BRANCH);
            title = repo;
            subTitle = path;
            currentFragment = CodeFragment.getInstance(owner, repo, path, branch);
        }
        setUpToolbar();
        // 根据Type设置内容
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_wrapper, currentFragment)
                .commit();
    }

    /**
     * 设置 Toolbar 显示的内容
     */
    private void setUpToolbar() {
        mToolbar.setTitle(title);
        if (!TextUtils.isEmpty(subTitle)) {
            mToolbar.setSubtitle(subTitle);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void parseIntent() {
        bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wrapper;
    }

    @Override
    public void initializeInjector() {
    }
}
