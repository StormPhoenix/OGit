package com.stormphoenix.ogit.mvp.ui.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.mvp.ui.activities.BreadcrumbTreeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */
public class BreadcrumbView<T> extends HorizontalScrollView implements View.OnClickListener, BreadcrumbTreeActivity.BreadcrumbTreeController {
    public static String TAG = BreadcrumbView.class.getName();

    // 包裹Breadcrumb
    private LinearLayout mContainer;
    // Breadcrumb 数据
    private List<Breadcrumb<T>> mBreadcrumbs;

    // 外界控制 BreadcrumbView 的接口
    private CrumbItemSelectorCallback callback;

    public BreadcrumbView(Context context) {
        super(context);
        init();
    }

    public BreadcrumbView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BreadcrumbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BreadcrumbView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化面包屑布局的内容
     */
    private void init() {
        setMinimumHeight((int) getResources().getDimension(R.dimen.breadcrumb_min_height));
        setClipToPadding(true);
        mBreadcrumbs = new ArrayList<Breadcrumb<T>>();
        mContainer = new LinearLayout(getContext());
        addView(mContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void removeFrom(int index) {
        if (index < 0 || index >= mBreadcrumbs.size()) {
            Log.e(TAG, "removeFrom: Index out of bound.");
            return;
        }

        for (int i = index; i < mBreadcrumbs.size(); i++) {
            mBreadcrumbs.remove(i);
            mContainer.removeViewAt(i);
        }
    }

    @Override
    public void addBreadcrumb(Breadcrumb breadcrumb) {
        mBreadcrumbs.add(breadcrumb);
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.bread_crumb_view, this, false);
        view.setTag(mBreadcrumbs.size() - 1);
        view.setOnClickListener(this);

        ImageView iv = (ImageView) view.getChildAt(0);
        Drawable arrow = getResources().getDrawable(R.drawable.ic_right_arrow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (arrow != null) {
                arrow.setAutoMirrored(true);
            }
        }
        iv.setImageDrawable(arrow);
//        iv.setVisibility(View.GONE);

        mContainer.addView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        requestLayout();
        refreshAllBreadcrumb();
    }

    private void refreshAllBreadcrumb() {
        for (int i = 0; i < mBreadcrumbs.size(); i++) {
            Breadcrumb crumb = mBreadcrumbs.get(i);
            refreshBreadcrumb(mContainer.getChildAt(i), i != 0)
                    .setText(crumb.getName());
        }
    }

    private TextView refreshBreadcrumb(View view, boolean isArrowVisible) {
        LinearLayout child = (LinearLayout) view;
        TextView tv = (TextView) child.getChildAt(1);
//        tv.setTextColor(getResources().getColor(isActive ? R.color.crumb_active : R.color.crumb_inactive));
        ImageView iv = (ImageView) child.getChildAt(0);
//        setAlpha(iv, isActive ? 255 : 109);
        if (isArrowVisible) {
            iv.setVisibility(VISIBLE);
        } else {
            iv.setVisibility(INVISIBLE);
        }
        return tv;
    }

    @Override
    public String getAbsolutePath() {
        StringBuilder builder = new StringBuilder();
        for (int index = 1; index < mBreadcrumbs.size(); index++) {
            builder.append(mBreadcrumbs.get(index).getName()).append(File.separator);
        }
        return builder.toString();
    }

    public void setOnItemSelectCallback(CrumbItemSelectorCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout: ");
        View child = mContainer.getChildAt(mBreadcrumbs.size() - 1);
        if (child != null) {
            smoothScrollTo(child.getLeft(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (callback != null) {
            Log.e(TAG, "onClick: click index : " + (Integer) v.getTag());
            callback.onItemSelect(mBreadcrumbs.get((Integer) v.getTag()));
        }
    }

    public interface CrumbItemSelectorCallback {
        void onItemSelect(Breadcrumb crumb);
    }

    public static class Breadcrumb<T> {
        private String name;
        private T attachedInfo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public T getAttachedInfo() {
            return attachedInfo;
        }

        public void setAttachedInfo(T attachedInfo) {
            this.attachedInfo = attachedInfo;
        }
    }
}
