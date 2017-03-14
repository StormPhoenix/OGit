package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;

import retrofit2.Response;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgsPresenter extends BasePresenter<BaseUIView> {
    private OrgInteractor mInteractor = null;
    private Context mContext;
    private GitOrganization organization = null;

    public OrgsPresenter(Context context) {
        super();
        mContext = context;
        mInteractor = new OrgInteractor(mContext);
    }

    public void loadOrganization(String org) {
        mInteractor.loadOrganization(org)
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitOrganization>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitOrganization> response) {
                        RxHttpLog.logResponse(response);
                        if (response.isSuccessful()) {
                            organization = response.body();
                            refreshViewInfo();
                        } else {
                            Log.e(TAG, "onNext: " + response.message());
                        }
                    }
                });
    }

    private void refreshViewInfo() {
        if (organization.getCompany() != null) {

        }

        if (organization.getEmail() != null) {

        }

        if (organization.getDescription() != null) {

        }

        if (organization.getBlog() != null) {

        }
    }
}
