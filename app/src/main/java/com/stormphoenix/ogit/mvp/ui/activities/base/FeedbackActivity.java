package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.issue.IssuePresenter;
import com.stormphoenix.ogit.mvp.view.IssueView;
import com.stormphoenix.ogit.utils.ViewUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class FeedbackActivity extends BaseActivity implements IssueView {

    @Inject
    public IssuePresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_feedback_title)
    AppCompatEditText editFeedbackTitle;
    @BindView(R.id.edit_feedback_content)
    AppCompatEditText editFeedbackContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_feedback:
                mPresenter.sendAnIssue(editFeedbackTitle.getText().toString().trim(), editFeedbackContent.getText().toString().trim());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showMessage(String message) {
        ViewUtils.showMessage(editFeedbackContent, message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSendIssueSuccess() {
        ViewUtils.showMessage(editFeedbackContent, getString(R.string.send_feedback_successful));
    }

    @Override
    public void onSendIssueFailed() {
        ViewUtils.showMessage(editFeedbackContent, getString(R.string.send_feedback_failed));
    }
}
