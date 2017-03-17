package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitToken;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.TokenInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.LoginView;
import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.shares.rx.subscribers.DefaultUiSubscriber;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public static final String TAG = "LoginPresenter";

    private TokenInteractor mTokenInteractor;

    private Context mContext;

    @Inject
    public LoginPresenter(Context context) {
        mContext = context;
        mTokenInteractor = new TokenInteractor(mContext);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }

    public void listToken(final String username, final String password) {
        mTokenInteractor.listToken(username, password)
                .flatMap(new Func1<Response<List<GitToken>>, Observable<Response<GitEmpty>>>() {
                    @Override
                    public Observable<Response<GitEmpty>> call(Response<List<GitToken>> listResponse) {
                        for (GitToken token : listResponse.body()) {
                            if (TokenInteractor.TOKEN_NOTE.equals(token.getNote())) {
                                return mTokenInteractor.removeToken(username, password, String.valueOf(token.getId()));
                            }
                        }
                        return Observable.empty();
                    }
                }).compose(RxJavaCustomTransformer.<Response<GitEmpty>>defaultSchedulers())
                .subscribe(new Subscriber<Response<GitEmpty>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(Response<GitEmpty> response) {
                        if (response.code() == 204) {
                            login(username, password);
                        } else {
                            mView.hideProgress();
                            mView.showMessage(mContext.getString(R.string.login_failed));
                        }
                    }
                });
    }

    /**
     * 登录Github并获取Token
     *
     * @param username 用户名
     * @param password 密码
     */
    public void login(final String username, final String password) {
        // 判断输入信息是否有为空
        if (!checkValue(username, password)) {
            mView.showMessage(mContext.getString(R.string.username_or_password_is_empty));
            return;
        }

        mView.showProgress();
        mTokenInteractor.createToken(username, password)
                .compose(RxJavaCustomTransformer.<Response<GitToken>>defaultSchedulers())
                .subscribe(new Subscriber<Response<GitToken>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(Response<GitToken> response) {
                        if (response.isSuccessful()) {
                            saveToken(username, password, response.body());
                            loadUser();
                        } else if (response.code() == 401) {
                            Log.i(TAG, "Token created fail: username or password is incorrect");
                            Log.i(TAG, response.toString());
                            mView.hideProgress();
                            mView.showMessage(mContext.getResources().getString(R.string.username_or_password_incorrect));
                        } else if (response.code() == 403) {
                            Log.i(TAG, "Token created fail: auth over-try");
                            mView.hideProgress();
                            mView.showMessage(mContext.getResources().getString(R.string.unknown_error));
                        } else if (response.code() == 422) {
                            Log.i(TAG, "Token created fail: try to delete existing token");
                            listToken(username, password);
                        } else {
                            Log.e(TAG, "onNext: " + response.code() + " " + response.message());
                            mView.showMessage(mContext.getResources().getString(R.string.unknown_error));
                        }
                    }
                });
    }

    /**
     * 若登录成功，则加载用户信息
     */
    public void loadUser() {
        mTokenInteractor.loadUser(PreferenceUtils.getUsername(mContext))
                .compose(RxJavaCustomTransformer.defaultSchedulers())
                .subscribe(new DefaultUiSubscriber<Response<GitUser>, BaseUIView>(mView, mContext.getResources().getString(R.string.network_error)) {
                    @Override
                    public void onNext(Response<GitUser> response) {
                        mView.hideProgress();
                        if (response.isSuccessful()) {
                            saveUser(response.body());
                            mView.onLoginSuccess();
                        } else {
                            mView.showMessage(response.message());
                        }
                        mView.hideProgress();
                    }
                });
    }

    private void saveUser(GitUser user) {
        PreferenceUtils.putString(mContext, PreferenceUtils.AVATAR_URL, user.getAvatarUrl());
    }

    /**
     * 如果登录成功，则保存当前用户的信息和Token
     *
     * @param token 需要保存的token值
     */
    private void saveToken(String username, String password, GitToken token) {
        PreferenceUtils.putString(mContext, PreferenceUtils.USERNAME, username);
        PreferenceUtils.putString(mContext, PreferenceUtils.PASSWORD, password);
        PreferenceUtils.putString(mContext, PreferenceUtils.TOKEN, token.getToken());
        PreferenceUtils.putBoolean(mContext, PreferenceUtils.IS_LOGIN, true);
        Log.e(TAG, "PreferenceUtils: Username : " + username + '\n'
                + "Password : " + password + '\n' + "Token : " + token);
    }

    private boolean checkValue(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }
}
