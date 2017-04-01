package com.stormphoenix.ogit.mvp.presenter.commits;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.commits.CommitDetailsInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.CommitDetailsView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by StormPhoenix on 17-3-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitDetailsPresenter extends BasePresenter<CommitDetailsView> {
    private Context context;
    private CommitDetailsInteractor mInteractor = null;
    private GitCommit commit;

    private String repoName;
    private String ownerName;
    private String commitSha;

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    @Inject
    public CommitDetailsPresenter(Context context) {
        this.context = context;
        this.mInteractor = new CommitDetailsInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        mView.initListItemView();
        loadCommitDetails();
    }

    public void loadCommitDetails() {
        if (!checkValue()) return;
        mInteractor.loadSingleCommitDetails(ownerName, repoName, commitSha)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitCommit>, BaseUIView>(mView, context.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitCommit> response) {
                        if (response.isSuccessful()) {
                            commit = response.body();
                            mView.loadListData(response.body().getFiles());
                        } else {
                            mView.showMessage(context.getString(R.string.network_error));
                        }
                    }
                });
    }

    private boolean checkValue() {
        if (TextUtils.isEmpty(repoName) || TextUtils.isEmpty(ownerName) || TextUtils.isEmpty(commitSha)) {
            return false;
        }
        return true;
    }
}
