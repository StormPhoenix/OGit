package com.stormphoenix.ogit.mvp.ui.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mActivitySplash.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mImgLogo.setColorFilter(this.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_OUT);
        timer();
    }

    private void timer() {
        Observable.timer(4000, TimeUnit.MILLISECONDS)
                .compose(RxJavaCustomTransformer.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        if (PreferenceUtils.isLogin(SplashActivity.this)) {
                            ActivityUtils.startActivity(SplashActivity.this, MainActivity.newIntent(SplashActivity.this));
                        } else {
                            startActivity(LoginActivity.newIntent(SplashActivity.this));
                        }
                        finish();
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
