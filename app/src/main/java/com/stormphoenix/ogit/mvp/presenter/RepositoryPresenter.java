package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.RepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.mvp.view.RepositoryView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;
import com.stormphoenix.ogit.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

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
    public void onMainEvent(GitRepository repository) {
        mRepository = repository;
        EventBus.getDefault().unregister(this);
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
                .subscribe(new DefaultUiSubscriber<String, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(String readmeText) {
                        Log.e(TAG, readmeText);
                        mView.loadReadmeHtml(readmeText, mRepository.getHtmlUrl(), mRepository.getDefaultBranch());
                    }
                });
        initRepositoryView();
    }

    private void initRepositoryView() {
        assert mRepository != null;
        initToolbarStatus();
        mView.setDescription(mRepository.getDescription());
        mView.setStarCount(String.valueOf(mRepository.getStargazersCount()));
        mView.setForkCount(String.valueOf(mRepository.getForksCount()));
        mView.setWatchersCount(String.valueOf(mRepository.getWatchers()));
        mView.setBranch(mRepository.getDefaultBranch());
//        mView.setCommitCount(String .valueOf(mRepository.get))
//        mView.setBranch();
    }

    private void initToolbarStatus() {
        assert mRepository != null;
        mView.setToolbarStatus(mRepository.getName(), mRepository.getOwner().getLogin());
    }

    @Override
    public void onDestory() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestory();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mView.finishView();
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contributor_wrapper:
                startContributorActivity();
                break;
            case R.id.code_wrapper:
                startCodeActivity();
                break;
            default:
                break;
        }
    }

    private void startCodeActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putString(BreadcrumbTreeActivity.TITLE, mRepository.getName());
        bundle.putString(BreadcrumbTreeActivity.SUB_TITLE, mRepository.getDefaultBranch());
        ActivityUtils.startActivity(mContext, BreadcrumbTreeActivity.newIntent(mContext, bundle));
    }

    private void startContributorActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_CONTRIBUTOR);
        ActivityUtils.startActivity(mContext, ToolbarActivity.newIntent(mContext, bundle));
    }
}
