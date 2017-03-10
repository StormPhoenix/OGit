package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitTree;
import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.mvp.view.TreeItemView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class RepoTreePresenter extends ListItemPresenter<GitTreeItem, TreeItemView<GitTreeItem>> {
    public static final String TAG = RepoTreePresenter.class.getSimpleName();
    private RepoInteractor mInteractor;
    private GitRepository mRepository;
    private String sha;

    @Inject
    public RepoTreePresenter(Context context) {
        super(context);
        mInteractor = new RepoInteractor(mContext);
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadMoreListItem() {
        mView.showProgress();
    }

    @Override
    public void loadNewlyListItem() {
        mInteractor.loadRepoTrees(mRepository.getOwner().getLogin(), mRepository.getName(), sha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitTree>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitTree> response) {
                        if (response.isSuccessful()) {
                            List<GitTreeItem> treeItems = response.body().getTree();
                            Collections.reverse(treeItems);
                            mView.loadNewlyListItem(treeItems);
                        } else if (response.code() == 401) {
                            mView.reLogin();
                        } else {
                            Log.e(TAG, "onNext: " + response.code() + " " + response.message());
                            mView.showMessage(response.message());
                        }
                        mView.hideProgress();
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
    public BreadcrumbTreeActivity.Breadcrumb<GitTreeItem> createBreadcrubm(GitTreeItem values) {
        BreadcrumbTreeActivity.Breadcrumb<GitTreeItem> crumb = new BreadcrumbTreeActivity.Breadcrumb();
        crumb.setName(values.getPath());
        crumb.setAttachedInfo(values);
        return crumb;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        BreadcrumbTreeActivity.Breadcrumb crumb = new BreadcrumbTreeActivity.Breadcrumb();
        crumb.setName("ROOT");
        GitTreeItem item = new GitTreeItem();
        item.setSha(mRepository.getDefaultBranch());
        crumb.setAttachedInfo(item);
        mView.addBreadcrumb(crumb);
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public void startCodeActivity(GitTreeItem values) {
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_CODE);
        bundle.putString(ToolbarActivity.OWNER, mRepository.getOwner().getLogin());
        bundle.putString(ToolbarActivity.REPO, mRepository.getName());
        bundle.putString(ToolbarActivity.BRANCH, mRepository.getDefaultBranch());
        bundle.putString(ToolbarActivity.PATH, mView.getAbsolutPath() + values.getPath());
        mContext.startActivity(ToolbarActivity.newIntent(mContext, bundle));
    }
}
