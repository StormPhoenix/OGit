package com.stormphoenix.ogit.mvp.presenter.repository;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.api.BlobFileApi;
import com.stormphoenix.ogit.mvp.model.interactor.repository.RepoFileInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.CodeView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * CodePresenter 负责 CodeFragment 中的逻辑交互，主要逻辑是：
 * 1、利用预先提供的 owner、repo、path、branch 数据获取 GitBlob信息。
 * 2、利用 GitBlob 中的信息下载代码文件。
 */
public class CodePresenter extends BasePresenter<CodeView> {
    public static final String TAG = CodePresenter.class.getName();
    // 代码文件的信息对象
    private GitBlob mBlob = null;
    // 网络交互对象
    private RepoFileInteractor mInteractor;

    private Context mContext = null;
    // 该代码文件的所有者
    private String owner;
    // 该仓库名字
    private String repo;
    // 代码文件在仓库中的相对路径
    private String path;
    // 分支
    private String branch;

    @Inject
    public CodePresenter(Context context) {
        super();
        mContext = context;
        mInteractor = new RepoFileInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        mView.initWebView();
        loadBlob();
    }

    public void loadBlob() {
        Log.e(TAG, "loadBlob: " + owner + " " + repo + " " + path + " " + branch);
        Retrofit retrofit = RetrofitCreator.getJsonRetrofit(mContext);
        BlobFileApi service = retrofit.create(BlobFileApi.class);

        service.loadBlob(owner, repo, path, branch)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<GitBlob, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(GitBlob blob) {
                        Log.e(TAG, "onNext: success");
                        mBlob = blob;
                        loadContent();
                    }
                });
    }

    public void loadContent() {
        mView.setMarkdown(false);
        mView.setSource(getFileName(path), mBlob);
    }

    public String getFileName(final String path) {
        if (TextUtils.isEmpty(path))
            return path;

        int lastSlash = path.lastIndexOf('/');
        if (lastSlash != -1 && lastSlash + 1 < path.length())
            return path.substring(lastSlash + 1);
        else
            return path;
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
