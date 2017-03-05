package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.model.interactor.GitRepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.WrapperActivity;
import com.stormphoenix.ogit.mvp.ui.component.BreadcrumbView;
import com.stormphoenix.ogit.mvp.view.TreeItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class RepoTreePresenter extends ListItemPresenter<GitTreeItem, TreeItemView<GitTreeItem>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitTreeItem> {
    public static final String TAG = RepoTreePresenter.class.getSimpleName();
    private GitRepoInteractor mInteractor;
    private GitRepository mRepository;
    private String sha;

    @Inject
    public RepoTreePresenter(Context context) {
        super(context);
        mInteractor = new GitRepoInteractor(mContext);
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadMoreListItem() {
        mView.stopRefresh();
    }

    @Override
    public void loadNewlyListItem() {
        mView.startRefresh();
        mInteractor.loadRepoTrees(mRepository.getOwner().getLogin(), mRepository.getName(), sha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitTree>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.getMessage());
                        mView.stopRefresh();
                    }

                    @Override
                    public void onNext(Response<GitTree> gitTreeResponse) {
                        if (gitTreeResponse.isSuccessful()) {
                            List<GitTreeItem> treeItems = gitTreeResponse.body().getTree();
                            Collections.reverse(treeItems);
                            mView.loadNewlyListItem(treeItems);
                        } else if (gitTreeResponse.code() == 401) {
                            mView.reLogin();
                        } else {
                            Log.e(TAG, "onNext: " + gitTreeResponse.code() + " " + gitTreeResponse.message());
                            mView.showMessage(gitTreeResponse.message());
                        }
                        mView.stopRefresh();
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
        return null;
    }

    @NonNull
    private BreadcrumbView.Breadcrumb<GitTreeItem> createBreadcrubm(GitTreeItem values) {
        BreadcrumbView.Breadcrumb<GitTreeItem> crumb = new BreadcrumbView.Breadcrumb();
        crumb.setName(values.getPath());
        crumb.setAttachedInfo(values);
        return crumb;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        BreadcrumbView.Breadcrumb crumb = new BreadcrumbView.Breadcrumb();
        crumb.setName("ROOT");
        crumb.setAttachedInfo(null);
        mView.addBreadcrumb(crumb);
    }

    @Override
    public void onClick(View parentV, View v, Integer position, GitTreeItem values) {
        sha = values.getSha();
        // 判断 GitTreeItem 类型，确定应该进行何种操作
        Log.d(TAG, "onClick: " + values.getType());
        switch (values.getType()) {
            case GitTreeItem.TYPE_BLOB:
                /** 如果点击的文件是文本（Blob）类型 **/
                Log.e(TAG, "onClick: " + mView.getAbsolutPath() + " " + values.getPath());
                startCodeActivity(values);
                break;
            case GitTreeItem.TYPE_TREE:
                /** 如果点击的文件是文件（Tree）类型，则进入下一个文件夹 **/
                // 创建新的 BreadcrumbView，并添加给TreeItemView
                BreadcrumbView.Breadcrumb<GitTreeItem> crumb = createBreadcrubm(values);
                mView.addBreadcrumb(crumb);
                // 设置该文件的sha值，并更新界面
                sha = values.getSha();
                loadNewlyListItem();
                break;
            case GitTreeItem.TYPE_COMMIT:
                break;
        }
    }

    private void startCodeActivity(GitTreeItem values) {
        Bundle bundle = new Bundle();
        bundle.putInt(WrapperActivity.TYPE, WrapperActivity.TYPE_CODE);
        bundle.putString(WrapperActivity.OWNER, mRepository.getOwner().getLogin());
        bundle.putString(WrapperActivity.REPO, mRepository.getName());
        bundle.putString(WrapperActivity.BRANCH, mRepository.getDefaultBranch());
        bundle.putString(WrapperActivity.PATH, mView.getAbsolutPath() + values.getPath());
        mContext.startActivity(WrapperActivity.getIntent(mContext, bundle));
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitTreeItem values) {
        return false;
    }
}
