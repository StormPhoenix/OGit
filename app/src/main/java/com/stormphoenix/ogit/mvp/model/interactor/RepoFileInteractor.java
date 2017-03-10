package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

import com.stormphoenix.httpknife.github.GitBlob;
import com.stormphoenix.ogit.mvp.model.api.BlobFileApi;
import com.stormphoenix.ogit.mvp.model.api.CodeFileApi;
import com.stormphoenix.ogit.shares.rx.creator.RetrofitCreator;

import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class RepoFileInteractor {
    private CodeFileApi codeFileApi = null;
    private BlobFileApi blobFileApi = null;
    private Context mContext = null;

    public RepoFileInteractor(Context mContext) {
        blobFileApi = RetrofitCreator.getJsonRetrofit(mContext).create(BlobFileApi.class);
        codeFileApi = RetrofitCreator.getStringRetrofit(mContext).create(CodeFileApi.class);
    }

    public Observable<GitBlob> loadBlob(String owner, String repo, String path, String branch) {
        return blobFileApi.loadBlob(owner, repo, path, branch);
    }

    /**
     * 加载代码内容
     *
     * @param url
     * @return
     */
    public Observable<String> loadCodeContent(String url) {
        return codeFileApi.loadCodeContent(url);
    }
}
