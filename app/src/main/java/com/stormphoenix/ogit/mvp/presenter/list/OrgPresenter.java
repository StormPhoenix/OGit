package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitOrg;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */
public class OrgPresenter extends ListItemPresenter<GitOrg, List<GitOrg>, ListItemView<GitOrg>> {
    private OrgInteractor mInteractor = null;

    @Inject
    public OrgPresenter(Context context) {
        super(context);
        mInteractor = new OrgInteractor(mContext);
    }

    @Override
    public void loadMoreListItem() {
        mView.hideProgress();
    }

    @Override
    protected List<GitOrg> transformBody(List<GitOrg> body) {
        return body;
    }

    /**
     * 加载用户组织信息
     */
    @Override
    protected Observable<Response<List<GitOrg>>> load(int page) {
        return mInteractor.loadOwnerOrgs(page);
    }
}
