package com.stormphoenix.ogit.mvp.ui.activities.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.WindowManager;
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
        setStatusBar();
        setContentView(getLayoutId());
        initializeInjector();
        ButterKnife.bind(this);
        setUpEnterTransitionAnim();
    }

    /**
     * 设置系统状态栏的样式
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
//        tintManager.setStatusBarTintEnabled(true);
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
