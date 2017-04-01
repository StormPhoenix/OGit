package com.stormphoenix.ogit.mvp.presenter.user;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;
import com.stormphoenix.ogit.mvp.presenter.repository.ReposPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.utils.ActivityUtils;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserStaredReposPresenter extends ReposPresenter {
    private UserInteractor mInfoInfoInteractor;

    public UserStaredReposPresenter(Context context) {
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

    /**
     * 启动RepositoryActivity界面。
     * 讲GitRepository对象传递给此Activity的代码请参见 onItemClick 方法
     */
    @Override
    public void startRepoDetailsActivity() {
        ActivityUtils.startActivity(mContext, RepositoryActivity.getIntent(mContext));
    }
}
