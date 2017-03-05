package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.stormphoenix.httpknife.github.GitBranch;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.GitRepoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;
import com.stormphoenix.ogit.mvp.ui.activities.WrapperActivity;
import com.stormphoenix.ogit.mvp.view.RepositoryView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

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
    private GitRepoInteractor mInteractor = null;

    private GitRepository mRepository;
    private List<GitBranch> mBranches;

    @Inject
    public RepositoryPresenter(Context context) {
        mContext = context;
        mInteractor = new GitRepoInteractor(mContext);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(GitRepository repository) {
        mRepository = repository;
        mInteractor.loadRepositoryBrances(mRepository.getOwner().getLogin(), mRepository.getName())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<List<GitBranch>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Response<List<GitBranch>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            mBranches = listResponse.body();
                        }
                    }
                });
        initRepositoryView();
        EventBus.getDefault().unregister(this);
    }

    private void initRepositoryView() {
        assert mRepository != null;
        initToolbarStatus();
        mView.setDescription(mRepository.getDescription());
        mView.setStarCount(String.valueOf(mRepository.getStargazersCount()));
        mView.setForkCount(String.valueOf(mRepository.getForksCount()));
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
        bundle.putInt(BreadcrumbTreeActivity.TYPE, BreadcrumbTreeActivity.TYPE_FOLD_LIST);
        bundle.putString(BreadcrumbTreeActivity.TITLE, mRepository.getName());
        bundle.putString(BreadcrumbTreeActivity.SUB_TITLE, mRepository.getDefaultBranch());
        mContext.startActivity(BreadcrumbTreeActivity.getIntent(mContext, bundle));
    }

    private void startContributorActivity() {
        EventBus.getDefault().postSticky(mRepository);
        Bundle bundle = new Bundle();
        bundle.putInt(WrapperActivity.TYPE, WrapperActivity.TYPE_CONTRIBUTOR);
        mContext.startActivity(WrapperActivity.getIntent(mContext, bundle));
    }
}
