package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.interactor.GitPersonInfoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.StarredView;
import com.stormphoenix.ogit.shares.PreferenceUtils;
import com.stormphoenix.ogit.shares.RxJavaCustomTransformer;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class StarredPresenter extends BasePresenter<StarredView> {

    private Context mContext;
    private GitPersonInfoInteractor mInfoInfoInteractor;

    @Inject
    public StarredPresenter(Context context) {
        mContext = context;
        mInfoInfoInteractor = new GitPersonInfoInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        loadStarredRepository(0);
    }

    public void loadStarredRepository(int page) {
        mInfoInfoInteractor.loadStarredRepository(PreferenceUtils.getUsername(mContext), page)
                .compose(RxJavaCustomTransformer.<List<GitRepository>>defaultSchedulers())
                .subscribe(new Subscriber<List<GitRepository>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(List<GitRepository> gitRepositories) {
                        mView.initGitRepositoryList(gitRepositories);
                    }
                });
    }
}