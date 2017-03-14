package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */
public class OrgPresenter extends ListItemPresenter<GitOrganization, List<GitOrganization>, ListItemView<GitOrganization>> {
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
    protected List<GitOrganization> transformBody(List<GitOrganization> body) {
        return body;
    }

    /**
     * 加载用户组织信息
     */
    @Override
    protected Observable<Response<List<GitOrganization>>> load(int page) {
        return mInteractor.loadOwnerOrgs(page);
    }
}
