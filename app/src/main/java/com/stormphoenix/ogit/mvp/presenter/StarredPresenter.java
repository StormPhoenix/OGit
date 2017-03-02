package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.interactor.GitPersonInfoInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.shares.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class StarredPresenter extends ListItemPresenter<GitRepository> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitRepository> {
    private GitPersonInfoInteractor mInfoInfoInteractor;

    @Inject
    public StarredPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new GitPersonInfoInteractor(context);
    }

    @Override
    protected Observable<Response<List<GitRepository>>> load(int page) {
        return mInfoInfoInteractor.loadStarredRepository(PreferenceUtils.getUsername(mContext), page);
    }

    @Override
    public void onClick(View parentV, View v, Integer position, GitRepository values) {
        EventBus.getDefault().postSticky(values);
        startRepositoryActivity();
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitRepository values) {
        return false;
    }

    /**
     * 启动RepositoryActivity界面。
     * 讲GitRepository对象传递给此Activity的代码请参见 onItemClick 方法
     */
    private void startRepositoryActivity() {
        Intent intent = RepositoryActivity.getIntent(mContext);
        mContext.startActivity(intent);
    }
}