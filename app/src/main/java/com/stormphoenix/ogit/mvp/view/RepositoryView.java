package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */
public interface RepositoryView extends BaseUIView {
    void setDescription(String description);

    void setStarCount(String s);

    void setBranch(String branch);

    void setForkCount(String forkCount);

    void setToolbarStatus(String repositoryName, String ownerName);

    void finishView();

    void loadReadmeHtml(String readmeText, String repoHtmlUrl, String defaultBranch);

    void setWatchersCount(String watcher);
}
