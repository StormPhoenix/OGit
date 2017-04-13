package com.stormphoenix.ogit.mvp.presenter.issue;

import android.content.Context;
import android.text.TextUtils;

import com.stormphoenix.httpknife.github.GitIssue;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.IssueInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.IssueView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by StormPhoenix on 17-4-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class IssuePresenter extends BasePresenter<IssueView> {
    private IssueInteractor issueInteractor;
    private Context mContext;

    @Inject
    public IssuePresenter(Context context) {
        super();
        mContext = context;
        issueInteractor = new IssueInteractor(context);
    }

    public void sendAnIssue(String owner, String repo) {
        if (TextUtils.isEmpty(owner) || TextUtils.isEmpty(repo)) {
            mView.showMessage(mContext.getString(R.string.content_is_empty));
            return;
        }
        issueInteractor.sendAnIssue(owner, repo).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitIssue>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitIssue> response) {
                        if (response.isSuccessful()) {
                            mView.onSendIssueSuccess();
                        } else {
                            mView.onSendIssueFailed();
                        }
                    }
                });
    }
}
