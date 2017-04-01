package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.repository.CodePresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.CodeView;
import com.stormphoenix.ogit.utils.PreferenceUtils;
import com.stormphoenix.ogit.utils.SourceEditor;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class CodeFragment extends BaseFragment implements CodeView {
    private static final String TAG = CodeFragment.class.getSimpleName();
    // 该代码文件的所有者
    private String owner;
    // 该仓库名字
    private String repo;
    // 代码文件在仓库中的相对路径
    private String path;
    // 分支
    private String branch;

    @BindView(R.id.webview)
    WebView mWebview;

    private SourceEditor editor;

    @Inject
    public CodePresenter mPresenter;

    View rootView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    public static CodeFragment getInstance(String owner, String repo, String path, String branch) {
        CodeFragment fragment = new CodeFragment();
        fragment.setRepo(repo);
        fragment.setPath(path);
        fragment.setOwner(owner);
        fragment.setBranch(branch);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initPresenter();
        mPresenter.onAttachView(this);
        mPresenter.onCreate(savedInstanceState);
        return rootView;
    }

    private void initPresenter() {
        mPresenter.setBranch(branch);
        mPresenter.setOwner(owner);
        mPresenter.setPath(path);
        mPresenter.setRepo(repo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_code;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void initWebView() {
        editor = new SourceEditor(mWebview);
        editor.setWrap(PreferenceUtils.getCodePreferences(this.getActivity()).getBoolean(
                PreferenceUtils.WRAP, false));
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setMarkdown(boolean isMarkdown) {
        editor.setMarkdown(isMarkdown);
    }

    @Override
    public void setSource(String name, GitBlob blob) {
        editor.setSource(name, blob);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
