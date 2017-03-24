package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.mvp.view.RepositoryView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;
import com.stormphoenix.ogit.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */
public class RepositoryPresenter extends BasePresenter<RepositoryView> {
    public static String TAG = RepositoryPresenter.class.getName();

    private Context mContext;
    private RepoInteractor mInteractor = null;

    private GitRepository mRepository;
    private List<GitBranch> mBranches;

    @Inject
    public RepositoryPresenter(Context context) {
        mContext = context;
        mInteractor = new RepoInteractor(mContext);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(GitRepository repository) {
        mRepository = repository;
        EventBus.getDefault().unregister(this);
        if (!isRepoFragmentary(mRepository)) {
            loadMoreRepoDetails();
            initRepositoryView();
        } else {
            // the Repository is fragmentary, we must get it from the net
            mInteractor.loadRepository(mRepository.getUrl())
                    .compose(RxJavaCustomTransformer.defaultSchedulers())
                    .subscribe(new DefaultUiSubscriber<Response<GitRepository>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                        @Override
                        public void onNext(Response<GitRepository> response) {
                            if (response.isSuccessful()) {
                                mRepository = response.body();
                                loadMoreRepoDetails();
                                initRepositoryView();
                            } else {
                                mView.showMessage(mContext.getString(R.string.network_error));
                            }
                        }
                    });
        }
    }

    private void loadMoreRepoDetails() {
        hasStared();
        mInteractor.loadRepositoryBranch(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<List<GitBranch>>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<List<GitBranch>> response) {
                        if (response.isSuccessful()) {
                            mBranches = response.body();
                        }
                    }
                });

        mInteractor.loadReadmeHtml(mRepository.getOwner().getLogin(),
                mRepository.getName(),
                mRepository.getDefaultBranch()
        ).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadReadmeHtml(null, null, null);
                    }

                    @Override
                    public void onNext(String readmeText) {
                        Log.e(TAG, readmeText);
                        mView.loadReadmeHtml(readmeText, mRepository.getHtmlUrl(), mRepository.getDefaultBranch());
                    }
                });
    }

    /**
     * 判断GitRepository信息是否完整
     *
     * @param mRepository
     * @return
     */
    private boolean isRepoFragmentary(GitRepository mRepository) {
        return mRepository.getOwner() == null;
    }

    private void initRepositoryView() {
        assert mRepository != null;
        initToolbarStatus();
        mView.setDescription(mRepository.getDescription());
        mView.setStarCount(String.valueOf(mRepository.getStargazersCount()));
        mView.setForkCount(String.valueOf(mRepository.getForksCount()));
        mView.setWatchersCount(String.valueOf(mRepository.getWatchers()));
        mView.setBranch(mRepository.getDefaultBranch());
        mView.setIsForked(mRepository.isFork());
//        mView.setCommitCount(String .valueOf(mRepository.get))
//        mView.setBranch();
    }

    private void initToolbarStatus() {
        assert mRepository != null;
        mView.setToolbarStatus(mRepository.getName(), mRepository.getOwner().getLogin());
    }

    public void hasStared() {
        mInteractor.hasStared(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        if (response.code() == 204) {
                            mView.setIsStared(true);
                        } else {
                            mView.setIsStared(false);
                        }
                    }
                });
    }

    public void fork() {
        if (mRepository.isFork()) {
            mView.showMessage(mContext.getString(R.string.repo_has_been_forked));
            return;
        }
        mView.startForking();
        mInteractor.fork(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitRepository>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitRepository> response) {
                        mRepository.setFork(true);
                        mView.hideProgress();
                        mView.showMessage(mContext.getString(R.string.fork_success));
                        mView.setIsForked(true);
                        mView.stopForking();
                    }
                });
    }

    @Override
    public void onDestory() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestory();
    }

    public void startCodeActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putString(BreadcrumbTreeActivity.TITLE, mRepository.getName());
        bundle.putString(BreadcrumbTreeActivity.SUB_TITLE, mRepository.getDefaultBranch());
        ActivityUtils.startActivity(mContext, BreadcrumbTreeActivity.newIntent(mContext, bundle));
    }

    public void startContributorActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_CONTRIBUTOR);
        ActivityUtils.startActivity(mContext, ToolbarActivity.newIntent(mContext, bundle));
    }

    public void unstar() {
        mInteractor.unstar(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        RxHttpLog.logResponse(response);
                        if (response.code() == 204) {
                            mView.setIsStared(false);
                        }
                    }
                });
    }

    public void star() {
        mInteractor.star(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        RxHttpLog.logResponse(response);
                        if (response.code() == 204) {
                            mView.setIsStared(true);
                        }
                    }
                });
    }

    public void startCommitsActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_COMMITS);
        bundle.putString(ToolbarActivity.SUB_TITLE, mRepository.getName());
        ActivityUtils.startActivity(mContext, ToolbarActivity.newIntent(mContext, bundle));
    }
}

