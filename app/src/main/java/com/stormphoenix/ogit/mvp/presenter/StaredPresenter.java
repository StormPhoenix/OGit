package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.view.View;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.model.interactor.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class StaredPresenter extends ListItemPresenter<GitRepository, List<GitRepository>, ListItemView<GitRepository>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitRepository> {
    private UserInteractor mInfoInfoInteractor;

    @Inject
    public StaredPresenter(Context context) {
        super(context);
        mInfoInfoInteractor = new UserInteractor(context);
    }

    @Override
    protected List<GitRepository> transformBody(List<GitRepository> body) {
        return body;
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
        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
    }
}