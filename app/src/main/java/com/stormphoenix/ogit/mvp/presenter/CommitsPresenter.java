package com.stormphoenix.ogit.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.model.interactor.CommitsInteractor;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.ToolbarActivity;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.OGitConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class CommitsPresenter extends ListItemPresenter<GitCommit, List<GitCommit>, ListItemView<GitCommit>> implements BaseRecyclerAdapter.OnInternalViewClickListener<GitCommit> {

    private GitRepository repository;
    private CommitsInteractor interactor;

    @Inject
    public CommitsPresenter(Context context) {
        super(context);
        interactor = new CommitsInteractor(context);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainThreadEvent(GitRepository repository) {
        // 一定要注意EventBus的广播性质，一个不完整的 GitRepository 极有可能会传播到这个地方
        this.repository = repository;
        EventBus.getDefault().unregister(this);
        loadNewlyListItem();
    }

    @Override
    protected List<GitCommit> transformBody(List<GitCommit> body) {
        return body;
    }

    @Override
    protected Observable<Response<List<GitCommit>>> load(int page) {
        if (repository == null) {
            return null;
        }
        return interactor.loadReposCommits(repository.getOwner().getLogin(), repository.getName());
    }

    @Override
    public void onClick(View parentV, View v, Integer position, GitCommit values) {
        Bundle bundle = new Bundle();
        bundle.putInt(ToolbarActivity.TYPE, ToolbarActivity.TYPE_COMMIT_DETAILS);
        bundle.putString(OGitConstants.REPO_NAME, repository.getName());
        bundle.putString(OGitConstants.OWNER_NAME, repository.getOwner().getLogin());
//        bundle.putString(ToolbarActivity.SUB_TITLE, mContext.getString(R.string.commit_details));
        bundle.putString(OGitConstants.SHA, values.getSha());
        mContext.startActivity(ToolbarActivity.newIntent(mContext, bundle));
    }

    @Override
    public boolean onLongClick(View parentV, View v, Integer position, GitCommit values) {
        return false;
    }
}
