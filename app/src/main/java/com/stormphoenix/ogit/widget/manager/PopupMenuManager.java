package com.stormphoenix.ogit.widget.manager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.stormphoenix.ogit.utils.UiUtils;
import com.stormphoenix.ogit.widget.menu.VerticalMenu;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class PopupMenuManager implements View.OnAttachStateChangeListener {
    private VerticalMenu menuView = null;

    private static PopupMenuManager instance;

    private volatile boolean isMenuShowing = false;
    private volatile boolean isMenuDismissing = false;

    /**
     * 单例模式
     *
     * @return
     */
    public static PopupMenuManager getInstance() {
        if (instance == null) {
            instance = new PopupMenuManager();
        }
        return instance;
    }

    private PopupMenuManager() {
    }

    public void toggleMenuFromView(View openView) {
        if (menuView == null) {
            showMenuFromView(openView);
        } else {
            hideMenu();
        }
    }

    synchronized private void showMenuFromView(View openView) {
        if (!isMenuShowing) {
            isMenuShowing = true;
            menuView = new VerticalMenu(openView.getContext());
            menuView.addOnAttachStateChangeListener(this);
            ((ViewGroup) openView.getRootView().findViewById(android.R.id.content)).addView(menuView);

            menuView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    menuView.getViewTreeObserver().removeOnPreDrawListener(this);
                    setupMenuInitialPostion(openView);
                    executeShowAnimation();
                    return false;
                }
            });
        }
    }

    /**
     * 执行菜单弹出动画
     */
    private void executeShowAnimation() {
        menuView.setPivotX(menuView.getWidth());
        menuView.setPivotY(0);
        menuView.setScaleX(0.1f);
        menuView.setScaleY(0.1f);
        menuView.animate()
                .scaleX(1f).scaleY(1f)
                .setDuration(150)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isMenuShowing = false;
                    }
                });
    }

    synchronized private void hideMenu() {
        if (!isMenuDismissing) {
            isMenuDismissing = true;
            executeDismissAnimation();
        }
    }

    private void executeDismissAnimation() {
        menuView.setPivotX(menuView.getWidth());
        menuView.setPivotY(0);
        menuView.animate()
                .scaleX(0.1f).scaleY(0.1f)
                .setDuration(150)
                .setInterpolator(new AccelerateInterpolator())
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (menuView != null) {
                            menuView.dismiss();
                        }
                        isMenuDismissing = false;
                    }
                });
    }

    private void setupMenuInitialPostion(View openView) {
        final int[] openingViewLocation = new int[2];
        openView.getLocationOnScreen(openingViewLocation);
        int additionalBottomMargin = UiUtils.dpToPx(16);

        menuView.setTranslationX(openingViewLocation[0] - menuView.getWidth() + openView.getWidth() / 2);
        menuView.setTranslationY(openingViewLocation[1] + openView.getHeight() / 2);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.e(PopupMenuManager.class.getSimpleName(), "onViewDetachedFromWindow: detach");
        menuView = null;
    }
}
