package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class EventsPresenter extends ListItemPresenter<GitEvent, List<GitEvent>, ListItemView<GitEvent>> {
    public EventsPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitEvent> transformBody(List<GitEvent> body) {
        return body;
    }

    public abstract void startOwnerProfileActivity();

    public abstract void startRepoDetailsActivity();
}
