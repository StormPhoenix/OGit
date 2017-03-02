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

import com.stormphoenix.ogit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */
public class BreadcrumbView extends HorizontalScrollView implements View.OnClickListener {
    public static String TAG = BreadcrumbView.class.getName();

    private LinearLayout mContainer;
    private List<Breadcrumb> mBreadcrumbs;

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
        mBreadcrumbs = new ArrayList<Breadcrumb>();
        mContainer = new LinearLayout(getContext());
        addView(mContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

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

    public void addBreadcrumb(Breadcrumb breadcrumb) {
        mBreadcrumbs.add(breadcrumb);
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.bread_crumb_view, this, false);
        view.setTag(mBreadcrumbs.size() - 1);
        view.setOnClickListener(this);

        ImageView iv = (ImageView) view.getChildAt(1);
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
        mBreadcrumbs.add(breadcrumb);
        requestLayout();
    }

    public void setOnItemSelectCallback(CrumbItemSelectorCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout: ");
        /**
         * Do something
         */
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

    public static class Breadcrumb {
        private String name;
        private Object attachedInfo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAttachedInfo() {
            return attachedInfo;
        }

        public void setAttachedInfo(Object attachedInfo) {
            this.attachedInfo = attachedInfo;
        }
    }
}
