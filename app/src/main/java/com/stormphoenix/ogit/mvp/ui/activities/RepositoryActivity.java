package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.RepositoryPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.view.RepositoryView;
import com.stormphoenix.ogit.shares.HtmlImageGetter;
import com.stormphoenix.ogit.utils.TextTools;
import com.stormphoenix.ogit.widget.KeyValueLabel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RepositoryActivity extends BaseActivity implements RepositoryView {
    @Inject
    public RepositoryPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_description)
    TextView mTextDescription;
    @BindView(R.id.text_branch)
    TextView mTextBranch;
    @BindView(R.id.text_num_commit)
    TextView mTextNumCommit;
    @BindView(R.id.text_num_contributor)
    TextView mTextNumContributor;
    @BindView(R.id.text_readme)
    AppCompatTextView mTextReadme;
    @BindView(R.id.label_star)
    KeyValueLabel mLabelStar;
    @BindView(R.id.label_fork)
    KeyValueLabel mLabelFork;
    @BindView(R.id.label_watcher)
    KeyValueLabel mLabelWatcher;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        return intent;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repository;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setDescription(String description) {
        mTextDescription.setText(description);
    }

    @Override
    public void setStarCount(String s) {
        mLabelStar.setValueName(s);
    }

    @Override
    public void setBranch(String branch) {
        mTextBranch.setText(branch);
    }

    @Override
    public void setForkCount(String forkCount) {
        mLabelFork.setValueName(forkCount);
    }

    @Override
    public void setToolbarStatus(String repositoryName, String ownerName) {
        mToolbar.setTitle(repositoryName);
        mToolbar.setSubtitle(ownerName);
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void loadReadmeHtml(String readmeText, String repoHtmlUrl, String defaultBranch) {
        HtmlImageGetter imageGetter = new HtmlImageGetter(mTextReadme, this,
                repoHtmlUrl + "/raw/" + defaultBranch);
        TextTools.showReadmeHtml(mTextReadme, readmeText, imageGetter);
    }

    @Override
    public void setWatchersCount(String watcher) {
        mLabelWatcher.setValueName(watcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.contributor_wrapper, R.id.code_wrapper})
    public void onClick(View view) {
        mPresenter.onClick(view);
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
}
