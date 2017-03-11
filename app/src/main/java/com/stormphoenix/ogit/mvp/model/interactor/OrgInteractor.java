package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitOrg;
import com.stormphoenix.ogit.mvp.model.api.OrgApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 获取组织信息
 */

public class OrgInteractor {
    private OrgApi api = null;
    private Context mContext = null;

    public OrgInteractor(Context context) {
        mContext = context;
        api = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(OrgApi.class);
    }

    public Observable<Response<List<GitOrg>>> loadOwnerOrgs(int page) {
        return api.loadOwnerOrgs(page);
    }
}
