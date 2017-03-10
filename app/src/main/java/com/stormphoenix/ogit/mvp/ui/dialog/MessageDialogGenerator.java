package com.stormphoenix.ogit.mvp.ui.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Developer on 17-1-20.
 * Wang Cheng is a intelligent Android developer.
 */

public class MessageDialogGenerator {
    private MaterialDialog.Builder builder = null;
    private MaterialDialog dialog = null;

    public MessageDialogGenerator(Context context) {
        builder = new MaterialDialog.Builder(context);
    }

    public MessageDialogGenerator title(String title) {
        builder.title(title);
        return this;
    }

    public MessageDialogGenerator content(String content) {
        builder.content(content);
        return this;
    }

    public void cancelable(boolean cancelable) {
        builder.cancelable(cancelable);
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
