package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.mvp.model.api.GithubBlobService;
import com.stormphoenix.ogit.mvp.model.interactor.GitRepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.CodeView;
import com.stormphoenix.ogit.shares.Constants;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

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
    private GitRepoInteractor mInteractor;

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
        mInteractor = new GitRepoInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        mView.initWebView();
        loadBlob();
    }

    public void loadBlob() {
        mView.startRefresh();
        Log.e(TAG, "loadBlob: " + owner + " " + repo + " " + path + " " + branch);

        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GithubBlobService service = retrofit.create(GithubBlobService.class);
        service.loadGitBlob(owner, repo, path, branch)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<GitBlob>() {
                    @Override
                    public void onCompleted() {
                        mView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stopRefresh();
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(GitBlob blob) {
                        mBlob = blob;
                        loadCodeContent();
                    }
                });
//        mInteractor.loadGitBlob(owner, repo, path, branch)
//                .compose(RxJavaCustomTransformer.defaultSchedulers())
//                .subscribe(new Subscriber<GitBlob>() {
//                    @Override
//                    public void onCompleted() {
//                        mView.stopRefresh();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(GitBlob blob) {
//                        mBlob = blob;
//                        loadCodeContent();
//                    }
//                });
    }

    private void loadCodeContent() {
        if (mBlob != null) {
            mInteractor.loadCodeContent(mBlob.getDownloadUrl())
                    .compose(RxJavaCustomTransformer.defaultSchedulers())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            mView.stopRefresh();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onFailure: " + e.toString());
                            mView.stopRefresh();
                            mView.showMessage(e.toString());
                        }

                        @Override
                        public void onNext(String s) {
                            mView.stopRefresh();
                            mView.loadCodeContent(s);
                        }
                    });

        }
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
