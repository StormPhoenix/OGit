package com.stormphoenix.ogit.mvp.presenter.list;

import android.content.Context;
import android.content.Intent;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */
public class OrgListPresenter extends ListItemPresenter<GitOrganization, List<GitOrganization>, ListItemView<GitOrganization>> {
    private OrgInteractor mInteractor = null;

    @Inject
    public OrgListPresenter(Context context) {
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

    public void startOrgDetailsActivity(Intent intent) {
        ActivityUtils.startActivity(mContext, intent);
    }
}
