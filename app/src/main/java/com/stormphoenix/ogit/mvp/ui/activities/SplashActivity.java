package com.stormphoenix.ogit.mvp.ui.activities;

import android.app.ActivityOptions;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.img_logo)
    ImageView mImgLogo;
    @BindView(R.id.activity_splash)
    ConstraintLayout mActivitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplash.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mImgLogo.setColorFilter(this.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_OUT);
        startLogoAnimation();
        timer();
    }

    private void startLogoAnimation() {
        mImgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_in));
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(1000);
//        set.playTogether(ObjectAnimator.ofFloat(mImgLogo, "scaleX", new float[]{1.0F, 1.25F, 0.75F, 1.15F, 1.0F}),
//                ObjectAnimator.ofFloat(mImgLogo, "scaleY", new float[]{1.0F, 0.75F, 1.25F, 0.85F, 1.0F}));
//        set.start();
    }

    private void timer() {
        Observable.timer(4000, TimeUnit.MILLISECONDS)
                .compose(RxJavaCustomTransformer.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, null);

                        if (PreferenceUtils.isLogin(SplashActivity.this)) {
                            ActivityUtils.startActivity(SplashActivity.this, MainActivity.newIntent(SplashActivity.this));
                        } else {
                            startActivity(LoginActivity.newIntent(SplashActivity.this));
//                            ActivityUtils.startActivity(SplashActivity.this, LoginActivity.newIntent(SplashActivity.this));
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
