package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseView;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */
public interface RepositoryView extends BaseView {
    void setDescription(String description);

    void setStarCount(String s);

    void setBranch(String branch);

    void setForkCount(String forkCount);

    void setToolbarStatus(String repositoryName, String ownerName);

    void finishView();
}
