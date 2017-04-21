package com.stormphoenix.ogit.mvp.presenter.repository;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.repository.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.mvp.view.TreeItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.widget.BreadcrumbView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 获取FoldsFragment需要展示的数据。并且要确保FoldsFragment和BreadcrumbView的同步
 */

public class RepoTreePresenter extends ListItemPresenter<GitTreeItem, List<GitTreeItem>, TreeItemView<GitTreeItem>> implements BreadcrumbView.OnCrumbSelectListener {
    public static final String TAG = RepoTreePresenter.class.getSimpleName();
    private RepoInteractor mInteractor;
    private GitRepository mRepository;
    private String sha;
    private volatile boolean isLoading = false;

    private volatile BreadcrumbTreeActivity.Breadcrumb<GitTreeItem> tempCrumb = null;

    private BreadcrumbTreeActivity.BreadcrumbTreeController controller = null;

    @Inject
    public RepoTreePresenter(Context context) {
        super(context);
        mInteractor = new RepoInteractor(mContext);
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadMoreListItem() {
        // 覆盖父类方法，此处无需加载更多选项
        mView.hideProgress();
    }

    @Override
    protected List<GitTreeItem> transformBody(List<GitTreeItem> body) {
        return body;
    }

    @Override
    protected void makeDataCached(List<GitTreeItem> data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Gson gson = new Gson();
                subscriber.onNext(gson.toJson(data));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                FileCache.saveCacheFile(getCacheType(), s);
            }
        });
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.REPO_TREE;
    }

    @Override
    synchronized public void loadNewlyListItem() {
        // 覆盖父类方法，采用另外一种实现
        // 判断是否正在加载，若是则直接返回
        if (isLoading) {
            return;
        }
        isLoading = true;
        mInteractor.loadRepoTrees(mRepository.getOwner().getLogin(), mRepository.getName(), sha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitTree>>() {
                    @Override
                    public void onStart() {
                        mView.showProgress();
                    }

                    @Override
                    public void onCompleted() {
                        isLoading = false;
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.toString());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(Response<GitTree> response) {
                        if (response.isSuccessful()) {
                            List<GitTreeItem> treeItems = response.body().getTree();
                            Collections.reverse(treeItems);
                            mView.loadNewlyListItem(treeItems);
                        } else if (response.code() == 401) {
                            mView.reLogin();
                        } else if (response.code() == 409) {
                            mView.showMessage(mContext.getString(R.string.no_code_file));
                        } else {
                            mView.showMessage(response.message());
                        }
                        // 加载完毕
                        isLoading = false;
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(GitRepository repository) {
        mRepository = repository;
        sha = mRepository.getDefaultBranch();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected Observable<Response<List<GitTreeItem>>> load(int page) {
        // 返回值为null，无需调用此方法
        return null;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        // 先初始化第一个Breadcrumb，名为 root
        BreadcrumbTreeActivity.Breadcrumb crumb = new BreadcrumbTreeActivity.Breadcrumb();
        crumb.setName("ROOT");
        GitTreeItem item = new GitTreeItem();
        item.setSha(mRepository.getDefaultBranch());
        crumb.setAttachedInfo(item);
        controller.addBreadcrumb(crumb);
    }

    public void startCodeActivity(GitTreeItem values) {
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_CODE);
        bundle.putString(ToolbarActivity.OWNER, mRepository.getOwner().getLogin());
        bundle.putString(ToolbarActivity.REPO, mRepository.getName());
        bundle.putString(ToolbarActivity.BRANCH, mRepository.getDefaultBranch());
        bundle.putString(ToolbarActivity.PATH, controller.getAbsolutePath() + values.getPath());
        mContext.startActivity(ToolbarActivity.newIntent(mContext, bundle));
    }

    @Override
    synchronized public void onItemSelect(int index, BreadcrumbTreeActivity.Breadcrumb crumb) {
        // 实现了 BreadcrumbView接口，用于BreadcrumbView的点击事件
        // 判断当前是否在加载
        Log.e(TAG, "onItemSelect: isLoading " + isLoading);
        if (isLoading) {
            return;
        }
        // 开始真正加载
        isLoading = true;
        GitTreeItem item = (GitTreeItem) crumb.getAttachedInfo();
        sha = item.getSha();
        mInteractor.loadRepoTrees(mRepository.getOwner().getLogin(), mRepository.getName(), sha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitTree>>() {
                    @Override
                    public void onStart() {
                        mView.showProgress();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.toString());
                        mView.hideProgress();
                        isLoading = false;
                    }

                    @Override
                    public void onNext(Response<GitTree> response) {
                        if (response.isSuccessful()) {
                            // 数据请求成功
                            List<GitTreeItem> treeItems = response.body().getTree();
                            Collections.reverse(treeItems);
                            mView.loadNewlyListItem(treeItems);
                            controller.removeFrom(index + 1);
                        } else if (response.code() == 401) {
                            mView.reLogin();
                        } else {
                            mView.showMessage(response.message());
                        }
                        // 加载完毕，回收资源
                        isLoading = false;
                    }
                });
    }

    public void setController(BreadcrumbTreeActivity.BreadcrumbTreeController controller) {
        this.controller = controller;
    }

    // 进入一个新的文件夹
    synchronized public void intoNewBreadcrum(GitTreeItem item) {
        if (isLoading) {
            // 如果当前正在加载，则退出
            return;
        }
        isLoading = true;
        // 新建一个tempCrumb
        tempCrumb = new BreadcrumbTreeActivity.Breadcrumb<GitTreeItem>();
        tempCrumb.setName(item.getPath());
        tempCrumb.setAttachedInfo(item);
        sha = item.getSha();

        // 开始请求新文件内容
        mInteractor.loadRepoTrees(mRepository.getOwner().getLogin(), mRepository.getName(), sha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitTree>>() {
                    @Override
                    public void onStart() {
                        mView.showProgress();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                        isLoading = false;
                        tempCrumb = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.toString());
                        mView.hideProgress();
                        isLoading = false;
                        tempCrumb = null;
                    }

                    @Override
                    public void onNext(Response<GitTree> response) {
                        if (response.isSuccessful()) {
                            // 数据请求成功
                            List<GitTreeItem> treeItems = response.body().getTree();
                            Collections.reverse(treeItems);
                            mView.loadNewlyListItem(treeItems);
                            controller.addBreadcrumb(tempCrumb);
                        } else if (response.code() == 401) {
                            mView.reLogin();
                        } else {
                            mView.showMessage(response.message());
                        }
                        // 加载完毕，回收资源
                        tempCrumb = null;
                        System.gc();
                        isLoading = false;
                    }
                });
    }
}
