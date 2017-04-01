package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-21.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class OwnerPresenter extends ListItemPresenter<GitUser, List<GitUser>, ListItemView<GitUser>> {
    public OwnerPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }
}
