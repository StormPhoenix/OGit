package com.stormphoenix.ogit.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.dagger2.component.DaggerActivityComponent;
import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.presenter.repository.CodePresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.view.CodesView;
import com.stormphoenix.ogit.utils.EncodingUtils;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

import static com.stormphoenix.ogit.shares.OGitConstants.CHARSET_UTF8;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class CodesFragment extends BaseFragment implements CodesView {
    private static final String TAG = CodesFragment.class.getSimpleName();
    // 该代码文件的所有者
    private String owner;
    // 该仓库名字
    private String repo;
    // 代码文件在仓库中的相对路径
    private String path;
    // 分支
    private String branch;

    @BindView(R.id.codeview)
    CodeView codeView;
//    @BindView(R.id.webview)
//    WebView mWebview;

//    private SourceEditor editor;

    @Inject
    public CodePresenter mPresenter;

    View rootView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    public static CodesFragment getInstance(String owner, String repo, String path, String branch) {
        CodesFragment fragment = new CodesFragment();
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
        codeView.setTheme(CodeViewTheme.ANDROIDSTUDIO).fillColor();
        //这里的CODE 为需要显示的代码，类型为String，使用的时候自己替换下。
//        codeView.showCode(CODE);
//        editor = new SourceEditor(mWebview);
//        editor.setWrap(PreferenceUtils.getCodePreferences(this.getActivity()).getBoolean(
//                PreferenceUtils.WRAP, false));
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
//        editor.setMarkdown(isMarkdown);
    }

    @Override
    public void setSource(String name, GitBlob blob) {
        String content = blob.getContent();
        if (content == null)
            content = "";
        boolean encoded = !TextUtils.isEmpty(content) && GitBlob.ENCODING_BASE64.equals(blob.getEncoding());
        if (encoded) {
            try {
                codeView.showCode(new String(EncodingUtils.fromBase64(content), CHARSET_UTF8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            codeView.showCode(content);
        }
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
