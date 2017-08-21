package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.presenter.commits.CommitsPresenter;
import com.stormphoenix.ogit.mvp.presenter.repository.ContributorsPresenter;
import com.stormphoenix.ogit.mvp.presenter.user.NotifyPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.CodesFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.commits.CommitDetailsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.commits.CommitsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.users.NotifyFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.OrganizationsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.UsersFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.shares.OGitConstants;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import butterknife.BindView;

public class ToolbarActivity extends BaseActivity {
    public static final String OWNER = "owner";
    public static final String REPO = "repo";
    public static final String PATH = "path";
    public static final String BRANCH = "branch";

    private Bundle bundle;
    public static final String TYPE = "type";
    public static final String SUB_TITLE = "subTitle";
    public static final int TYPE_CONTRIBUTOR = 1;
    public static final int TYPE_CODE = 2;
    public static final int TYPE_ORGANIZATION = 3;
    public static final int TYPE_NOTIFICATION = 4;
    public static final int TYPE_COMMITS = 5;
    public static final int TYPE_COMMIT_DETAILS = 6;
    private int type;

    // Toolbar 设置的标题
    private String title;
    // Toolbar 设置的子标题
    private String subTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private BaseFragment currentFragment = null;

    public static Intent newIntent(Context context, Bundle bundle) {
        assert bundle != null;
        Intent intent = new Intent(context, ToolbarActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();

        resolveCurrentFragment();
        setUpToolbar();
        // 根据Type设置内容
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_wrapper, currentFragment)
                .commit();
    }

    private void resolveCurrentFragment() {
        if (type == TYPE_CONTRIBUTOR) {
            title = getString(R.string.contributor);
            ContributorsPresenter presenter = new ContributorsPresenter(this);
            currentFragment = UsersFragment.newInstance(presenter);
        } else if (type == TYPE_CODE) {
            String owner = bundle.getString(OWNER);
            String repo = bundle.getString(REPO);
            String path = bundle.getString(PATH);
            String branch = bundle.getString(BRANCH);
            title = repo;
            subTitle = path;
            currentFragment = CodesFragment.getInstance(owner, repo, path, branch);
        } else if (type == TYPE_ORGANIZATION) {
            title = getString(R.string.organization);
            subTitle = PreferenceUtils.getUsername(this);
            currentFragment = OrganizationsFragment.getInstance();
        } else if (type == TYPE_NOTIFICATION) {
            title = getString(R.string.notification);
            subTitle = PreferenceUtils.getUsername(this);
            currentFragment = NotifyFragment.newInstance(new NotifyPresenter(this));
        } else if (type == TYPE_COMMITS) {
            title = getString(R.string.commits);
            currentFragment = CommitsFragment.newInstance(new CommitsPresenter(this));
        } else if (type == TYPE_COMMIT_DETAILS) {
            title = getString(R.string.commit_details);
            currentFragment = CommitDetailsFragment.newInstance(
                    bundle.getString(OGitConstants.OWNER_NAME),
                    bundle.getString(OGitConstants.REPO_NAME),
                    bundle.getString(OGitConstants.SHA)
            );
        }
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
        subTitle = bundle.getString(SUB_TITLE, null);
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
