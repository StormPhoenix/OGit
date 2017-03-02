package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.ContributorsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;

import butterknife.BindView;

public class WrapperActivity extends BaseActivity {
    public static final String TYPE = "type";
    public static int TYPE_CONTRIBUTOR = 1;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private BaseFragment currentFragment = null;
    private int type;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, WrapperActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (type == TYPE_CONTRIBUTOR) {
            currentFragment = ContributorsFragment.getInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_wrapper, currentFragment)
                .commit();
    }

    public void parseIntent() {
        Bundle bundle = getIntent().getExtras();
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
