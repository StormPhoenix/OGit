package com.stormphoenix.ogit.mvp.presenter.base;

import android.content.Context;
import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-16.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class BaseRepoListPresenter extends ListItemPresenter<GitRepository, List<GitRepository>, ListItemView<GitRepository>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitRepository> {

    @Override
    public void onClick(View parentV, View v, Integer position, GitRepository values) {
        EventBus.getDefault().postSticky(values);
        startRepoDetailsActivity();
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitRepository values) {
        return false;
    }

    public BaseRepoListPresenter(Context context) {
        super(context);
    }

    @Override
    protected List<GitRepository> transformBody(List<GitRepository> body) {
        return body;
    }

    public abstract void startRepoDetailsActivity();

    /**
     * 启动RepositoryActivity界面。
     * 讲GitRepository对象传递给此Activity的代码请参见 onItemClick 方法
     */
//    private void startRepositoryActivity() {
//        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
//    }
}
