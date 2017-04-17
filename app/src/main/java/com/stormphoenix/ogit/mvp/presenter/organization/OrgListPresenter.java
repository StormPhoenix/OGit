package com.stormphoenix.ogit.mvp.presenter.organization;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.cache.FileCache;
import com.stormphoenix.ogit.mvp.model.interactor.OrgInteractor;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;
import com.stormphoenix.ogit.shares.rx.RxJavaCustomTransformer;
import com.stormphoenix.ogit.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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

    @Override
    protected void makeDataCached(List<GitOrganization> data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Gson gson = new Gson();
                subscriber.onNext(gson.toJson(data));
            }
        }).compose(RxJavaCustomTransformer.defaultSchedulers()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                FileCache.saveCacheFile(getCacheType(), s);
            }
        });
    }

    @Override
    protected FileCache.CacheType getCacheType() {
        return FileCache.CacheType.ORG_LIST;
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
