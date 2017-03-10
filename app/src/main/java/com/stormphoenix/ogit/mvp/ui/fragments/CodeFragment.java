package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.CodePresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.CodeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class CodeFragment extends BaseFragment implements CodeView {
    // 该代码文件的所有者
    private String owner;
    // 该仓库名字
    private String repo;
    // 代码文件在仓库中的相对路径
    private String path;
    // 分支
    private String branch;
    // 代码内容
    private String content;

    @BindView(R.id.webview)
    WebView mWebview;

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
        WebSettings settings = mWebview.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(new JavaScriptInterface(), "bitbeaker");
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
    public void loadCodeContent(String codeContent) {
        content = codeContent;
        mWebview.loadUrl("file:///android_asset/source.html");
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

    protected class JavaScriptInterface {
        @JavascriptInterface
        public String getCode() {
            return TextUtils.htmlEncode(content.replace("\t", "    "));
        }

        @JavascriptInterface
        public String getRawCode() {
            return content;
        }

        @JavascriptInterface
        public String getFilename() {
            return path;
        }

        @JavascriptInterface
        public int getLineHighlight() {
            return 0;
        }
    }
}
