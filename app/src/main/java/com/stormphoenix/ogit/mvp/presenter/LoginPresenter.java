package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.httpknife.github.GitEmpty;
import com.stormphoenix.httpknife.github.GitToken;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.model.interactor.GitTokenInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.BasePresenter;
import com.stormphoenix.ogit.mvp.view.LoginView;
import com.stormphoenix.ogit.shares.PreferenceUtils;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

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

    private Context mContext;
    private GitTokenInteractor mTokenInteractor;

    private String username;
    private String password;

    @Inject
    public LoginPresenter(Context context) {
        mContext = context;
        mTokenInteractor = new GitTokenInteractor(mContext);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        mView.initToolbar(mContext.getString(R.string.login));
    }

    public void listToken(final String username, final String password) {
        mTokenInteractor.listToken(username, password)
                .flatMap(new Func1<Response<List<GitToken>>, Observable<Response<GitEmpty>>>() {
                    @Override
                    public Observable<Response<GitEmpty>> call(Response<List<GitToken>> listResponse) {
                        for (GitToken token : listResponse.body()) {
                            if (GitTokenInteractor.TOKEN_NOTE.equals(token.getNote())) {
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
                    }

                    @Override
                    public void onNext(Response<GitEmpty> gitEmptyResponse) {
                        if (gitEmptyResponse.code() == 204) {
                            login(username, password);
                        } else {
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
        mTokenInteractor.createGitToken(username, password)
                .compose(RxJavaCustomTransformer.<Response<GitToken>>defaultSchedulers())
                .subscribe(new Subscriber<Response<GitToken>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(mContext.getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(Response<GitToken> gitTokenResponse) {
                        if (gitTokenResponse.isSuccessful()) {
                            saveUserConfig(gitTokenResponse);
                            PreferenceUtils.putBoolean(mContext, PreferenceUtils.IS_LOGIN, true);
                            mView.onLoginSuccess();
                        } else if (gitTokenResponse.code() == 401) {
                            Log.i(TAG, "Token created fail: username or password is incorrect");
                            mView.showMessage(gitTokenResponse.toString());
                            mView.showMessage(mContext.getResources().getString(R.string.unknown_error));
                        } else if (gitTokenResponse.code() == 403) {
                            Log.i(TAG, "Token created fail: auth over-try");
                            mView.showMessage(mContext.getResources().getString(R.string.unknown_error));
                        } else if (gitTokenResponse.code() == 422) {
                            Log.i(TAG, "Token created fail: try to delete existing token");
                            listToken(username, password);
                        } else {
                            Log.e(TAG, "onNext: " + gitTokenResponse.code());
                            mView.showMessage(mContext.getResources().getString(R.string.unknown_error));
                        }
                    }
                });
    }

    private void saveUserConfig(Response<GitToken> gitTokenResponse) {
        String token = gitTokenResponse.body().getToken();
        PreferenceUtils.putString(mContext, PreferenceUtils.USERNAME, username);
        PreferenceUtils.putString(mContext, PreferenceUtils.PASSWORD, password);
        PreferenceUtils.putString(mContext, PreferenceUtils.TOKEN, token);
        Log.e(TAG, "PreferenceUtils: Username : " + username + '\n'
                + "Password : " + password + '\n' + "Token : " + token);
    }

    public void onClick(int resId) {
        switch (resId) {
            case R.id.btn_login:
                username = mView.getUsernameText();
                password = mView.getPasswordText();
                if (checkValue()) {
                    login(username, password);
                } else {
                    mView.showMessage(mContext.getString(R.string.username_or_password_is_empty));
                }
                break;
            default:
                mView.showMessage(mContext.getString(R.string.unknown_error));
                break;
        }
    }

    private boolean checkValue() {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }
}
