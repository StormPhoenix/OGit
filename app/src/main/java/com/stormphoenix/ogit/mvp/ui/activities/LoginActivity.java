package com.stormphoenix.ogit.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.LoginPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.mvp.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class LoginActivity extends BaseActivity implements LoginView {
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

    public static Intent getInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public String getUsernameText() {
        return mEditUsername.getText().toString().trim();
    }

    @Override
    public String getPasswordText() {
        return mEditPassword.getText().toString().trim();
    }

    @Override
    public void onLoginSuccess() {
        startMainActivity();
        finishView();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void startMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }
}
