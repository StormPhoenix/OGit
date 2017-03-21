package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-21.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class PersonsPresenter extends ListItemPresenter<GitUser, List<GitUser>, ListItemView<GitUser>> {
    public PersonsPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitUser> transformBody(List<GitUser> body) {
        return body;
    }
}
