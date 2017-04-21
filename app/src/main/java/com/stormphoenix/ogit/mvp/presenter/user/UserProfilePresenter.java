package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerProfilePresenter;
import com.stormphoenix.ogit.mvp.ui.activities.UserDetailsActivity;
import com.stormphoenix.ogit.mvp.view.UserDetailsView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;
import com.stormphoenix.ogit.utils.TextTools;
import com.stormphoenix.ogit.utils.TimeUtils;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserProfilePresenter extends OwnerProfilePresenter<UserDetailsView> {
    public static final String TAG = UserProfilePresenter.class.getSimpleName();

    private UserInteractor mInteractor = null;
    private GitUser mUser;

    @Inject
    public UserProfilePresenter(Context context) {
        super(context);
        mInteractor = new UserInteractor(mContext);
    }

    public void refreshViewInfo() {
        mView.loadHeaderImage(mUser.getAvatarUrl());
        mView.showProgress();
        mView.setOwnerDescription(mUser.getHtmlUrl());
        mInteractor.loadUser(mUser.getLogin())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<GitUser>>() {
                    @Override
                    public void onCompleted() {
                        mView.stopProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stopProgress();
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(Response<GitUser> response) {
                        if (response.isSuccessful()) {
                            mUser = response.body();
                            setUpUserInfo();
                        } else {
                            mView.showMessage(response.message());
                        }
                        mView.stopProgress();
                    }
                });
        mInteractor.loadStaredCount(mUser.getLogin())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new Subscriber<Response<List<GitRepository>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onNext(Response<List<GitRepository>> response) {
                        RxHttpLog.logResponse(response);
                        if (response.isSuccessful()) {
                            String linkHeader = response.headers().get("Link");
                            Log.e(TAG, "linkHeader = " + linkHeader);
                            if (!TextUtils.isEmpty(linkHeader)) {
                                int count = TextTools.parseListCount(linkHeader);
                                mView.setStaredCount(String.valueOf(count));
                            }
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    public void hasFollowed() {
        if (mUser == null) {
            return;
        }
        mInteractor.hasFollowed(mUser.getLogin())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        if (response.code() == 204) {
                            mView.setIsFollow(true);
                        } else if (response.code() == 404) {
                            mView.setIsFollow(false);
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    public void unFollow() {
        if (mUser == null) {
            return;
        }
        mInteractor.unFollow(mUser.getLogin())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        if (response.isSuccessful()) {
                            mView.setIsFollow(false);
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    public void follow() {
        if (mUser == null) {
            return;
        }
        mInteractor.follow(mUser.getLogin())
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitEmpty>, BaseUIView>(mView, mContext.getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        if (response.isSuccessful()) {
                            mView.setIsFollow(true);
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    private void setUpUserInfo() {
        mView.setFollowersCount(String.valueOf(mUser.getFollowers()));
        mView.setFollowingCount(String.valueOf(mUser.getFollowing()));
        String key, value;

        if (!TextUtils.isEmpty(mUser.getBlog())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.blob));
            label.setValueName(mUser.getBlog());
            label.setImageDrawableResourceId(R.drawable.ic_link_black_24dp);
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(mUser.getEmail())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.email));
            label.setValueName(mUser.getEmail());
            label.setImageDrawableResourceId(R.drawable.ic_email_black_24dp);
            mView.addBaseLabel(label);
        }

        if (!TextUtils.isEmpty(mUser.getCompany())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.company));
            label.setValueName(mUser.getCompany());
            label.setImageDrawableResourceId(R.drawable.ic_home_black_24dp);
            mView.addBaseLabel(label);
        }
        if (!TextUtils.isEmpty(mUser.getLocation())) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.location));
            label.setValueName(mUser.getLocation());
            label.setImageDrawableResourceId(R.drawable.ic_location_black_24dp);
            mView.addBaseLabel(label);
        }
        if (mUser.getCreatedAt() != null) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            label.setKeyName(mContext.getString(R.string.join));
            label.setValueName(TimeUtils.defaultTimeFormat(mUser.getCreatedAt()));
            label.setImageDrawableResourceId(R.drawable.ic_exit_to_app_black_24dp);
            mView.addBaseLabel(label);
        }

        if (mUser.getHtmlUrl() != null) {
            ImageVerticalKeyValueLabel label = new ImageVerticalKeyValueLabel(mContext);
            key = mContext.getString(R.string.person_page);
            value = mUser.getHtmlUrl();
            label.setImageDrawableResourceId(R.drawable.ic_exit_to_app_black_24dp);
            mView.addBaseLabel(createBaseLabel(key, value, R.drawable.ic_exit_to_app_black_24dp));
        }
        mView.addDynamicLabel(createDynamicLabel(mContext.getString(R.string.public_repos), String.valueOf(mUser.getPublicRepos()), R.drawable.ic_created_event_black_24dp));
        mView.addDynamicLabel(createDynamicLabel(mContext.getString(R.string.public_gists), String.valueOf(mUser.getPublicGists()), R.drawable.ic_created_event_black_24dp));
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(GitUser user) {
        this.mUser = user;
        EventBus.getDefault().unregister(this);
        mView.setUpToolbar(mUser.getLogin());
        refreshViewInfo();
        hasFollowed();
    }

    public void startUserDetailsActivity() {
        EventBus.getDefault().postSticky(mUser);
        Intent intent = UserDetailsActivity.getIntent(mContext);
        mContext.startActivity(intent);
    }
}
