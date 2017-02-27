package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;

import com.stormphoenix.ogit.dagger2.InjectorInitializer;

import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class BaseActivity extends AppCompatActivity implements InjectorInitializer {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initializeInjector();
        ButterKnife.bind(this);

//        setUpEnterTransitionAnim();
    }

    private void setUpEnterTransitionAnim() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();//滑出，fade 也可以，什么效果自己上
        slideTransition.setSlideEdge(Gravity.TOP);//滑出的方向
        slideTransition.setInterpolator(new DecelerateInterpolator());
        slideTransition.setDuration(500);//动画持续时间
        getWindow().setEnterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
        getWindow().setReturnTransition(slideTransition);
        getWindow().setReenterTransition(slideTransition);//
    }

    protected abstract int getLayoutId();
}
