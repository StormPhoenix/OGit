package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.OrgDetailsView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;
import com.stormphoenix.ogit.utils.TextTools;
import com.stormphoenix.ogit.utils.TimeUtils;
import com.stormphoenix.ogit.widget.ImageHorizontalKeyValueLabel;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgDetailsPresenter extends BasePresenter<OrgDetailsView> {
    private OrgInteractor mInteractor = null;
    private Context mContext;
    private GitOrganization organization = null;

    @Inject
    public OrgDetailsPresenter(Context context) {
        super();
        mContext = context;
        mInteractor = new OrgInteractor(mContext);
    }

    public void loadOrganizationDetails(String org) {
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

    public void loadMembersCount(String org) {
        mInteractor.loadMembersCount(org).compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<List<GitUser>>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<List<GitUser>> response) {
                        RxHttpLog.logResponse(response);
                        if (response.isSuccessful()) {
                            String linkHeader = response.headers().get("Link");
                            Log.e(TAG, "linkHeader = " + linkHeader);
                            if (!TextUtils.isEmpty(linkHeader)) {
                                int count = TextTools.parseListCount(linkHeader);
                                mView.setMembersCount(String.valueOf(count));
                            }
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void refreshViewInfo() {
        mView.loadOrgHeaderImage(organization.getAvatarUrl());
        mView.setFollowersCount(String.valueOf(organization.getFollowers()));
        mView.setFolloweringCount(String.valueOf(organization.getFollowing()));
        String key = null;
        String value = null;

        if (!TextUtils.isEmpty(organization.getCompany())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.company));
            label.setValueName(organization.getCompany());
            label.setImageDrawableResourceId(R.drawable.ic_arrow_back_white_24dp);
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(organization.getEmail())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.email));
            label.setValueName(organization.getEmail());
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(organization.getDescription())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.description));
            label.setValueName(organization.getDescription());
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(organization.getBlog())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.blob));
            label.setValueName(organization.getBlog());
            label.setImageDrawableResourceId(R.drawable.ic_link_black_24dp);
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(organization.getLocation())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.location));
            label.setValueName(organization.getLocation());
            label.setImageDrawableResourceId(R.drawable.ic_location_black_24dp);
            mView.addBaseLabel(label);
        }

        if (organization.getCreatedAt() != null) {
            key = mContext.getString(R.string.join);
            value = TimeUtils.defaultTimeFormat(organization.getCreatedAt());
            mView.addBaseLabel(createBaseLabel(key, value, R.drawable.ic_exit_to_app_black_24dp));
        }
        if (organization.getHtmlUrl() != null) {
            key = mContext.getString(R.string.github_page);
            value = organization.getHtmlUrl();
            mView.addBaseLabel(createBaseLabel(key, value, R.drawable.ic_exit_to_app_black_24dp));
        }

        mView.addDynamicLabel(createDynamicLabel(mContext.getString(R.string.public_repos), String.valueOf(organization.getPublicRepos()), R.drawable.ic_created_event_black_24dp));
        mView.addDynamicLabel(createDynamicLabel(mContext.getString(R.string.public_gists), String.valueOf(organization.getPublicGists()), R.drawable.ic_created_event_black_24dp));
    }

    private ImageHorizontalKeyValueLabel createDynamicLabel(String key, String value, int resId) {
        ImageHorizontalKeyValueLabel label = new ImageHorizontalKeyValueLabel(mContext);
        label.setValueName(value);
        label.setKeyName(key);
        label.setImageDrawableResourceId(resId);
        return label;
    }

    private ImageVerticalKeyValueLabel createBaseLabel(String key, String value, int resId) {
        ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
        label.setKeyName(key);
        label.setValueName(value);
        label.setImageDrawableResourceId(resId);
        return label;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(GitOrganization organization) {
        this.organization = organization;
        EventBus.getDefault().unregister(this);
        mView.setTitle(this.organization.getLogin());
        loadOrganizationDetails(organization.getLogin());
        loadMembersCount(organization.getLogin());
    }
}
