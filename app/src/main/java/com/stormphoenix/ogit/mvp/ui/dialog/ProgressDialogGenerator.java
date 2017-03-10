package com.stormphoenix.ogit.mvp.ui.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Developer on 17-1-20.
 * Wang Cheng is a intelligent Android developer.
 */

public class ProgressDialogGenerator {
    private MaterialDialog.Builder builder = null;
    private MaterialDialog dialog = null;

    public ProgressDialogGenerator(Context context) {
        builder = new MaterialDialog.Builder(context);
    }

    public ProgressDialogGenerator title(String title) {
        builder.title(title);
        return this;
    }

    public ProgressDialogGenerator content(String content) {
        builder.content(content);
        return this;
    }

    public void cancelable(boolean cancelable) {
        builder.cancelable(cancelable);
    }

    public ProgressDialogGenerator horizontalProgress(int maxValue) {
        builder.progress(false, maxValue);
        return this;
    }

    public ProgressDialogGenerator circularProgress() {
        builder.progress(true, 0);
        return this;
    }

    public void show() {
        if (dialog == null) {
            dialog = builder.build();
        }
        dialog.show();
    }

    public void cancel() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
