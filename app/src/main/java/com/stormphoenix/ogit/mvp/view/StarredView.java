package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.httpknife.github.GitRepository;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public interface StarredView extends BaseView {
    void initGitRepositoryList();

    void showMessage(String message);

    void initRefreshLayout();

    void loadGitRepositoryList(List<GitRepository> gitRepositories);

    void startRefresh();

    void stopRefresh();

    int getRepositoryCounts();

    void addRepositories(List<GitRepository> gitRepositories);
}
