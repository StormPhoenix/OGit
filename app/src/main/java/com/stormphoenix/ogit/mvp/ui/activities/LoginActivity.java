package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.LoginPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.ogit.mvp.view.LoginView;
import com.stormphoenix.ogit.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class LoginActivity extends BaseActivity implements LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Inject
    public LoginPresenter mPresenter = null;

    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ProgressDialogGenerator generator;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildProgressDialog();
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(String title) {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
    }

    @Override
    public void showMessage(String string) {
        Snackbar.make(mBtnLogin, string, Snackbar.LENGTH_SHORT).show();
    }

    public String getUsernameText() {
        return mEditUsername.getText().toString().trim();
    }

    public String getPasswordText() {
        return mEditPassword.getText().toString().trim();
    }

    @Override
    public void onLoginSuccess() {
        generator.cancel();
        startMainActivity();
        finishView();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void startMainActivity() {
        ActivityUtils.startActivity(this, MainActivity.newIntent(this));
    }

    @Override
    public void showProgress() {
        generator.show();
    }

    @Override
    public void hideProgress() {
        generator.cancel();
    }

    private void buildProgressDialog() {
        generator = new ProgressDialogGenerator(this);
        generator.cancelable(false);
        generator.circularProgress();
        generator.content(getResources().getString(R.string.logining));
        generator.title(getResources().getString(R.string.login));
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login(getUsernameText(), getPasswordText());
                break;
            default:
                Log.e(TAG, "onClick: unknown click");
                break;
        }
    }
}
