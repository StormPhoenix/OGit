package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.model.interactor.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.UserDetailsView;
import com.stormphoenix.ogit.utils.ImageUtils;
import com.stormphoenix.ogit.shares.rx.RxHttpLog;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.utils.TextTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserDetailsPresenter extends BasePresenter<UserDetailsView> {
    public static final String TAG = UserDetailsPresenter.class.getSimpleName();

    private final SimpleDateFormat timeFormater;
    private UserInteractor mInteractor = null;
    private Context mContext;
    private GitUser mUser;

    @Inject
    public UserDetailsPresenter(Context context) {
        super();
        mContext = context;
        mInteractor = new UserInteractor(mContext);
        timeFormater = new SimpleDateFormat("hh:mm MMM dd, yyyy", Locale.UK);
    }

    public void loadUserInfo() {
        mView.showProgress();
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
                                int count = TextTools.parseStaredCount(linkHeader);
                                mView.setStaredCount(String.valueOf(count));
                            }
                        } else {
                            mView.showMessage(response.message());
                        }
                    }
                });
    }

    private void setUpUserInfo() {
        mView.setFollowers(String.valueOf(mUser.getFollowers()));
        mView.setFollowings(String.valueOf(mUser.getFollowing()));
        mView.setEmail(mUser.getEmail());
        mView.setLocation(mUser.getLocation());
        mView.setJoinTime(timeFormater.format(mUser.getCreatedAt()));
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(GitUser user) {
        this.mUser = user;
        EventBus.getDefault().unregister(this);
        mView.setUpToolbar(mUser.getLogin());
        loadUserInfo();
        ImageUtils.getInstance().displayImage(mUser.getAvatarUrl(), mView.getHeadImageView());
    }
}
