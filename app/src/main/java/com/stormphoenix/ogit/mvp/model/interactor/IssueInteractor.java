package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.google.gson.Gson;
import com.stormphoenix.httpknife.github.GitIssue;
import com.stormphoenix.ogit.mvp.model.api.IssueApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-4-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class IssueInteractor {
    private IssueApi issueApi;
    private Context mContext;

    @Inject
    public IssueInteractor(Context context) {
        mContext = context;
        issueApi = RetrofitCreator.getJsonRetrofitWithToken(mContext).create(IssueApi.class);
    }

    public Observable<Response<GitIssue>> sendAnIssue(String title, String content) {
        Gson gson = new Gson();
        GitIssue body = new GitIssue();
        body.setTitle(title);
        body.setBody(content);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body));
        return issueApi.sendAnIssue("StormPhoenix", "OGit", requestBody);
    }
}
