package com.stormphoenix.ogit.shares;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.stormphoenix.ogit.utils.ViewUtils;

import java.text.NumberFormat;

/**
 * Created by StormPhoenix on 17-3-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class ViewUpdater {
    public static final NumberFormat FORMAT_INT = NumberFormat.getIntegerInstance();

    public View view;
    public View[] childrenViews;

    public ViewUpdater() {
    }

    /**
     * 初始化view里面所有的子View
     *
     * @param view        用来初始化子View的view
     * @param childLayout 要初始化的子view的布局id
     * @return
     */
    public View initialize(View view, int[] childLayout) {
        View[] views = new View[childLayout.length];

        // 根据id查找到所有的子View
        for (int i = 0; i < childLayout.length; i++) {
            views[i] = view.findViewById(childLayout[i]);
        }

        // 子views存入view的标记
        view.setTag(views);
        this.view = view;
        this.childrenViews = views;
        return view;
    }

    /**
     * 更换当前ViewUpdater所处理的View
     *
     * @param view
     */
    public void setCurrentView(View view) {
        this.view = view;
        this.childrenViews = getchildrenViews(view);
    }

    /**
     * 获取一个view的所有子View，前提是该View曾被ViewUpdater处理过
     *
     * @param parentView
     * @return
     */
    public View[] getchildrenViews(View parentView) {
        return (View[]) ((View[]) parentView.getTag());
    }

    public TextView textView(int childViewIndex) {
        return (TextView) this.childrenViews[childViewIndex];
    }

    public TextView textView(View parentView, int childViewIndex) {
        return (TextView) this.getchildrenViews(parentView)[childViewIndex];
    }

    public ImageView imageView(int childViewIndex) {
        return (ImageView) this.childrenViews[childViewIndex];
    }

    public ImageView imageView(View parentView, int childViewIndex) {
        return (ImageView) this.getchildrenViews(parentView)[childViewIndex];
    }

    public <V extends View> V view(int childViewIndex) {
        return (V) this.childrenViews[childViewIndex];
    }

    public <V extends View> V view(View parentView, int childViewIndex) {
        return (V) this.getchildrenViews(parentView)[childViewIndex];
    }

    public TextView setText(int childViewIndex, CharSequence text) {
        TextView textView = this.textView(childViewIndex);
        textView.setText(text);
        return textView;
    }

    public TextView setText(View parentView, int childViewIndex, CharSequence text) {
        TextView textView = this.textView(parentView, childViewIndex);
        textView.setText(text);
        return textView;
    }

    public TextView setText(int childViewIndex, int resourceId) {
        TextView textView = this.textView(childViewIndex);
        textView.setText(resourceId);
        return textView;
    }

    public TextView setText(View parentView, int childViewIndex, int resourceId) {
        TextView textView = this.textView(parentView, childViewIndex);
        textView.setText(resourceId);
        return textView;
    }

    public TextView setNumber(int childViewIndex, long number) {
        TextView textView = this.textView(childViewIndex);
        textView.setText(FORMAT_INT.format(number));
        return textView;
    }

    public TextView setNumber(View parentView, int childViewIndex, long number) {
        TextView textView = this.textView(parentView, childViewIndex);
        textView.setText(FORMAT_INT.format(number));
        return textView;
    }

    public <T> T getView(int childViewIndex, Class<T> childViewClass) {
        return (T) this.childrenViews[childViewIndex];
    }

    public <T> T getView(View parentView, int childViewIndex, Class<T> childViewClass) {
        return (T) this.getchildrenViews(parentView)[childViewIndex];
    }

    public View setGone(int childViewIndex, boolean gone) {
        return ViewUtils.setGone(this.view(childViewIndex), gone);
    }

    public View setGone(View parentView, int childViewIndex, boolean gone) {
        return ViewUtils.setGone(this.view(parentView, childViewIndex), gone);
    }

    public CompoundButton setChecked(int childViewIndex, boolean checked) {
        CompoundButton button = (CompoundButton) this.view(childViewIndex);
        button.setChecked(checked);
        return button;
    }

    public CompoundButton setChecked(View parentView, int childViewIndex, boolean checked) {
        CompoundButton button = (CompoundButton) this.view(parentView, childViewIndex);
        button.setChecked(checked);
        return button;
    }

    public TextView setVisibleText(int childViewIndex, CharSequence text) {
        TextView view = this.textView(childViewIndex);
        view.setText(text);
        ViewUtils.setGone(view, TextUtils.isEmpty(text));
        return view;
    }

    public TextView setVisibleText(View parentView, int childViewIndex, CharSequence text) {
        TextView view = this.textView(parentView, childViewIndex);
        view.setText(text);
        ViewUtils.setGone(view, TextUtils.isEmpty(text));
        return view;
    }

    private CharSequence formatRelativeTimeSpan(long time) {
        return DateUtils.getRelativeTimeSpanString(time);
    }

    public TextView setRelativeTimeSpan(int childViewIndex, long time) {
        return this.setText(childViewIndex, this.formatRelativeTimeSpan(time));
    }

    public TextView setRelativeTimeSpan(View parentView, int childViewIndex, long time) {
        return this.setText(parentView, childViewIndex, this.formatRelativeTimeSpan(time));
    }
}
