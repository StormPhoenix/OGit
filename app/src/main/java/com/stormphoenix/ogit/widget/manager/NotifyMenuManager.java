package com.stormphoenix.ogit.widget.manager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.ogit.utils.SystemUtils;
import com.stormphoenix.ogit.widget.menu.NotifyMenu;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class NotifyMenuManager extends RecyclerView.OnScrollListener implements View.OnAttachStateChangeListener {
    private NotifyMenu menuView = null;

    private static NotifyMenuManager instance;

    private volatile boolean isMenuShowing = false;
    private volatile boolean isMenuDismissing = false;
    private String mNotifyMessage;
    private List<GitNotification> mNotifications;

    /**
     * 单例模式
     *
     * @return
     */
    public static NotifyMenuManager getInstance() {
        if (instance == null) {
            instance = new NotifyMenuManager();
        }
        return instance;
    }

    private NotifyMenuManager() {
    }

    public void toggleMenuFromView(View openView) {
        if (menuView == null) {
            showMenuFromView(openView);
        } else {
            hideMenu();
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

    synchronized private void showMenuFromView(View openView) {
        if (!isMenuShowing) {
            isMenuShowing = true;
            menuView = new NotifyMenu(openView.getContext());
            if (!TextUtils.isEmpty(mNotifyMessage)) {
                menuView.setNofityContent(mNotifyMessage, mNotifications);
            }
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

    synchronized private void hideMenu() {
        if (!isMenuDismissing) {
            isMenuDismissing = true;
            executeDismissAnimation();
        }
    }

    public void setNotifyContent(String message, List<GitNotification> notifications) {
        this.mNotifications = notifications;
        this.mNotifyMessage = message;
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
        int additionalBottomMargin = SystemUtils.dpToPx(16);

        menuView.setTranslationX(openingViewLocation[0] - menuView.getWidth() + openView.getWidth() / 2);
        menuView.setTranslationY(openingViewLocation[1] + openView.getHeight() / 2);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.e(NotifyMenuManager.class.getSimpleName(), "onViewDetachedFromWindow: detach");
        menuView = null;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Log.e(NotifyMenuManager.class.getSimpleName(), "onScrollStateChanged: ");
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (menuView != null) {
            hideMenu();
            menuView.setTranslationY(menuView.getTranslationY() - dy);
        }
    }
}
