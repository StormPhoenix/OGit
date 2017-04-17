package com.stormphoenix.ogit.mvp.presenter.organization;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerPresenter;
import com.stormphoenix.ogit.utils.NetworkUtils;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-21.
 * StormPhoenix is a intelligent Android developer.
 */

public class OrgMembersPresenter extends OwnerPresenter {
    private OrgInteractor mInteractor = null;
    private String orgName = null;

    public OrgMembersPresenter(Context context) {
        super(context);
        mInteractor = new OrgInteractor(context);
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.ORG_EVENTS;
    }

    @Override
    protected Observable<Response<List<GitUser>>> load(int page) {
        if (orgName == null) {
            return null;
        }
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            return super.load(page);
        }
        return mInteractor.loadMembers(orgName, page);
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
