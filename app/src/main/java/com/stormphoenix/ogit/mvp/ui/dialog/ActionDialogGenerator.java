package com.stormphoenix.ogit.mvp.ui.dialog;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Developer on 17-1-20.
 * Wang Cheng is a intelligent Android developer.
 */

public class ActionDialogGenerator {
    private MaterialDialog.Builder builder = null;
    private MaterialDialog dialog = null;

    public ActionDialogGenerator(Context context) {
        builder = new MaterialDialog.Builder(context);
    }

    public ActionDialogGenerator title(String title) {
        builder.title(title);
        return this;
    }

    public ActionDialogGenerator onPositive(MaterialDialog.SingleButtonCallback callback) {
        builder.onPositive(callback);
        return this;
    }

    public ActionDialogGenerator customView(View view) {
        builder.customView(view, true);
        return this;
    }

    public ActionDialogGenerator cancelable(boolean cancelable) {
        builder.cancelable(cancelable);
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

    public ActionDialogGenerator setActionButton(DialogAction positive, String string) {
        if (dialog == null) {
            dialog = builder.build();
        }
        dialog.setActionButton(positive, string);
        return this;
    }
}
