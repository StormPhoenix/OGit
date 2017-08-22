package com.stormphoenix.ogit.mvp.ui.activities;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.img_logo)
    ImageView mImgLogo;
    @BindView(R.id.activity_splash)
    RelativeLayout mActivitySplash;

    private static Activity instance = null;

    public static void finishSplash() {
        if (instance != null) {
            instance.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        instance = this;
//        mActivitySplash.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mImgLogo.setColorFilter(this.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_OUT);
//        if (OGitApplication.instance.isFirstIn) {
//            OGitApplication.instance.isFirstIn = false;
        timer(3000);
//        } else {
//            timer(0);
//        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void timer(long duration) {
        Observable.timer(duration, TimeUnit.MILLISECONDS)
                .compose(RxJavaCustomTransformer.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        if (PreferenceUtils.isLogin(SplashActivity.this)) {
                            ActivityUtils.startActivity(SplashActivity.this, MainActivity.newIntent(SplashActivity.this));
                        } else {
                            startActivity(LoginActivity.newIntent(SplashActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initializeInjector() {
    }
}
